package hudson.zipscript.parser.template.element.directive.whiledir;

import java.io.StringWriter;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

public class EndWhileDirective extends NestableDirective {

	public String contents;

	public EndWhileDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[/#while]";
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		throw new ExecutionException("Invalid while directive");
	}
}