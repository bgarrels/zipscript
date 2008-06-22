package hudson.zipscript.parser.template.element.lang.variable.special.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;


public class JSDateSpecialMethod implements SpecialMethod {

	public static final JSDateSpecialMethod INSTANCE = new JSDateSpecialMethod();
	SimpleDateFormat sdf = new SimpleDateFormat("'new Date('yyyy, MM, dd')'");

	public Object execute(Object source, ZSContext context) throws Exception {
		return sdf.format((Date) source);
	}
}