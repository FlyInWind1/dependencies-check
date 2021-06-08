package flyinwind.dependency.finder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LostDependenciesFinder {
    private URL dependencyGraphUrl;

    public URL getDependencyGraphUrl() {
        return dependencyGraphUrl;
    }

    public void setDependencyGraphUrl(URL dependencyGraphUrl) {
        this.dependencyGraphUrl = dependencyGraphUrl;
    }

    public void check(ClassLoader classLoader) throws DependencyException {
        //noinspection AlibabaUndefineMagicConstant
        if ("org.springframework.boot.loader.LaunchedURLClassLoader".equals(classLoader.getClass().getName())) {
            try {
                if (dependencyGraphUrl == null) {
                    dependencyGraphUrl = classLoader.getResource("dependency-graph.json");
                }

                ObjectMapper objectMapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                DependencyGraph dependencyGraph = objectMapper.readValue(dependencyGraphUrl, DependencyGraph.class);
                List<Dependency> requiredDependencies = dependencyGraph.getArtifacts();
                for (int i = requiredDependencies.size() - 1; i >= 0; i--) {
                    Dependency dependency = requiredDependencies.get(i);
                    if (dependency.getArtifactId().equals(dependencyGraph.getGraphName())
                            || !"jar".equals(dependency.getTypes()[0])
                    ) {
                        requiredDependencies.remove(i);
                    }
                }

                Class<? extends ClassLoader> loaderClass = URLClassLoader.class;
                Field ucpField = loaderClass.getDeclaredField("ucp");
                ucpField.setAccessible(true);
                Object urlClassPath = ucpField.get(classLoader);
                Field pathField = urlClassPath.getClass().getDeclaredField("path");
                pathField.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<URL> pathList = (List<URL>) pathField.get(urlClassPath);
                Set<String> existDependencies = pathList.stream()
                        .map(url -> FilenameUtils.getName(url.getPath()))
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toSet());
                Iterator<Dependency> dependencyIterator = requiredDependencies.iterator();
                while (dependencyIterator.hasNext()) {
                    Dependency requiredDependency = dependencyIterator.next();
                    String jarFileName = requiredDependency.getArtifactId()
                            + "-" + requiredDependency.getVersion()
                            + "." + requiredDependency.getTypes()[0];
                    if (existDependencies.remove(jarFileName)) {
                        dependencyIterator.remove();
                    }
                }
                if (!requiredDependencies.isEmpty() || !existDependencies.isEmpty()) {
                    DependencyException exception = new DependencyException();
                    exception.setDependencyNotFond(requiredDependencies);
                    exception.setDependencyNotNeed(existDependencies.stream().map(Dependency::from).collect(Collectors.toList()));
                    throw exception;
                }

            } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
