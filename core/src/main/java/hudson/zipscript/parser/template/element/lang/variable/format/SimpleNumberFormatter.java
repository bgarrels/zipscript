package hudson.zipscript.parser.template.element.lang.variable.format;

import hudson.zipscript.parser.context.ZSContext;

import java.text.DecimalFormat;
import java.util.Locale;

public class SimpleNumberFormatter implements Formatter {

	private DecimalFormat formatter;

	public SimpleNumberFormatter (String format, Locale defaultLocale) {
		this.formatter = new DecimalFormat(format);
	}

	public String format(Object object, ZSContext context) throws Exception {
		return formatter.format((Number) object);
	}
}