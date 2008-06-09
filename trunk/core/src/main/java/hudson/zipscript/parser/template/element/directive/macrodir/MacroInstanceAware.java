package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ZSContext;

import java.util.List;

public interface MacroInstanceAware {

	public void getMacroInstances (ZSContext context, List list); 
}
