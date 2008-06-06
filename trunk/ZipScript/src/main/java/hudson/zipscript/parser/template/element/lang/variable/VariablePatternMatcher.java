package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.AbstractPatternMatcher;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;


public class VariablePatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return null;
	}

	public char[][] getStartTokens() {
		return new char[][]{"${".toCharArray(), "$!{".toCharArray()};
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader)
			throws ParseException {
		StringBuffer sb = new StringBuffer();
		int nesting = 1;
		boolean inString = false;
		while (reader.hasRemaining()) {
			char c = reader.get();
			if (c == '\'') {
				inString = !inString;
				sb.append(c);
			}
			else if (c == '\\') {
				if (reader.hasRemaining())
					sb.append(reader.get());
				else
					return null;
			}
			else if (!inString) {
				if (c == '{') {
					nesting ++;
				}
				else if (c == '}') {
					nesting --;
					if (nesting == 0) {
						// we've got a match
						if (startChars[1] == '!')
							return new VariableElement(true, sb.toString());
						else
							return new VariableElement(false, sb.toString());
					}
					
				}
				sb.append(c);
			}
			else {
				sb.append(c);
			}
		}
		return null;
	}
}