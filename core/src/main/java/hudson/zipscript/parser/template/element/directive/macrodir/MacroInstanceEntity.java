package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.MacroInstanceEntityContext;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.context.ZSContextRequiredGetter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MacroInstanceEntity implements ZSContextRequiredGetter{

	private MacroInstanceDirective macroInstance;
	private MacroInstanceEntityContext context;
	private boolean initialized = false;

	public MacroInstanceEntity (
			MacroInstanceDirective macroInstance, ZSContext context, Map additionalContextEntries) {
		this.macroInstance = macroInstance;

		Map clonedEntries = new HashMap();
		// save any looping context values
		if (null != additionalContextEntries)
			for (Iterator i=additionalContextEntries.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry entry = (Map.Entry) i.next();
				clonedEntries.put(entry.getKey(), entry.getValue());
			}
		this.context = new MacroInstanceEntityContext(
				context, clonedEntries, macroInstance.getAttributes(), macroInstance.getMacroDefinitionAttributes());
	}

	public MacroInstanceDirective getMacroInstance() {
		return macroInstance;
	}


	public Object get(String key, ZSContext context) {
		// FIXME is there a better way to do this?
		if (key.equals("body")) {
			initialize(context, true);
			return macroInstance.getNestedContent(this.context);
		}
		else if (key.equals("header")) {
			initialize(context, false);
			return macroInstance.getHeader();
		}
		else if (key.equals("footer")) {
			initialize(context, false);
			return macroInstance.getFooter();
		}
		else {
			initialize(context, false);
			return this.context.get(key);
		}
	}

	public String toString() {
		if (null != macroInstance)
			return macroInstance.toString();
		else return super.toString();
	}

	public void put (String key, Object value) {
		this.context.put(key, value, true);
	}

	public MacroInstanceEntityContext getContext() {
		return context;
	}

	private void initialize (ZSContext context, boolean force) {
		if (!initialized || force) {
			this.context.setPostMacroContext(context);
			initialized = true;
		}
	}
}