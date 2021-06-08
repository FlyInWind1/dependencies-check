package flyinwind.dependency.finder;

import java.util.List;

public class DependencyGraph {
    private String graphName;

    public String getGraphName() {
        return graphName;
    }

    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    private List<Dependency> artifacts;

    public List<Dependency> getArtifacts() {
        return artifacts;
    }

    public DependencyGraph setArtifacts(List<Dependency> artifacts) {
        this.artifacts = artifacts;
        return this;
    }
}
