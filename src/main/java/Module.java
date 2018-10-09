import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Represents a module (package) of script functions.
 */
public class Module {
    private String name;
    private String overview;
    private File file;
    private PrintWriter writer;

    public Module(String name, String overview) {
        this.name = name;
        this.overview = overview;
        this.file = new File("docs/" + name + ".md");

        try {
            this.writer = new PrintWriter(file);
            writeln(overview);
        } catch (FileNotFoundException e) {
            Log.error("File not found for module " + name);
        }
    }

    public void writeln(String line) {
        writer.println(line);
    }

    public void close() {
        writer.close();
    }

    @Override
    public String toString() {
        return name;
    }
}
