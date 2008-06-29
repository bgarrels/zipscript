package hudson.zipscript.parser.util;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;

public class ClassUtil {

	public static Object loadClass (String path, String type, Element fromElement) {
		try {
			Class clazz  = Thread.currentThread()
					.getContextClassLoader().loadClass(path);
			return clazz.newInstance();
		}
		catch (InstantiationException e) {
			throw new ExecutionException("The " + type + " loader '" + path + "' failed to load", fromElement);
		}
		catch (IllegalAccessException e) {
			throw new ExecutionException("The " + type + " loader '" + path + "' failed to load", fromElement);
		}
		catch (ClassNotFoundException e) {
			throw new ExecutionException("The " + type + " loader '" + path + "' could not be located", fromElement);
		}
	}
}
