import java.io.File;

public class Utils {
    public static void makeDir(String name) {
        File dir = new File(name);
        dir.mkdir();
    }

    public static String split(String tagText) {
        int index = tagText.indexOf(" ");
        return tagText.substring(0, index) + ":" + tagText.substring(index);
    }
}
