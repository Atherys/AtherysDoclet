import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptFunction {
    private static final String jsNameTag = "jsname";
    private static final String exampleTag = "ex";
    private String name;
    private String description;
    private String returnType;
    private String returnDesc;
    private Module module;

    private List<String> example;
    private List<Tag> paramDescs;
    private List<Parameter> parameters;

    public ScriptFunction(MethodDoc methodDoc, Module module) {
        Tag[] nameTag = methodDoc.tags(jsNameTag);
        String name;

        //Default name is the class name with the first letter lowercase
        if (nameTag.length == 0) {
            String containing = methodDoc.containingClass().simpleTypeName();
            name = Character.toLowerCase(containing.charAt(0)) + containing.substring(1);
        } else {
            name = nameTag[0].text();
        }


        Tag[] descTag = methodDoc.tags("return");
        if (descTag.length > 0) {
            returnDesc = descTag[0].text();
        }

        example = new ArrayList<>();
        for (Tag code : methodDoc.tags(exampleTag)) {
            example.add(code.text());
        }

        description = methodDoc.commentText();
        parameters = Arrays.asList(methodDoc.parameters());
        paramDescs = Arrays.asList(methodDoc.tags("param"));
        returnType = methodDoc.returnType().simpleTypeName();
        this.name = name;
        this.module = module;
    }

    /**
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

    /**
     * Writes the function's information to its module file
     */
    public void write() {
        module.writeln("## " + name);
        module.writeln("");
        module.writeln(description);
        module.writeln("");
        module.writeln("#### Signature:");
        module.writeln("```js");
        module.writeln(signature());
        module.writeln("```");
        paramDescs.forEach(tag -> {
            module.writeln("");
            module.writeln(Utils.split(tag.text()));
        });
        if (returnDesc != null) {
            module.writeln("");
            module.writeln("Returns a _**" + returnType + "**_: " + returnDesc);
        }
        if (example.size() > 0) {
            module.writeln("");
            module.writeln("#### Example:");
            module.writeln("");
            module.writeln("```js");
            example.forEach(code -> module.writeln(code));
            module.writeln("```");
        }
        module.writeln("");
    }
}
