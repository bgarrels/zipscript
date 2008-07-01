package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.Writer;
import java.util.List;

public class EndMacroInstanceDirective extends NestableDirective {

	public String name;

	public EndMacroInstanceDirective (String name) {
		this.name = name;
	}

	public String toString() {
		return "[/@" + getName() + "]";
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		throw new ExecutionException("Invalid macro directive", this);
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}

	public String getName() {
		return name;
	}
}
