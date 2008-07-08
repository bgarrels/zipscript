package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.MacroInstanceEntityContext;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;

import java.io.Writer;
import java.util.List;

public class MacroInstanceExecutor implements ToStringWithContextElement {

	private MacroInstanceDirective macroInstance;

	public MacroInstanceExecutor (
			MacroInstanceDirective macroInstance) {
		this.macroInstance = macroInstance;
	}

	public List getChildren () {
		return macroInstance.getChildren();
	}

	public String toString(ZSContext context) {
		if (context instanceof MacroInstanceEntityContext)
			((MacroInstanceEntityContext) context).setPostMacroContext(context);
		return macroInstance.getNestedContent(context);
	}

	public void toString(ZSContext context, Writer writer) {
		if (context instanceof MacroInstanceEntityContext)
			((MacroInstanceEntityContext) context).setPostMacroContext(context);
		macroInstance.writeNestedContent(context, writer);
	}

	public MacroInstanceDirective getMacroInstance() {
		return macroInstance;
	}
}