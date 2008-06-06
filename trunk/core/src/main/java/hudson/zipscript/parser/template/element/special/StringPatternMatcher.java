package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.template.element.AbstractPatternMatcher;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.TextElement;

public class StringPatternMatcher extends AbstractPatternMatcher {

	protected Element createElement(char[] startToken, String s) {
		return new TextElement(s);
	}

	protected char[] getEndChars() {
		return "\'".toCharArray();
	}

	public char[] getStartToken() {
		return "\'".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

}
