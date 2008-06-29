package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;

public class RootChild implements VariableChild {

	public String name;

	public RootChild (String name) {
		this.name = name;
	}

	public Object execute(Object parent, ZSContext context) {
		return context.get(name);
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
}