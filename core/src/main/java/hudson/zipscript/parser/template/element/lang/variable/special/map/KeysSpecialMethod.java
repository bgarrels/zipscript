package hudson.zipscript.parser.template.element.lang.variable.special.map;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.MapAdapter;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

public class KeysSpecialMethod extends MapSpecialMethod {

	public Object execute(
			Object source, MapAdapter mapAdapter, RetrievalContext retrievalContext, ExtendedContext context) {
		return mapAdapter.getKeys(source);
	}
}
