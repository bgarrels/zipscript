package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.MacroInstanceEntityContext;
import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.context.ZSContextRequiredGetter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MacroInstanceEntity implements ZSContextRequiredGetter{

	private MacroInstanceDirective macroInstance;
	private MacroInstanceEntityContext context;

	public MacroInstanceEntity (
			MacroInstanceDirective macroInstance, ZSContext context, Map additionalContextEntries) {
		this.macroInstance = macroInstance;

		Map clonedEntries = new HashMap();
		// save any looping context values
		for (Iterator i=additionalContextEntries.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry entry = (Map.Entry) i.next();
			clonedEntries.put(entry.getKey(), entry.getValue());
		}
		this.context = new MacroInstanceEntityContext(
				context, clonedEntries, macroInstance.getAttributes());
	}

	public MacroInstanceDirective getMacroInstance() {
		return macroInstance;
	}


	public Object get(String key, ZSContext context) {
		this.context.setPostMacroContext(context);
		if (key.equals("body"))
			return macroInstance.getNestedContent(this.context);
		else {
			return this.context.get(key);
		}
	}

	public String toString() {
		if (null != macroInstance)
			return macroInstance.toString();
		else return super.toString();
	}

	public void put (String key, Object value) {
		this.context.put(key, value);
	}

	private boolean isNested (ZSContext context) {
		if (context instanceof NestedContextWrapper) return true;
		else return false;
	}

	public MacroInstanceEntityContext getContext() {
		return context;
	}
}