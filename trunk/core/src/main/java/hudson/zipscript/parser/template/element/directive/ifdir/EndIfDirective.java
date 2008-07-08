package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.Writer;

public class EndIfDirective extends NestableDirective {

	public String contents;

	public EndIfDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[/#if]";
	}

	public void merge(ExtendedContext context, Writer sw) throws ExecutionException {
		throw new ExecutionException("Invalid if directive", this);
	}
}
