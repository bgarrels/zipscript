package hudson.zipscript.parser.template.element.comment;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractPatternMatcher;
import hudson.zipscript.parser.template.element.Element;

import java.nio.CharBuffer;

public class CommentPatternMatcher extends AbstractPatternMatcher {

	public Element match(char previousChar, char[] startChars,
			CharBuffer contents, ParsingSession parseData)
			throws ParseException {
		Element e = super.match(previousChar, startChars, contents, parseData);
		if (null != e && contents.hasRemaining()) {
			if (contents.charAt(0) == '\r')
				contents.get();
			if (contents.charAt(0) == '\n')
				contents.get();
		}
		return e;
	}
	
	protected Element createElement(char[] startToken, String s,
			int contentStartPosition, ParsingSession parseData)
			throws ParseException {
		return CommentElement.getInstance();
	}

	protected char[] getEndChars() {
		return "**#".toCharArray();
	}

	public char[] getStartToken() {
		return "#**".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

	protected boolean isNestingApplicable() {
		return false;
	}
}