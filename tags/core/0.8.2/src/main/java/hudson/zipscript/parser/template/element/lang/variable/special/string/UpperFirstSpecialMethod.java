package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;
import hudson.zipscript.parser.util.StringUtil;

public class UpperFirstSpecialMethod implements SpecialMethod {

	public static final UpperFirstSpecialMethod INSTANCE = new UpperFirstSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return StringUtil.firstLetterUpperCase((String) source);
	}
}