package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.Writer;

public class ElseDirective extends NestableDirective {

	public String contents;

	public ElseDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[#else]";
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		throw new ExecutionException("Invalid if directive", this);
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
