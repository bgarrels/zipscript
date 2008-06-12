package hudson.zipscript.parser.util;

import hudson.zipscript.parser.exception.ExecutionException;

public class ClassUtil {

	public static Object loadClass (String path, String type) {
		try {
			Class clazz  = Thread.currentThread()
					.getContextClassLoader().loadClass(path);
			return clazz.newInstance();
		}
		catch (InstantiationException e) {
			throw new ExecutionException("The " + type + " loader '" + path + "' failed to load", e);
		}
		catch (IllegalAccessException e) {
			throw new ExecutionException("The " + type + " loader '" + path + "' failed to load", e);
		}
		catch (ClassNotFoundException e) {
			throw new ExecutionException("The " + type + " loader '" + path + "' could not be located", e);
		}
	}
}
