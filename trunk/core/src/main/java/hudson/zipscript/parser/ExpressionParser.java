package hudson.zipscript.parser;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParseData;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;
import hudson.zipscript.parser.util.ElementNormalizer;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import sun.security.pkcs.ParsingException;


public class ExpressionParser {

	private static final ExpressionParser INSTANCE = new ExpressionParser();
	public static final ExpressionParser getInstance () {
		return INSTANCE;
	}
	private ExpressionParser () {}

	public ParseData parse (
			String contents, Component[] components, DefaultElementFactory defaultElementFactory)
	throws ParseException {
		ParseParameters parameters = new ParseParameters(false, false);
		return parse(
				CharBuffer.wrap(contents.toCharArray()),
				getStartTokens(components),
				defaultElementFactory, parameters);
	}

	public Element parseToElement (
			String contents, PatternMatcher[] matchers, DefaultElementFactory defaultElementFactory)
	throws ParseException {
		ParseParameters parameters = new ParseParameters(true, true);
		ParseData data = parse(
				CharBuffer.wrap(contents), getStartTokens(matchers), defaultElementFactory, parameters);
		if (data.getElements().size() == 1)
			return (Element) data.getElements().get(0);
		else
			return null;
	}
			

	public List parse (
			String contents, PatternMatcher[] matchers, DefaultElementFactory defaultElementFactory,
			ParseParameters parameters)
	throws ParseException {
		ParseData data = parse(
				CharBuffer.wrap(contents), getStartTokens(matchers), defaultElementFactory, parameters);
		return data.getElements();
	}

	private ParseData parse (
			CharBuffer buffer, StartTokenEntry[] startTokens, DefaultElementFactory defaultElementFactory, ParseParameters parameters) 
	throws ParseException {
		List elements = new ArrayList();
		List lineBreaks = new ArrayList();
		StringBuffer unmatchedChars = new StringBuffer();
		StartTokenEntry startTokenEntry = null;
		try {
			char previousChar = Character.MIN_VALUE;
			while (buffer.hasRemaining()) {
				boolean match = false;
				char c = buffer.get();
				if (c == '\n') lineBreaks.add(new Long(buffer.position()));
				for (int i=0; i<startTokens.length; i++) {
					if (c == startTokens[i].startToken[0]) {
						// possible start token match
						startTokenEntry = startTokens[i];
						int position = buffer.position();
						if (isMatch(buffer, startTokenEntry.startToken)) {
							// we've got a start token match - remove remaining start tokens from buffer
							for (int j=1; j<startTokenEntry.startToken.length; j++)
								buffer.get();
							Element e = startTokenEntry.patternMatcher.match(
									previousChar, startTokenEntry.startToken, buffer);
							
							if (null != e) {
								e.setElementPosition(position);
								e.setElementLength((int) (buffer.position()-position));
								unmatchedChars = recordUnmatchedChars(
										unmatchedChars, elements, defaultElementFactory);
								elements.add(e);
								match = true;

								buffer.position(buffer.position()-1);
								previousChar = buffer.get();
								break;
							}
							else {
								// remove start tokens that were read from the buffer
								buffer.position(position);
							}
						}
					}
				}
				if (!match && defaultElementFactory.doAppend(c)) {
					unmatchedChars.append(c);
				}
			}
			recordUnmatchedChars(unmatchedChars, elements, defaultElementFactory);
	
			ElementNormalizer.normalize(elements, parameters, true);
			return getParsingData(elements, lineBreaks);
		}
		catch (ParseException e) {
			e.setParsingData(getParsingData(elements, lineBreaks));
			throw e;
		}
	}

	private ParseData getParsingData (List elements, List lineBreaks) {
		long[] lineBreakArr = new long[lineBreaks.size()];
		for (int i=0; i<lineBreaks.size(); i++)
			lineBreakArr[i] = ((Long) lineBreaks.get(i)).longValue();
		return new ParseData(elements, lineBreakArr);
	}

	private StringBuffer recordUnmatchedChars (
			StringBuffer sb, List elements, DefaultElementFactory factory)
	throws ParseException {
		// we've got an element match - record it
		if (sb.length() > 0) {
			// record any unmatched characters as an element
			elements.add(factory.createDefaultElement(
					sb.toString()));
			return new StringBuffer();
		}
		else return sb;
	}

	private boolean isMatch (CharBuffer cb, char[] tokens) {
		if (tokens.length == 1) return true;
		if (tokens.length > cb.length() + 1) return false;
		for (int i=1; i<tokens.length; i++) {
			if (tokens[i] != cb.charAt(i-1)) return false;
		}
		return true;
	}

	private StartTokenEntry[] getStartTokens (Component[] components) {
		List l = new ArrayList();
		for (int i=0; i<components.length; i++) {
			PatternMatcher[] patternMatchers = components[i].getPatternMatchers();
			for (int j=0; j<patternMatchers.length; j++) {
				if (null != patternMatchers[j].getStartToken()) {
					l.add(new StartTokenEntry(components[i], patternMatchers[j], patternMatchers[j].getStartToken()));
				}
				else if (null != patternMatchers[j].getStartTokens()) {
					char[][] startTokens = patternMatchers[j].getStartTokens();
					for (int k=0; k<startTokens.length; k++) {
						l.add(new StartTokenEntry(components[i], patternMatchers[j], startTokens[k]));
					}
				}
			}
		}
		return (StartTokenEntry[]) l.toArray(new StartTokenEntry[l.size()]);
	}

	private StartTokenEntry[] getStartTokens (PatternMatcher[] patternMatchers) {
		List l = new ArrayList();
		for (int j=0; j<patternMatchers.length; j++) {
			if (null != patternMatchers[j].getStartToken()) {
				l.add(new StartTokenEntry(null, patternMatchers[j], patternMatchers[j].getStartToken()));
			}
			else if (null != patternMatchers[j].getStartTokens()) {
				char[][] startTokens = patternMatchers[j].getStartTokens();
				for (int k=0; k<startTokens.length; k++) {
					l.add(new StartTokenEntry(null, patternMatchers[j], startTokens[k]));
				}
			}
		}
		return (StartTokenEntry[]) l.toArray(new StartTokenEntry[l.size()]);
	}

	private class StartTokenEntry {
		private char[] startToken;
		private PatternMatcher patternMatcher;
		private Component component;

		public StartTokenEntry (
			Component component, PatternMatcher patternMatcher, char[] startToken) {
			this.component = component;
			this.patternMatcher = patternMatcher;
			this.startToken = startToken;
		}
	}
}
