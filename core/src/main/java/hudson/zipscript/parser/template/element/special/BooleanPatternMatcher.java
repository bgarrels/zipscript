package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;

public class BooleanPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return null;
	}

	public char[][] getStartTokens() {
		return new char[][] {
			"true".toCharArray(),
			"false".toCharArray()
		};
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader)
			throws ParseException {
		if (startChars[0] == 't')
			return new BooleanElement(true);
		else
			return new BooleanElement(false);
	}

}
