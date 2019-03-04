import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class writeFile {

	private PrintWriter file;
	
	public writeFile(String name) {
		try { 
			file = new PrintWriter(new File(name));
		}
		catch(FileNotFoundException ex) {
			System.out.println("Didn't find a file");
		}
	}
	
	public void println(String text) {
		file.println(text);
	}
	
	public void close() {
		file.close();
	}
}
