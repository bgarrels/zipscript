package hudson.zipscript.parser.template.element.directive;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.template.element.ElementStackAware;

import java.util.Stack;


public abstract class AbstractDirective extends AbstractElement implements Directive, ElementStackAware {

	private Stack elementStack;

	public Object objectValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("Directives are only applicable for template parsing", this);
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("Directives are only applicable for template parsing", this);
	}

	public Stack getElementStack() {
		return elementStack;
	}

	public void setElementStack(Stack elementStack) {
		this.elementStack = elementStack;
	}
}