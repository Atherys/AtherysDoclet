package com.atherys.doclet;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Represents a single scripting function.
 */
public class ScriptFunction {
    private static final String NAME_TAG = "jsname";
    private static final String CODE_TAG = "code";

    private String name;
    private String description;
    private String returnType;
    private String returnDescription;
    private Module module;

    private String example;
    private List<Tag> paramDescs;
    private List<Parameter> parameters;

    public ScriptFunction(MethodDoc methodDoc, Module module) {
        Optional<String> nameTag = Utils.getTag(methodDoc, NAME_TAG);

        //Default name is the class name with the first letter lowercase
        if (nameTag.isPresent()) {
            name = nameTag.get();
        } else {
            String containing = methodDoc.containingClass().simpleTypeName();
            name = Character.toLowerCase(containing.charAt(0)) + containing.substring(1);
        }

        Utils.getTag(methodDoc, "return").ifPresent(this::setDescription);
        Utils.getTag(methodDoc, CODE_TAG).ifPresent(this::setCode);

        description = methodDoc.commentText();
        parameters = Arrays.asList(methodDoc.parameters());
        paramDescs = Arrays.asList(methodDoc.tags("param"));
        returnType = methodDoc.returnType().simpleTypeName();
        this.module = module;
    }

    private void setDescription(String description) {
        returnDescription = description;
    }

    private void setCode(String code) {
        this.example = code;
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
            sigBuilder.append(parameter.type().simpleTypeName()).append(parameter.type().dimension());
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
        module.writeln();

        if (description.length() > 0) {
            module.writeln(description);
            module.writeln();
            module.writeln("### Signature:");
        } else {
            module.writeln("<h3 style=\"padding-top: 4.6rem\"> Signature: </h3>");
            module.writeln();
        }

        module.writeln("```js");
        module.writeln(signature());
        module.writeln("```");

		if (paramDescs.size() > 0) {
			module.writeln("### Arguments:");
		}
        paramDescs.forEach(tag -> {
            if (tag.text().split(" ").length >= 2) {
                module.writeln();
                module.writeln(Utils.split(tag.text()));
            }
        });

        if (returnDescription != null) {
            module.writeln();
            module.writeln("Returns a _**" + returnType + "**_: " + returnDescription);
        }

        if (example != null) {
            module.writeln();
            module.writeln("### Example:");
            module.writeln();
            module.writeln("```js");
            module.writeln(example);
            module.writeln("```");
        }
        module.writeln();
    }
}
