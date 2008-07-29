package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.directive.NestableDirective;

import java.io.Writer;
import java.util.List;

public class EndTemplateDefinedParameter extends NestableDirective {

	private String name;
	private boolean isTemplateDefinedParameterInMacroDefinition;

	public EndTemplateDefinedParameter (String name) {
		this(name, false);
	}

	public EndTemplateDefinedParameter (String name, boolean isTemplateDefinedParameterInMacroDefinition) {
		this.name = name;
		this.isTemplateDefinedParameterInMacroDefinition = isTemplateDefinedParameterInMacroDefinition;
	}

	public String toString() {
		return "[/%" + getName() + "]";
	}

	public void merge(ExtendedContext context, Writer sw) throws ExecutionException {
		throw new ExecutionException("Invalid macro directive", this);
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}

	public String getName() {
		return name;
	}

	public boolean isTemplateDefinedParameterInMacroDefinition() {
		return isTemplateDefinedParameterInMacroDefinition;
	}
}