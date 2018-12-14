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

    private static final String FUNCTION_TAG = "jsfunc";
    private static final Set<String> FUNCTION_NAMES = new HashSet<>();

    static {
        FUNCTION_NAMES.add("apply");
        FUNCTION_NAMES.add("get");
        FUNCTION_NAMES.add("accept");
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
            if (FUNCTION_NAMES.contains(methodDoc.name())) {
				new ScriptFunction(methodDoc, createModule(classDoc)).write();
            }
        }
    }

	private static Module createModule(ClassDoc classDoc) {
		String moduleName = classDoc.containingPackage().name();
		moduleName = moduleName.substring(moduleName.lastIndexOf(".") + 1);

		if (!modules.containsKey(moduleName)) {
			Tag[] fileTag = classDoc.containingPackage().tags("file");

			String fileName = moduleName;
			if (fileTag.length > 0) {
				fileName = fileTag[0] .text();
			}
			String overview = classDoc.containingPackage().commentText();

			modules.put(moduleName, new Module(moduleName, overview, fileName));
		}

		return modules.get(moduleName);
	}
}
