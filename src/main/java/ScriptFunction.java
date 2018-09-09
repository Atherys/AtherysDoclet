import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;

import java.util.Arrays;
import java.util.List;

public class ScriptFunction {
    private String name;
    private Module module;
    private String description;
    private String returnType;

    private List<Parameter> parameters;

    public ScriptFunction(MethodDoc methodDoc, Module module) {
        Tag[] nameTag = methodDoc.tags("jsname");
        String name;
        if (nameTag.length == 0) {
            String containing = methodDoc.containingClass().simpleTypeName();
            name = Character.toLowerCase(containing.charAt(0)) + containing.substring(1);
        } else {
            name = nameTag[0].text();
        }

        description = methodDoc.commentText();

        parameters = Arrays.asList(methodDoc.parameters());
        this.name = name;
        this.module = module;
        this.returnType = methodDoc.returnType().simpleTypeName();
        Log.info(signature());
    }

    /**
     *
     * @return the signature of the js function
     */
    private String signature() {
        StringBuilder sigBuilder = new StringBuilder()
                .append(returnType)
                .append(" ")
                .append(name)
                .append("(");
        int index = 0;
        for (Parameter parameter : parameters) {
            sigBuilder.append(parameter.type().simpleTypeName());
            sigBuilder.append(" ");
            sigBuilder.append(parameter.name());
            index++;
            if (index < parameters.size()) {
                sigBuilder.append(", ");
            }
        }
        sigBuilder.append(")");
        return sigBuilder.toString();
    }

    public void write() {
        module.writeln("## " + name);
        module.writeln("");
        module.writeln(description);
        module.writeln("");
        module.writeln("#### Signature:");
        module.writeln("```js");
        module.writeln(signature());
        module.writeln("```");
        module.writeln("");

    }
}
