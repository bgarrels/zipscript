package hudson.zipscript.parser.template.element.group;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.StringWriter;

public class EndMapElement extends NestableDirective {

	

	public void merge(ZSContext context, StringWriter sw) {
		sw.append(']');
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("groups can not be evaluated as booleans");
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("groups can not be evaluated as objects");
	}

	public String toString() {
		return "]";
	}
}
