package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;

public class LengthSpecialMethod extends SequenceSpecialMethod {

	public Object execute(
			Object source, SequenceAdapter sequenceAdapter, RetrievalContext retrievalContext,
			String contextHint, ExtendedContext context) {
		return new Integer(sequenceAdapter.getLength(source));
	}
}