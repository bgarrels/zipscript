package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;

public class WhitespacePatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return null;
	}

	public char[][] getStartTokens() {
		return new char[][] {
			" ".toCharArray(),
			"\r\n".toCharArray(),
			"\n".toCharArray(),
			"\t".toCharArray()
		};
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader)
			throws ParseException {
		return new WhitespaceElementImpl();
	}
}
