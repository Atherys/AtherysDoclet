import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import java.util.HashMap;
import java.util.Map;

/**
 * Starting class for the documentation generator.
 */
public class MarkdownDoclet {

    private static Map<String, Module> modules = new HashMap<>();
    private static final String functionTag = "jsfunction";

    public static boolean start(RootDoc start) {
        int functionFiles = 0;
        Utils.makeDir("docs");

        for (ClassDoc classDoc : start.classes()) {
            if (classDoc.tags("jsfunction").length > 0) {
                handleClass(classDoc);
                functionFiles++;
            }
        }

        Log.info("Files processed: " + functionFiles);
        Log.info("Modules found:");
        modules.forEach((name, module) -> {
            Log.info(name);
            module.close();
        });

        return true;
    }

    private static void handleClass(ClassDoc classDoc) {
        for (MethodDoc methodDoc : classDoc.methods()) {
            if (methodDoc.overriddenMethod() != null) {
                String module = classDoc.containingPackage().name();
                module = module.substring(module.lastIndexOf(".") + 1);
                if (!modules.containsKey(module)) {
                    modules.put(module, new Module(module));
                }
                handleFunc(methodDoc, module);
            }
        }
    }

    private static void handleFunc(MethodDoc methodDoc, String module) {
        ScriptFunction function = new ScriptFunction(methodDoc, modules.get(module));
        function.write();
    }
}
