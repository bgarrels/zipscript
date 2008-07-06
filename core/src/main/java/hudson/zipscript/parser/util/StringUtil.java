package hudson.zipscript.parser.util;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.TextElement;

import java.io.IOException;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.List;

public class StringUtil {

	public static void append (char c, Writer writer) {
		try {
			writer.write(c);
		}
		catch (IOException e) {
			throw new ExecutionException(e.getMessage(), null, e);
		}
	}

	public static void append (String s, Writer writer) {
		try {
			for (int i=0; i<s.length(); i++)
				writer.write(s.charAt(i));
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

	public static String trimLastEmptyLine (StringBuffer sb) {
		for (int i=sb.length()-1; i>=0; i--) {
			char c = sb.charAt(i);
			if (c == '\n') {
				if (i > 0 && sb.charAt(i-1) == '\r')
					i--;
				String rtn = sb.substring(i, sb.length());
				sb.delete(i, sb.length());
				return rtn;
			}
			else if (c != ' ' && c != '\t') return null;
		}
		return null;
	}

	public static String trimFirstEmptyLine (CharBuffer reader) {
		for (int i=0; reader.length() > i; i++) {
			char c = reader.charAt(i);
			if (c == '\r') {
				if (reader.length() > i && reader.charAt(i+1) == '\n')
					i ++;
				char[] carr = new char[i+1];
				reader.position(reader.position()+i+1);
				return new String(carr);
			}
			else if (c == '\n') {
				char[] carr = new char[i-1];
				reader.position(reader.position()+i+1);
				return new String(carr);
			}
			else if (c != ' ' && c != '\t') {
				return null;
			}
		}
		return null;
	}

	public static String trimLastEmptyLine (List elements) {
		if (null != elements && elements.size() > 0) {
			Element e = (Element) elements.get(elements.size()-1);
			if (e instanceof TextElement) {
				return trimLastEmptyLine((TextElement) e);
			}
		}
		return null;
	}

	public static String trimLastEmptyLine (List elements, int index) {
		if (index == 0) return null;
		if (null != elements && elements.size() > 0) {
			Element e = (Element) elements.get(index-1);
			if (e instanceof TextElement) {
				return trimLastEmptyLine((TextElement) e);
			}
		}
		return null;
	}

	public static String trimLastEmptyLine (TextElement element) {
		String text = element.getText();
		for (int i=text.length()-1; i>=0; i--) {
			char c = text.charAt(i);
			if (c == '\n') {
				if (i > 0 && text.charAt(i-1) == '\r')
					i --;
				String rtn = text.substring(i, text.length());
				element.setText(text.substring(0, i));
				return rtn;
			}
			else if (c != ' ' && c != '\t') {
				return null;
			}
		}
		return null;
	}
}