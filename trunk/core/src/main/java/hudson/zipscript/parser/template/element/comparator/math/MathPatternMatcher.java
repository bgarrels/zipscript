package hudson.zipscript.parser.template.element.comparator.math;

import java.nio.CharBuffer;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;

public class MathPatternMatcher implements PatternMatcher {

	public char[] getStartToken() {
		return null;
	}

	public char[][] getStartTokens() {
		return new char[][] {
			"+".toCharArray(),
			"-".toCharArray(),
			"*".toCharArray(),
			"/".toCharArray(),
			"%".toCharArray(),
			"^".toCharArray()
		};
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader, ParsingSession parseData)
			throws ParseException {
		char c = startChars[0];
		if (c == '+')
			return new AddExpression();
		else if (c == '-')
			return new SubtractExpression();
		else if (c == '*')
			return new MultiplyExpression();
		else if (c == '/')
			return new DivideExpression();
		else if (c == '%')
			return new ModExpression();
		else
			return new ExponentExpression();
	}
}
