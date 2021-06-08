package flyinwind.dependency.finder;

import java.util.Collection;

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
        return "DependencyException{" +
                "\ndependencyNotFond=" + dependencyNotFond +
                "\n, dependencyNotNeed=" + dependencyNotNeed +
                "\n}";
    }
}
