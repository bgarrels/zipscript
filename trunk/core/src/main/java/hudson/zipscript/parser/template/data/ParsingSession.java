package hudson.zipscript.parser.template.data;

import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;

import java.util.HashMap;
import java.util.Map;

public class ParsingSession {

	private ParseParameters parameters;
	private Map unknownVariablePatterns;
	private Map inlineMacroDefinitions;

	public ParsingSession (ParseParameters parameters) {
		this.parameters = parameters;
	}

	public ParseParameters getParameters () {
		return parameters;
	}

	public void setParameters (ParseParameters parameters) {
		this.parameters = parameters;
	}

	public boolean isVariablePatternRecognized (String pattern) {
		if (null == unknownVariablePatterns)
			unknownVariablePatterns = new HashMap();
		return (null != unknownVariablePatterns.get(pattern));
	}

	public void setReferencedVariable (String pattern) {
		if (null == unknownVariablePatterns)
			unknownVariablePatterns = new HashMap();
		unknownVariablePatterns.put(pattern, Boolean.TRUE);
	}

	public void addInlineMacroDefinition (MacroDirective directive) {
		if (null == inlineMacroDefinitions)
			inlineMacroDefinitions = new HashMap();
		inlineMacroDefinitions.put(directive.getName(), directive);
	}

	public MacroDirective getMacroDirective (String name) {
		if (null == inlineMacroDefinitions) return null;
		else return (MacroDirective) inlineMacroDefinitions.get(name);
	}
}