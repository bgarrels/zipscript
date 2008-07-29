package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.VariableAdapterFactory;

public class RootChild implements VariableChild {

	private short TYPE_STANDARD = 1;
	private short TYPE_RESERVED = 2;

	public String name;
	private short type = TYPE_STANDARD;
	private RetrievalContext retrievalContext;

	public RootChild (String name, VariableAdapterFactory variableAdapterFactory) {
		this.name = name;
		for (int i=0; i<variableAdapterFactory.getReservedContextAttributes().length; i++) {
			if (name.equals(variableAdapterFactory.getReservedContextAttributes()[i])) {
				type = TYPE_RESERVED;
			}
		}
	}

	public Object execute(Object parent, ExtendedContext context) {
		if (type == TYPE_RESERVED)
			return context.getRootContext().get(name, retrievalContext);
		else
			return context.get(name, retrievalContext);
	}

	public String getPropertyName() {
		return name;
	}

	public boolean shouldReturnSomething() {
		return true;
	}

	public String toString() {
		return name;
	}

	public RetrievalContext getRetrievalContext() {
		return retrievalContext;
	}

	public void setRetrievalContext(RetrievalContext retrievalContext) {
		this.retrievalContext = retrievalContext;
	}
}