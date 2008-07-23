package hudson.zipscript.parser.template.data;

import hudson.zipscript.ResourceContainer;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;
import hudson.zipscript.resource.macrolib.MacroProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ParsingSession implements MacroProvider {

	private ParseParameters parameters;
	private Map unknownVariablePatterns;
	private Map inlineMacroDefinitions;
	private Map staticMacroImports;
	private Stack nestingStack;
	private Stack escapeMethodStack = new Stack();
	private boolean hideEscapeMethods = false;

	public ParsingSession (
			ParseParameters parameters) {
		this.parameters = parameters;
	}

	public ParsingSession clone (ParseParameters parameters) {
		ParsingSession session = new ParsingSession(parameters);
		Map m = null;
		if (null != unknownVariablePatterns) {
			m = new HashMap();
			m.putAll(unknownVariablePatterns);
			session.unknownVariablePatterns = m;
		}
		if (null != inlineMacroDefinitions) {
			m = new HashMap();
			m.putAll(inlineMacroDefinitions);
			session.inlineMacroDefinitions = m;
		}
		return session;
	}

	public ResourceContainer getResourceContainer () {
		return parameters.getResourceContainer();
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

	public void markValidVariablePattern (String pattern) {
		if (null != unknownVariablePatterns) {
			unknownVariablePatterns.remove(pattern);
		}
	}

	public void addInlineMacroDefinition (MacroDirective directive) {
		if (null == inlineMacroDefinitions)
			inlineMacroDefinitions = new HashMap();
		inlineMacroDefinitions.put(directive.getName(), directive);
	}

	public void addMacroImport (String namespace, String macroPath) {
		if (null == staticMacroImports)
			staticMacroImports = new HashMap();
		staticMacroImports.put(namespace, macroPath);
	}

	public String getMacroImportPath (String namespace) {
		if (null == staticMacroImports)
			return null;
		else
			return (String) staticMacroImports.get(namespace);
	}

	public MacroDirective getMacro (String name) {
		if (null == inlineMacroDefinitions) return null;
		else return (MacroDirective) inlineMacroDefinitions.get(name);
	}

	public Stack getNestingStack() {
		if (null == nestingStack) nestingStack = new Stack();
		return nestingStack;
	}

	public boolean isDebug () {
		return false;
	}

	public void addEscapeMethod (SpecialMethod sm) {
		if (hideEscapeMethods) return;
		escapeMethodStack.push(sm);
	}

	public SpecialMethod removeEscapeMethod (SpecialMethod sm) {
		if (hideEscapeMethods) return null;
		if (escapeMethodStack.size() == 0) return null;
		SpecialMethod sm1 = (SpecialMethod) escapeMethodStack.pop();
		if (null == sm) return sm1;
		if (sm1 != sm) return null;
		else return sm;
	}

	public Stack getEscapeMethods () {
		return escapeMethodStack;
	}

	public boolean isHideEscapeMethods() {
		return hideEscapeMethods;
	}

	public void setHideEscapeMethods(boolean hideEscapeMethods) {
		this.hideEscapeMethods = hideEscapeMethods;
	}

	public Map getStaticMacroImports() {
		return staticMacroImports;
	}
}