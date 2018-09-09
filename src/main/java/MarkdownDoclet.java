import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import java.util.HashSet;
import java.util.Set;

/**
 * Starting class for the documentation generator.
 */
public class MarkdownDoclet {
    private static Set modules = new HashSet<String>();
    public static boolean start(RootDoc start) {
        int functionFiles = 0;
        for (ClassDoc classDoc : start.classes()) {
            if (classDoc.tags("func").length > 0) {
                handleClass(classDoc);
                functionFiles++;
            }
        }
        Log.info("Files processed: " + functionFiles);
        Log.info("Modules found:");
        modules.forEach(mod -> {
            Log.info((String) mod);
        });
        return true;
    }

    private static void handleClass(ClassDoc classDoc) {
        boolean hasFunc = false;
        for (MethodDoc methodDoc : classDoc.methods()) {
            if (methodDoc.tags("func").length > 0) {
                hasFunc = true;
                String module = classDoc.containingPackage().name();

                modules.add(module.substring(module.lastIndexOf(".") + 1));
                handleFunc(methodDoc);
            }
        }
        if (!hasFunc) {
            Log.warn("Class " + classDoc.name() + " has tag 'func' but no method tagged 'func'.");
        }
    }

    private static void handleFunc(MethodDoc methodDoc) {

    }


}
