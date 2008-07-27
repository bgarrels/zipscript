package hudson.zipscript.parser.template.element.lang.variable.special.map;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.MapAdapter;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;

public abstract class MapSpecialMethod implements SpecialMethod {

	private MapAdapter mapAdapter;

	public final Object execute(Object source, ExtendedContext context)
			throws Exception {
		if (null == mapAdapter) {
			mapAdapter = context.getResourceContainer().getVariableAdapterFactory().getMapAdapter(source);
		}
		return execute(source, mapAdapter, context);
	}

	protected abstract Object execute (
			Object source, MapAdapter mapAdapter, ExtendedContext context);
}
