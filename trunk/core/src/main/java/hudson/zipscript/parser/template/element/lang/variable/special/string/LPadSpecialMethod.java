package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;


public class LPadSpecialMethod implements SpecialMethod {

	private Element padding;

	public LPadSpecialMethod (Element[] vars) {
		if (null != vars && vars.length > 0) {
			padding = vars[0];
		}
	}

	public Object execute(Object source, ExtendedContext context) throws Exception {
		if (null == padding) return source;
		int paddingAmt = ((Number) padding.objectValue(context)).intValue();
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<paddingAmt; i++)
			sb.append(' ');
		sb.append(source);
		return sb.toString();
	}
}