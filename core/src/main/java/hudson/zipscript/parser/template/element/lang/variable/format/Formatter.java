package hudson.zipscript.parser.template.element.lang.variable.format;

import hudson.zipscript.parser.context.ExtendedContext;

public interface Formatter {

	public String format (Object object, ExtendedContext context) throws Exception;
}
