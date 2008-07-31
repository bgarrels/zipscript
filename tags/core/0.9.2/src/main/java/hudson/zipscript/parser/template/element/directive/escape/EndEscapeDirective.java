package hudson.zipscript.parser.template.element.directive.escape;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.Writer;

public class EndEscapeDirective extends NestableDirective {

	public EndEscapeDirective (String contents) {
	}

	public String toString() {
		return "[/#escape]";
	}

	public void merge(ExtendedContext context, Writer sw) throws ExecutionException {
		throw new ExecutionException("Invalid escape directive", this);
	}
}
