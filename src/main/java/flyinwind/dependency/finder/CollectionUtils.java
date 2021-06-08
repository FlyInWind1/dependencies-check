package flyinwind.dependency.finder;

public class CollectionUtils {
    public static boolean startWithContains(Iterable<String> iterable, String str) {
        for (String s : iterable) {
            if (str.startsWith(s)) {
                return true;
            }
        }
        return false;
    }
}
