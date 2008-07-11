package hudson.zipscript.parser.template.element.lang.variable.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;
import java.util.List;

public class VarSpecialElementPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return "?".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader,
			ParsingSession session, List elements, StringBuffer unmatchedChars) throws ParseException {
		if (previousChar != ' ') {
			return new VarSpecialElement();
		}
		else {
			return null;
		}
	}
}
