package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.util.LocaleUtil;

import java.nio.CharBuffer;
import java.util.Locale;

public class NumericPatternMatcher implements PatternMatcher {

	private Locale locale;
	private char decimalSeparator;

	public NumericPatternMatcher () {
		this(Locale.getDefault());
	}

	public NumericPatternMatcher (Locale locale) {
		this.locale = locale;
		this.decimalSeparator = LocaleUtil.getDecimalSeparator(locale);
	}

	public char[] getStartToken() {
		return null;
	}

	public char[][] getStartTokens() {
		return new char[][] {
				new char[]{decimalSeparator},
				"0".toCharArray(),
				"1".toCharArray(),
				"2".toCharArray(),
				"3".toCharArray(),
				"4".toCharArray(),
				"5".toCharArray(),
				"6".toCharArray(),
				"7".toCharArray(),
				"8".toCharArray(),
				"9".toCharArray()
		};
	}

	public Element match(char previousChar, char[] startChars, CharBuffer reader)
			throws ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append (startChars);
		char type = Character.MIN_VALUE;
		while (reader.hasRemaining()) {
			char c = reader.get();
			if (Character.isDigit(c) || c == decimalSeparator) {
				// still number
				sb.append(c);
			}
			else {
				for (int i=0; i<NumberElement.TYPES.length; i++) {
					if (c == NumberElement.TYPES[i]) {
						type = c;
						break;
					}
				}
				if (type != Character.MIN_VALUE) {
					return new NumberElement(sb.toString(), type, locale);
				}
				else if (Character.isLetter(c)) {
					return null;
				}
				else {
					reader.position(reader.position()-1);
					return new NumberElement(sb.toString(), locale);
				}
			}
		}
		return new NumberElement(sb.toString(), type, locale);
	}
}
