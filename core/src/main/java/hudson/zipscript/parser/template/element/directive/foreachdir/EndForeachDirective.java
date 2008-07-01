package hudson.zipscript.parser.template.element.directive.foreachdir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.Writer;

public class EndForeachDirective extends NestableDirective {

	public String contents;

	public EndForeachDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[/#foreach]";
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		throw new ExecutionException("Invalid foreach directive", this);
	}
}
