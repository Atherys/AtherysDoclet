import java.io.File;

public class FileUtils {
    public static void makeDir(String name) {
        File dir = new File(name);
        dir.mkdir();
    }
}
