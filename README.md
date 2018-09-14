# AtherysDoclet

This is the Javadoc Doclet for generating documentation for AtherysScript from the Java code. To run it, download the latest [release](https://github.com/Atherys-Horizons/AtherysDocs/releases) and run it with the following command:
```
javadoc -cp $JAVA_HOME/lib/tools.jar -doclet MarkdownDoclet -docletpath path/to/jar source-files
```

Alternatively, and more easily: download the Python script found in [doclet/docgen.py](https://github.com/Atherys-Horizons/AtherysDocs/blob/master/doclet/docgen.py) and run with the following command:

```
python docgen.py path/to/jar path/to/source-files
```
It will run javadoc on all the java files found in the given directory.

A directory named 'docs' with a markdown file for each package will be placed there.

### Supported tags

* @jsfunc - Tells the doclet that the class contains a scripting function
* @jsname - The default name for the JavaScript function is the name of the class with the first letter lowercase. If it differs, use this tag.
* @ex - Use this tag for the JavaScript example; the lines with this tag will be printed after one another.

