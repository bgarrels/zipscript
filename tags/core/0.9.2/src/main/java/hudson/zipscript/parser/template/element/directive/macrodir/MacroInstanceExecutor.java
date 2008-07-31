package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.context.MacroInstanceEntityContext;
import hudson.zipscript.parser.template.element.NoAutoEscapeElement;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;

import java.io.Writer;
import java.util.List;

public class MacroInstanceExecutor implements ToStringWithContextElement, NoAutoEscapeElement {

	private MacroOrientedElement macroInstance;

	public MacroInstanceExecutor (
			MacroOrientedElement macroInstance) {
		this.macroInstance = macroInstance;
	}

	public List getChildren () {
		return macroInstance.getChildren();
	}

	public String toString(ExtendedContext context) {
		if (context instanceof MacroInstanceEntityContext)
			((MacroInstanceEntityContext) context).setPostMacroContext(context);
		return macroInstance.getNestedContent(context);
	}

	public void append(ExtendedContext context, Writer writer) {
		if (context instanceof MacroInstanceEntityContext)
			((MacroInstanceEntityContext) context).setPostMacroContext(context);
		macroInstance.writeNestedContent(context, writer);
	}

	public MacroOrientedElement getMacroInstance() {
		return macroInstance;
	}
}