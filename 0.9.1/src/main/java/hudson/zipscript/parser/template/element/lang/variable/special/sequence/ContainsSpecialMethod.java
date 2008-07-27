package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;


public class ContainsSpecialMethod extends SequenceSpecialMethod{

	private Element checkElement;

	public ContainsSpecialMethod (Element[] vars) {
		if (null != vars && vars.length > 0) {
			checkElement = vars[0];
		}
	}

	public Object execute(
			Object source, SequenceAdapter sequenceAdapter, ExtendedContext context) {
		Object check = checkElement.objectValue(context);
		return new Boolean(sequenceAdapter.contains(check, source));
	}
}