package hudson.zipscript.parser.template.element.directive;

import hudson.zipscript.parser.template.element.AbstractPatternMatcher;
import hudson.zipscript.parser.template.element.Element;

import java.nio.CharBuffer;


public abstract class AbstractDirectivePatternMatcher extends AbstractPatternMatcher {

	private char[] startToken;
	public char[] getStartToken() {
		if (null == startToken) {
			if (!onlyAllowEmpty() && !allowEmpty()) {
				startToken = new char[3 + getDirectiveName().length()];
				startToken[0] = '[';
				startToken[1] = '#';
				System.arraycopy(
						getDirectiveName().toCharArray(), 0, startToken, 2, getDirectiveName().length());
				startToken[startToken.length-1] = ' ';
			}
			else {
				startToken = new char[2 + getDirectiveName().length()];
				startToken[0] = '[';
				startToken[1] = '#';
				System.arraycopy(
						getDirectiveName().toCharArray(), 0, startToken, 2, getDirectiveName().length());
			}
		}
		return startToken;
	}

	public char[][] getStartTokens() {
		return null;
	}

	protected char[] getEndChars () {
		return new char[]{']'};
	}

	protected char[] getInvalidChars () {
		return null;
	}

	protected abstract String getDirectiveName ();

	protected Element onCreateElement(Element element, char[] startChars,
			String contents, CharBuffer buffer) {
		if (buffer.length() > 0 && buffer.charAt(0) == '\r') {
			buffer.get();
			if (buffer.length() > 0 && buffer.charAt(0) == '\n')
				buffer.get();
		}
		else if (buffer.length() > 0 && buffer.charAt(0) == '\n') {
			buffer.get();
		}
		return element;
	}
}
