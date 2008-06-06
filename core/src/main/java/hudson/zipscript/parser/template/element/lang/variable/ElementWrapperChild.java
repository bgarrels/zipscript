package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;

public class ElementWrapperChild implements VariableChild {

	private hudson.zipscript.parser.template.element.Element element;
	public ElementWrapperChild (Element element) {
		this.element = element;
	}

	public Object execute(Object parent, ZSContext context) throws ExecutionException {
		return element.objectValue(context);
	}

	public String getPropertyName() {
		return element.toString();
	}

	public String toString() {
		return element.toString();
	}
}
