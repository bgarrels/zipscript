package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.MacroInstanceEntityContext;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;

import java.util.List;

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

	public String toString(ZSContext context) {
		if (context instanceof MacroInstanceEntityContext)
			((MacroInstanceEntityContext) context).setPostMacroContext(context);
		return macroInstance.getNestedContent(context);
	}

	public MacroInstanceDirective getMacroInstance() {
		return macroInstance;
	}
}