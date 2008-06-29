package hudson.zipscript.parser.template.element.comparator.logic;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;

public class OrLogicPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return new char[]{'|','|'};
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader, ParsingSession parseData)
			throws ParseException {
		return new OrLogicElement();
	}
}
