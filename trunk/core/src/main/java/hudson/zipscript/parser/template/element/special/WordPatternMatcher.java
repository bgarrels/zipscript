package hudson.zipscript.parser.template.element.special;

import java.nio.CharBuffer;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

public abstract class WordPatternMatcher implements PatternMatcher {

	private Element element;

	public char[][] getStartTokens() {
		return null;
	}

	public char[] getStartToken() {
		return getWord().toCharArray();
	}

	public Element match(
			char previousChar, char[] startChars, CharBuffer reader, ParsingSession session)
	throws ParseException {
		if (Character.isWhitespace(previousChar) || Character.MIN_VALUE == previousChar) {
			if (reader.length() == 0 || Character.isWhitespace(reader.charAt(0))) {
				if (null == element) element = getElement();
				return element;
			}
		}
		return null;
	}

	protected abstract String getWord();

	public abstract Element getElement();

	public String toString() {
		return getWord();
	}
}
