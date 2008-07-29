package hudson.zipscript.parser.template.element.lang.variable.special.date;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.Date;


public class JSDateTimeSpecialMethod implements SpecialMethod {

	public static final JSDateTimeSpecialMethod INSTANCE = new JSDateTimeSpecialMethod();

	public Object execute(
			Object source, RetrievalContext retrievalContext, ExtendedContext context) throws Exception {
		return "new Date(" + Long.toString(((Date) source).getTime()) + ")";
	}

	public RetrievalContext getExpectedType() {
		return RetrievalContext.TEXT;
	}
}