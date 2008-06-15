package hudson.zipscript.parser.template.element.lang.variable.format;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DefinedNumberFormatter implements Formatter {

	private String format;
	private Locale locale;
	private NumberFormat formatter;
	private Map localeFormatters;

	public DefinedNumberFormatter (String format, Locale locale) {
		this.format = format;
		this.locale = locale;
		if (format.equals("currency")) {
			formatter = NumberFormat.getCurrencyInstance(locale);
		}
		else if (format.equals("percent")) {
			formatter = NumberFormat.getPercentInstance(locale);
		}
		else {
			throw new ExecutionException("Undefined number format '" + format + "'", null);
		}
	}

	public String format(Object object, ZSContext context) throws Exception {
		if (null == context.getLocale() || null == this.locale
				|| this.locale.equals(context.getLocale())) {
			return formatter.format((Number) object);
		}
		else {
			if (null == localeFormatters) localeFormatters = new HashMap(2);
			NumberFormat formatter = null;
			if (format.equals("currency")) {
				formatter = NumberFormat.getCurrencyInstance(context.getLocale());
			}
			else if (format.equals("percent")) {
				formatter = NumberFormat.getPercentInstance(context.getLocale());
			}
			return formatter.format((Date) object);
		}
	}
}
