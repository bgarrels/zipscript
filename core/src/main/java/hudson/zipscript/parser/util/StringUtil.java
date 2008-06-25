package hudson.zipscript.parser.util;

import hudson.zipscript.parser.exception.ExecutionException;

import java.io.IOException;
import java.io.Writer;

public class StringUtil {

	public static void append (char c, Writer writer) {
		try {
			writer.append(c);
		}
		catch (IOException e) {
			throw new ExecutionException(e.getMessage(), null, e);
		}
	}

	public static void append (String s, Writer writer) {
		try {
			for (int i=0; i<s.length(); i++)
				writer.append(s.charAt(i));
		}
		catch (IOException e) {
			throw new ExecutionException(e.getMessage(), null, e);
		}
	}

	public static String firstLetterUpperCase (String s) {
		if (null == s|| s.length() == 1) return s;
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toUpperCase(s.charAt(0)));
		for (int i=1; i<s.length(); i++)
			sb.append(s.charAt(i));
		return sb.toString();
	}

	public static String firstLetterLowerCase (String s) {
		if (null == s|| s.length() == 1) return s;
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toLowerCase(s.charAt(0)));
		for (int i=1; i<s.length(); i++)
			sb.append(s.charAt(i));
		return sb.toString();
	}

	public static String humpbackCase (String s) {
		StringBuffer sb = new StringBuffer();
		boolean doUpper = false;
		for (int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			while (Character.isWhitespace(c) || c == '_' || c == '-') {
				doUpper = true;
				if (s.length() > i)
					c = s.charAt(++i);
				else
					return sb.toString();
			}
			if (Character.isLetterOrDigit(c)) {
				if (doUpper && sb.length() > 0)
					sb.append(Character.toUpperCase(c));
				else
					sb.append(Character.toLowerCase(c));
				doUpper = false;
			}
		}
		return sb.toString();
	}
}
