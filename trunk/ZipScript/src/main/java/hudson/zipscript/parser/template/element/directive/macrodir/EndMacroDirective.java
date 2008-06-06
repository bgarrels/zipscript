package hudson.zipscript.parser.template.element.directive.macrodir;

import java.io.StringWriter;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

public class EndMacroDirective extends NestableDirective {

	public String contents;

	public EndMacroDirective (String contents) {
		this.contents = contents;
	}

	public String toString() {
		return "[/#macro]";
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		throw new ExecutionException("Invalid macro directive");
	}
}
