import com.sun.javadoc.Doc;
import com.sun.javadoc.Tag;

import java.io.File;
import java.util.Optional;

public class Utils {
    public static void makeDir(String name) {
        File dir = new File(name);
        dir.mkdir();
    }

    public static String split(String tagText) {
        int index = tagText.indexOf(" ");
        return "**" + tagText.substring(0, index) + "**:" + tagText.substring(index);
    }

    public static Optional<String> getTag(Doc doc, String tagName) {
        Tag[] tags = doc.tags(tagName);
        if (tags.length > 0) {
            return Optional.of(tags[0].text());
        }

        return Optional.empty();
    }
}
