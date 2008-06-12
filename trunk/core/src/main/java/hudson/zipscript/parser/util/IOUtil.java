package hudson.zipscript.parser.util;

import hudson.zipscript.parser.exception.ExecutionException;

import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

	public static String toString (InputStream in) {
		try {
		    StringBuffer out = new StringBuffer();
		    byte[] b = new byte[4096];
		    for (int n; (n = in.read(b)) != -1;) {
		        out.append(new String(b, 0, n));
		    }
		    return out.toString();
		}
		catch (IOException e) {
			throw new ExecutionException("Error reading input stream", null);
		}
	}
}
