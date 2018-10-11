import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Represents a module (package) of script functions.
 */
public class Module {
    private String name;
    private File file;
    private PrintWriter writer;

    public Module(String name, String overview, String fileName) {
        this.name = name;
        this.file = new File("docs/" + fileName + ".md");

        try {
            this.writer = new PrintWriter(file);
            writeln(overview);
            writeln();
        } catch (FileNotFoundException e) {
            Log.error("File not found for module " + name);
        }
    }

    public void writeln(String line) {
        writer.println(line);
    }

    public void writeln() {
        writeln("");
    }

    public void close() {
        writer.close();
    }

    @Override
    public String toString() {
        return name;
    }
}
