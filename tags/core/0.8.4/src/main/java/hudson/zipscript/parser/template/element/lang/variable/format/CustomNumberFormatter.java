package hudson.zipscript.parser.template.element.lang.variable.format;

import hudson.zipscript.parser.context.ExtendedContext;

import java.text.DecimalFormat;
import java.util.Locale;

public class CustomNumberFormatter implements Formatter {

	private DecimalFormat formatter;

	public CustomNumberFormatter (String format, Locale defaultLocale) {
		this.formatter = new DecimalFormat(format);
	}

	public String format(Object object, ExtendedContext context) throws Exception {
		return formatter.format((Number) object);
	}
}