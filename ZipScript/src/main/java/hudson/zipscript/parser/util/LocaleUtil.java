package hudson.zipscript.parser.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import sun.text.resources.LocaleData;

public class LocaleUtil {

	private static HashMap cachedLocaleData = new HashMap(2);
	public static String[] getLocaleDataArr (String property, Locale locale) {
		String[] data = (String[]) cachedLocaleData.get(locale);
		if (null == data) {
			ResourceBundle rb = LocaleData.getLocaleElements(locale);
			data = rb.getStringArray(property);
		}
		return data;
	}

	public static char getDecimalSeparator (Locale locale) {
		return getLocaleDataArr("NumberElements", locale)[0].charAt(0);
	}
}
