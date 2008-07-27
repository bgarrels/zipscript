package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;

public class FirstSpecialMethod extends SequenceSpecialMethod {

	public Object execute(
			Object source, SequenceAdapter sequenceAdapter, ExtendedContext context) {
		return sequenceAdapter.getItemAt(0, source);
	}
}
