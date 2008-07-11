package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;

public class TextElementRootChild implements VariableChild {

	public String text;

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
}