package hudson.zipscript.parser.template.element.lang.variable.special.sequence;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.SequenceAdapter;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public abstract class SequenceSpecialMethod implements SpecialMethod {

	private SequenceAdapter sequenceAdapter;

	public final Object execute(Object source, RetrievalContext retrievalContext,
			String contextHint, ExtendedContext context)
			throws Exception {
		if (null == sequenceAdapter) {
			sequenceAdapter = context.getResourceContainer().getVariableAdapterFactory().getSequenceAdapter(source);
		}
		return execute(source, sequenceAdapter, retrievalContext, contextHint, context);
	}

	protected abstract Object execute (
			Object source, SequenceAdapter sequenceAdapter, RetrievalContext retrievalContext,
			String contextHint, ExtendedContext context);

	public RetrievalContext getExpectedType() {
		return RetrievalContext.SEQUENCE;
	}
}
