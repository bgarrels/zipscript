package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;


public class EndMacroInstancePatternMatcher implements PatternMatcher {

	boolean isFlat = false;
	public char[] getStartToken() {
		return "[/@".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(char previousChar, char[] startChars,
			CharBuffer reader, ParsingSession session) throws ParseException {
		StringBuffer sb = new StringBuffer();
		while (reader.hasRemaining()) {
			char c = reader.get();
			if (c == '[') {
				throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, null, "Unexpted content '['");
			}
			else if (c == ']') {
				// check for new line
				if (reader.hasRemaining()) {
					int readAmt = 0;
					if (reader.charAt(0) == '\r')
						readAmt ++;
					if(reader.charAt(readAmt) == '\n') {
						readAmt ++;
						for (int i=0; i<readAmt; i++)
							reader.get();
					}
				}
				return new EndMacroInstanceDirective(sb.toString().trim());
			}
			sb.append(c);
		}
		return null;
	}
}