package flyinwind.dependency.finder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Collection;
import java.util.HashMap;

public class DependencyException extends Exception {

    private Collection<Dependency> dependencyNotFond;
    private Collection<Dependency> dependencyNotNeed;

    public Collection<Dependency> getDependencyNotFond() {
        return dependencyNotFond;
    }

    public void setDependencyNotFond(Collection<Dependency> dependencyNotFond) {
        this.dependencyNotFond = dependencyNotFond;
    }

    public Collection<Dependency> getDependencyNotNeed() {
        return dependencyNotNeed;
    }

    public void setDependencyNotNeed(Collection<Dependency> dependencyNotNeed) {
        this.dependencyNotNeed = dependencyNotNeed;
    }

    @Override
    public String toString() {
        ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
        HashMap<String, Collection<Dependency>> msg = new HashMap<>(3);
        msg.put("dependencyNotFond", dependencyNotFond);
        msg.put("dependencyNotNeed", dependencyNotNeed);
        try {
            return objectWriter.writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
