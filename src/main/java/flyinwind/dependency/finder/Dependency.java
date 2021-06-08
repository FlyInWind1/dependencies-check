package flyinwind.dependency.finder;

import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;
import java.util.Objects;

public class Dependency {
    private String groupId;
    private String artifactId;
    private String version;
    private String[] scopes;
    private String[] types;

    public String getGroupId() {
        return groupId;
    }

    public Dependency setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Dependency setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Dependency setVersion(String version) {
        this.version = version;
        return this;
    }

    public String[] getScopes() {
        return scopes;
    }

    public Dependency setScopes(String[] scopes) {
        this.scopes = scopes;
        return this;
    }

    public String[] getTypes() {
        return types;
    }

    public Dependency setTypes(String[] types) {
        this.types = types;
        return this;
    }

    /**
     * because jar file only contains artifactId,version and extension,
     * so only compare them
     *
     * @param o dependency
     * @return equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dependency that = (Dependency) o;
        return Objects.equals(artifactId, that.artifactId)
                && Objects.equals(version, that.version)
                && Arrays.equals(types, that.types);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(artifactId, version);
        result = 31 * result + Arrays.hashCode(types);
        return result;
    }

    public static Dependency from(String filename) {
        String basename = FilenameUtils.getBaseName(filename);
        int underlineIndex = basename.lastIndexOf("-");
        return new Dependency()
                .setArtifactId(basename.substring(0, underlineIndex))
                .setVersion(basename.substring(underlineIndex + 1))
                .setTypes(new String[]{FilenameUtils.getExtension(filename)});
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", scopes=" + Arrays.toString(scopes) +
                ", types=" + Arrays.toString(types) +
                '}';
    }
}
