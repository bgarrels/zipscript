package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import java.util.Collection;
import java.util.Iterator;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;


public class ContainsSpecialMethod implements SpecialMethod {

	private Element checkElement;

	public ContainsSpecialMethod (Element[] vars) {
		if (null != vars && vars.length > 0) {
			checkElement = vars[0];
		}
	}

	public Object execute(Object source, ZSContext context) throws Exception {
		Object check = checkElement.objectValue(context);
		if (source instanceof Object[]) {
			Object[] arr = (Object[]) source;
			for (int i=0; i<arr.length; i++)
				if (arr[i].equals(check)) return Boolean.TRUE;
			return Boolean.FALSE;
		}
		else if (source instanceof Collection) {
			Collection c = (Collection) source;
			for (Iterator i=c.iterator(); i.hasNext(); )
				if (i.next().equals(check))
					return Boolean.TRUE;
			return Boolean.FALSE;
		}
		else return Boolean.FALSE;
	}
}