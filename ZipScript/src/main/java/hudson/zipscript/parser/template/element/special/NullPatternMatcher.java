package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;

public class NullPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return "null".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader)
			throws ParseException {
		return NullElement.getInstance();
	}
}