package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.Writer;

public class EndMacroDirective extends NestableDirective {

	public String contents;

	public EndMacroDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[/#macro]";
	}

	public void merge(ExtendedContext context, Writer sw) throws ExecutionException {
		throw new ExecutionException("Invalid macro directive", this);
	}
}
