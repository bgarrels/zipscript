package hudson.zipscript.parser.template.element.lang.variable.special.string;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SplitSpecialMethod implements SpecialMethod {

	private Element splitToken;
	public SplitSpecialMethod (Element[] params) {
		this.splitToken = params[0];
	}

	public Object execute(Object source, ZSContext context) throws Exception {
		String s = source.toString();
		String split = splitToken.objectValue(context).toString();

		StringTokenizer st = new StringTokenizer(s, split);
		java.util.List l = new ArrayList();
		while (st.hasMoreElements())
			l.add(st.nextElement());
		return l.toArray(new String[l.size()]);
	}
}
