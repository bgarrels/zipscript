package hudson.zipscript.parser.template.element.directive.macrodir;

import java.util.List;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;

public class MacroInstanceExecutor implements ToStringWithContextElement {

	private MacroInstanceDirective macroInstance;
	private ZSContext context;

	public MacroInstanceExecutor (
			MacroInstanceDirective macroInstance, ZSContext context) {
		this.macroInstance = macroInstance;
		this.context = context;
	}

	public List getChildren () {
		return macroInstance.getChildren();
	}

	public String toString() {
		return macroInstance.getNestedContent(context);
	}

	public String toString(ZSContext context) {
		return macroInstance.getNestedContent(context);
	}
}
