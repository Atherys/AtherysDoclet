import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Module {
    private String name;
    private File file;
    private PrintWriter writer;

    public Module(String name) {
        this.name = name;
        this.file = new File("docs/" + name + ".md");
        try {
            this.writer = new PrintWriter(file);
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
