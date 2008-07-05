package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

import java.nio.CharBuffer;
import java.util.List;


public class MacroInstanceHeaderFooterPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return "[[".toCharArray();
	}

	public char[][] getStartTokens() {
		return null;
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader,
			ParsingSession session, List elements, StringBuffer unmatchedChars) throws ParseException {
		if (elements.size() > 0
				&& elements.get(elements.size()-1) instanceof MacroInstanceDirective
				&& unmatchedChars.toString().trim().length() == 0) {
			int nesting = 1;
			previousChar = Character.MIN_VALUE;
			StringBuffer sb = new StringBuffer();
			while (reader.hasRemaining()) {
				
			}
			
			
		}
		else return null;
		
		boolean isFlat = false;
		int nesting = 1;
		previousChar = Character.MIN_VALUE;
		StringBuffer sb = new StringBuffer();
		while (reader.hasRemaining()) {
			char c = reader.get();
			boolean inString = false;
			if (c == '[') {
				if (!inString) nesting ++;
			}
			else if (c == '\'' || c == '\"') {
				inString = !inString;
			}
			else if (c == ']') {
				if (!inString) nesting --;
				if (nesting == 0) {
					if (previousChar == '/') {
						isFlat = true;
						sb.deleteCharAt(sb.length()-1);
					}
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
					if (startChars.length == 2) {
						return new MacroInstanceDirective(
								sb.toString(), isFlat, session, reader.position());
					}
					else {
						return new MacroInstanceDirective(
								sb.toString(), isFlat, true, session, reader.position());
					}
				}
			}
			else if (c == '\\') {
				// escape sequence
				if (reader.hasRemaining()) {
					sb.append(reader.get());
					continue;
				}
			}
			previousChar = c;
			sb.append(c);
		}
		return null;
	}
}