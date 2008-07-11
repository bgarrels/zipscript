package hudson.zipscript.parser.util;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LocaleUtil {

	public static char getDecimalSeparator (Locale locale) {
		return new DecimalFormatSymbols(locale).getDecimalSeparator();
	}
}
