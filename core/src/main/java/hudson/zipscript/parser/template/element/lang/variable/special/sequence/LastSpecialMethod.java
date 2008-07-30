package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;

public class LastSpecialMethod extends SequenceSpecialMethod {

	public Object execute(
			Object source, SequenceAdapter sequenceAdapter, RetrievalContext retrievalContext,
			String contextHint, ExtendedContext context) {
		int length = sequenceAdapter.getLength(source);
		if (length == 0) return null;
		return sequenceAdapter.getItemAt(length-1, source, retrievalContext, contextHint);
	}
}