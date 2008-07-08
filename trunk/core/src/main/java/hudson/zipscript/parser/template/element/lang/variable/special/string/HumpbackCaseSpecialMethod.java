package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;
import hudson.zipscript.parser.util.StringUtil;

public class HumpbackCaseSpecialMethod implements SpecialMethod {

	public static final HumpbackCaseSpecialMethod INSTANCE = new HumpbackCaseSpecialMethod();

	public Object execute(Object source, ExtendedContext context) throws Exception {
		return StringUtil.humpbackCase((String) source);
	}
}