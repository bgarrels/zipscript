package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;

import java.nio.CharBuffer;



public interface PatternMatcher {

	public char[] getStartToken ();

	public char[][] getStartTokens ();

	public Element match (
			char previousChar, char[] startChars, CharBuffer reader, ParsingSession parseData) throws ParseException;
}
