package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.StringWriter;

public class EndIfDirective extends NestableDirective {

	public String contents;

	public EndIfDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[/#if]";
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		throw new ExecutionException("Invalid if directive", this);
	}
}
