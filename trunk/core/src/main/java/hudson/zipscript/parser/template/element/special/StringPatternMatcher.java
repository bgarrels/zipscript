package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractPatternMatcher;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.TextElement;

import java.nio.CharBuffer;

public class StringPatternMatcher extends AbstractPatternMatcher {

	protected Element createElement(
			char[] startToken, String s, ParsingSession session) {
		return new TextElement(s);
	}

	public Element match(
			char previousChar, char[] startChars, CharBuffer contents, ParsingSession parseData) throws ParseException {
		int position = contents.position();
		char[] endChars = getEndChars();
		int length = findMatch (contents, startChars, startChars, getInvalidChars(), true);
		String s = read(contents, position, length, 1);
		Element rtn = createElement(startChars, s, parseData);
		rtn = onCreateElement (rtn, startChars, s, contents);
		return rtn;
	}

	public char[] getStartToken() {
		return null;
	}

	public char[][] getStartTokens() {
		return new char[][] {
				"\'".toCharArray(),
				"\"".toCharArray()
		};
	}

	protected char[] getEndChars() {
		return null;
	}
}
