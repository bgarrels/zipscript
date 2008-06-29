package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;

public class AssignmentPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return "=".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(
			char previousChar, char[] startChars, CharBuffer reader, ParsingSession session)
			throws ParseException {
		if (previousChar == '=') return null;
		if (reader.length() == 0) return new AssignmentElement();
		else if (reader.charAt(0) != '=') return new AssignmentElement();
		else return null;
	}
}
