package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;

import java.nio.CharBuffer;

/**
 * Used for matching a expression resource pattern
 * 
 * @author Joe Hudson
 */

public interface PatternMatcher {

	/**
	 * Return the start token or null if getStartTokens() is used
	 */
	public char[] getStartToken ();

	/**
	 * Return the array of start tokens or null if getStartToken() is used
	 */
	public char[][] getStartTokens ();

	/**
	 * Called if the document matched this pattern mather's start token
	 * @param previousChar
	 * @param startChars
	 * @param reader
	 * @param parseData
	 * @return
	 * @throws ParseException
	 */
	public Element match (
			char previousChar, char[] startChars, CharBuffer reader, ParsingSession parseData) throws ParseException;
}
