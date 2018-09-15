# AtherysDoclet

This is the Javadoc doclet for generating documentation for AtherysScript from the Java code. To run it, download the latest [release](https://github.com/Atherys-Horizons/AtherysDocs/releases) and run it with the following command:

```
python docgen.py paths/to/dependent/jars path/to/plugin/directory
```

Dependent jars are any jars that the plugin depends on (mostly other A'therys plugins). The doclet will create a directory called `docs/` with a markdown file for each package. 

### Supported tags

* @jsfunc - Tells the doclet that the class contains a scripting function
* @jsname - The default name for the JavaScript function is the name of the class with the first letter lowercase. If it differs, use this tag.
* @ex - Use this tag for the JavaScript example; the lines with this tag will be printed after one another.

