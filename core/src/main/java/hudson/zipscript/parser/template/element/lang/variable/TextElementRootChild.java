package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

public class TextElementRootChild implements VariableChild {

	private String text;
	private RetrievalContext retrievalContext;

	public TextElementRootChild (String text) {
		this.text = text;
	}

	public Object execute(Object parent, ExtendedContext context) {
		return text;
	}

	public boolean shouldReturnSomething() {
		return true;
	}

	public String toString() {
		return text;
	}

	public String getPropertyName() {
		return text;
	}

	public RetrievalContext getRetrievalContext() {
		return retrievalContext;
	}

	public void setRetrievalContext(RetrievalContext retrievalContext) {
		this.retrievalContext = retrievalContext;
	}
}