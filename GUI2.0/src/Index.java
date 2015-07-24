import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import net.sourceforge.yamlbeans.YamlException;
import net.sourceforge.yamlbeans.YamlReader;

public class Index {
	public static void main(String[] args) throws FileNotFoundException, YamlException{
		GUI gui = new GUI("src/Index.yml");
		gui.run();
		
	}
}