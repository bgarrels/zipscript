package hudson.zipscript.parser.util;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.resource.FileResourceLoader;

import java.io.FileWriter;
import java.util.HashMap;

public class CommandLineUtil {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("You must provide the filename as the first argument");
			System.exit(-1); 
		}

		HashMap props = new HashMap();
		props.put("templateResourceLoader.class", FileResourceLoader.class.getName());
		ZipEngine engine = new ZipEngine();
		engine.init(props);
		try {
			FileWriter fw = new FileWriter(args[0] + ".txt");
			engine.getTemplate(args[0]).merge(null, fw);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1); 
		}
	}
}
