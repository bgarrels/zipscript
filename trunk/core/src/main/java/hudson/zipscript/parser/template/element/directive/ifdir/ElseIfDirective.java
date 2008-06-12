package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.StringWriter;

public class ElseIfDirective extends NestableDirective {

	private String contents;

	public ElseIfDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[#elseif " + contents + "]";
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		throw new ExecutionException("Invalid if directive", this);
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
