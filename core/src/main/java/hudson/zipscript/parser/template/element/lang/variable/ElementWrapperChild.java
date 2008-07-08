package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;

public class ElementWrapperChild implements VariableChild {

	private hudson.zipscript.parser.template.element.Element element;
	public ElementWrapperChild (Element element) {
		this.element = element;
	}

	public Object execute(Object parent, ExtendedContext context) throws ExecutionException {
		return element.objectValue(context);
	}

	public String getPropertyName() {
		return element.toString();
	}

	public boolean shouldReturnSomething() {
		return true;
	}

	public String toString() {
		return element.toString();
	}
}
