import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

import java.util.*;

/**
 * Starting class for the documentation generator.
 */
public class MarkdownDoclet {

    private static Map<String, Module> modules = new HashMap<>();
    private static final String functionTag = "jsfunc";

    private static final Set<String> functionNames = new HashSet<>();

    static {
        functionNames.add("apply");
        functionNames.add("get");
        functionNames.add("accept");
    }

    public static boolean start(RootDoc start) {
        Utils.makeDir("docs");

        Arrays.asList(start.classes()).forEach(classDoc -> {
            Utils.getTag(classDoc, functionTag).ifPresent((t) -> {
                handleClass(classDoc);
            });
        });

        System.out.println("Modules found:");
        modules.forEach((name, module) -> {
            System.out.println(name);
            module.close();
        });

        return true;
    }

    private static void handleClass(ClassDoc classDoc) {
        //Loop through methods to see if they are functional interfaces
        for (MethodDoc methodDoc : classDoc.methods()) {
            if (functionNames.contains(methodDoc.name())) {
                String moduleName = classDoc.containingPackage().name();
                classDoc.containingPackage();
                moduleName = moduleName.substring(moduleName.lastIndexOf(".") + 1);

                if (!modules.containsKey(moduleName)) {
                    Tag[] fileTag = classDoc.containingPackage().tags("file");
                    String fileName = moduleName;

                    String overview = classDoc.containingPackage().commentText();

                    if (fileTag.length > 0) {
                        fileName = fileTag[0] .text();
                    }

                    modules.put(moduleName, new Module(moduleName, overview, fileName));
                }

                handleFunc(methodDoc, moduleName);
            }
        }
    }

    private static void handleFunc(MethodDoc methodDoc, String module) {
        ScriptFunction function = new ScriptFunction(methodDoc, modules.get(module));
        function.write();
    }
}
