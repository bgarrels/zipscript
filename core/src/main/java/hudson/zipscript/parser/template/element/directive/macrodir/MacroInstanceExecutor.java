package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ZSContext;

public class MacroInstanceExecutor {

	private MacroInstanceDirective macroInstance;
	private ZSContext context;

	public MacroInstanceExecutor (
			MacroInstanceDirective macroInstance, ZSContext context) {
		this.macroInstance = macroInstance;
		this.context = context;
	}

	public String toString() {
		return macroInstance.getNestedContent(context);
	}
}
