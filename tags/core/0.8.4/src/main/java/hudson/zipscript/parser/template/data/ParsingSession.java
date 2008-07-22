package hudson.zipscript.parser.template.data;

import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.lang.variable.special.SpecialMethod;
import hudson.zipscript.resource.macrolib.MacroManager;
import hudson.zipscript.resource.macrolib.MacroProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ParsingSession implements MacroProvider {

	private ParseParameters parameters;
	private Map unknownVariablePatterns;
	private Map inlineMacroDefinitions;
	private Stack nestingStack;
	private Stack escapeMethodStack = new Stack();
	private MacroManager macroManager;
	private boolean hideEscapeMethods = false;

	public ParsingSession (
			ParseParameters parameters, MacroManager macroManager) {
		this.parameters = parameters;
		this.macroManager = macroManager;
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

	public MacroDirective getMacro (String name) {
		if (null == inlineMacroDefinitions) return null;
		else return (MacroDirective) inlineMacroDefinitions.get(name);
	}

	public Stack getNestingStack() {
		if (null == nestingStack) nestingStack = new Stack();
		return nestingStack;
	}

	public MacroManager getMacroManager() {
		return macroManager;
	}

	public void setMacroManager(MacroManager macroManager) {
		this.macroManager = macroManager;
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
}