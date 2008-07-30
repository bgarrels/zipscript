package hudson.zipscript.parser.template.element.lang.variable.special.map;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.MapAdapter;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public abstract class MapSpecialMethod implements SpecialMethod {

	private MapAdapter mapAdapter;

	public final Object execute(Object source, RetrievalContext retrievalContext,
			String contextHint, ExtendedContext context)
			throws Exception {
		if (null == mapAdapter) {
			mapAdapter = context.getResourceContainer().getVariableAdapterFactory().getMapAdapter(source);
		}
		return execute(source, mapAdapter, retrievalContext, contextHint, context);
	}

	protected abstract Object execute (
			Object source, MapAdapter mapAdapter, RetrievalContext retrievalContext, String contextHint, ExtendedContext context);

	public RetrievalContext getExpectedType() {
		return RetrievalContext.HASH;
	}
}
