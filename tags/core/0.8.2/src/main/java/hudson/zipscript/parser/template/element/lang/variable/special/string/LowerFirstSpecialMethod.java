package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;
import hudson.zipscript.parser.util.StringUtil;

public class LowerFirstSpecialMethod implements SpecialMethod {

	public static final LowerFirstSpecialMethod INSTANCE = new LowerFirstSpecialMethod();

	public Object execute(Object source, ZSContext context) throws Exception {
		return StringUtil.firstLetterLowerCase((String) source);
	}
}