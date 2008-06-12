package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;

import java.nio.CharBuffer;


public abstract class AbstractPatternMatcher implements PatternMatcher {

	protected String read (
			CharBuffer contents, int position, int length, int endCharLength) {
		contents.position(position);
		char[] arr = new char[length];
		contents.get(arr);
		String s = new String(arr);
		s = s.substring(0, s.length()-endCharLength);
		return s;
	}

	protected int findMatch (
			CharBuffer contents, char[] startChars, char[] endChars, char[] stopChars, boolean allowEscape)
	throws ParseException {
		int nesting = 1;
		char startMatchStart = startChars[0];
		char endMatchStart = endChars[0];
		int length = 0;
		while (true) {
			if (!contents.hasRemaining()) {
				throw new ParseException(ParseException.TYPE_EOF, this, length);
			}
			char c = contents.get();
			if (c == endMatchStart) {
				// possible match
				nesting --;
				if (nesting > 0) {
					length ++;
					continue;
				}
				if (contents.length() >= endChars.length-1) {
					boolean match = true;
					for (int i=1; i<endChars.length; i++) {
						if (contents.charAt(i-1) != endChars[i])
							match = false;
							break;
					}
					if (match) {
						if (length == 0 && !allowEmpty() && !onlyAllowEmpty()) {
							throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, length + contents.length());
						}
						if (length > 0 && onlyAllowEmpty()) {
							throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, length + contents.length());
						}
						length += endChars.length;
						return length;
					}
				}
				else {
					throw new ParseException(ParseException.TYPE_EOF, this, length + contents.length());
				}
			}
			else if (c == startMatchStart) {
				nesting ++;
			}
			else if (isMatch(c, stopChars)) {
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, length);
			}
			length ++;
		}
	}

	public Element match(
			char previousChar, char[] startChars, CharBuffer contents, ParsingSession parseData) throws ParseException {
		int position = contents.position();
		char[] endChars = getEndChars();
		int length = findMatch (contents, startChars, endChars, getInvalidChars(), true);
		String s = read(contents, position, length, endChars.length);
		Element rtn = createElement(startChars, s, position, parseData);
		rtn = onCreateElement (rtn, startChars, s, position, contents);
		return rtn;
	}

	protected Element onCreateElement (
			Element element, char[] startChars, String contents, int contentStartPosition, CharBuffer  buffer) {
		return element;
	}

	protected boolean isMatch (char c, char[] arr) {
		if (null == arr) return false;
		for (int i=0; i<arr.length; i++) {
			if (c == arr[i]) return true;
		}
		return false;
	}

	protected boolean allowEmpty () {
		return false;
	}

	protected boolean onlyAllowEmpty () {
		return false;
	}

	protected abstract char[] getEndChars();

	protected char[] getInvalidChars() {
		return null;
	}

	protected abstract Element createElement (
			char[] startToken, String s, int contentStartPosition, ParsingSession parseData) throws ParseException;
}