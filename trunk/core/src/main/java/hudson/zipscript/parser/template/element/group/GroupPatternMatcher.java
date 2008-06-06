package hudson.zipscript.parser.template.element.group;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;


public class GroupPatternMatcher implements PatternMatcher {

	public char[][] getStartTokens() {
		return new char[][] {
				"(".toCharArray(),
				")".toCharArray()
		};
	}

	public char[] getStartToken() {
		return null;
	}

	public Element match(char previousChar, char[] startChars, CharBuffer contents)
			throws ParseException {
		if (startChars[0] == '(')
			return new GroupElement();
		else
			return new EndGroupElement();
	}
}
