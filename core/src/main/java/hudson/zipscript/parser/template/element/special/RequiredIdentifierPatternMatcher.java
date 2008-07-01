package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;

public class RequiredIdentifierPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return "*".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(char previousChar, char[] startChars,
			CharBuffer reader, ParsingSession parseData) throws ParseException {
		if (Character.isWhitespace(previousChar) || previousChar == '[') {
			if (reader.hasRemaining()) {
				char c = reader.charAt(0);
				if (Character.isLetterOrDigit(c))
					return RequiredIdentifierElement.getInstance();
			}
		}
		return null;
	}
}