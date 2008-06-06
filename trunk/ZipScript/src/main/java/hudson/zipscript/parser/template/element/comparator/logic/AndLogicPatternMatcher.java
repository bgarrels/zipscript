package hudson.zipscript.parser.template.element.comparator.logic;

import java.nio.CharBuffer;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

public class AndLogicPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return new char[]{'&','&'};
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader)
			throws ParseException {
		return new AndLogicElement();
	}
}
