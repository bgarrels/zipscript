package hudson.zipscript.parser.template.element.lang.variable.format;

import hudson.zipscript.parser.context.ZSContext;

public interface Formatter {

	public String format (Object object, ZSContext context) throws Exception;
}
