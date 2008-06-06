package hudson.zipscript.parser.template.element.directive.ifdir;

import java.io.StringWriter;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

public class ElseDirective extends NestableDirective {

	public String contents;

	public ElseDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[#else]";
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		throw new ExecutionException("Invalid if directive");
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
