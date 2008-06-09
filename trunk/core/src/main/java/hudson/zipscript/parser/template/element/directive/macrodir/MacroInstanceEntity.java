package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.Element;

import java.util.HashMap;
import java.util.Iterator;

public class MacroInstanceEntity implements ZSContext {

	private MacroInstanceDirective macroInstance;
	private java.util.Map values;

	public MacroInstanceEntity (
			MacroInstanceDirective macroInstance, ZSContext context) {
		this.macroInstance = macroInstance;
		values = new HashMap(macroInstance.getAttributes().size());
		for (java.util.Iterator i=macroInstance.getAttributes().iterator(); i.hasNext(); ) {
			MacroAttribute attribute = (MacroAttribute) i.next();
			Element val = attribute.getDefaultValue();
			if (null != val)
				values.put(attribute.getName(), val.objectValue(context));
		}
	}

	public MacroInstanceDirective getMacroInstance() {
		return macroInstance;
	}

	public java.util.Map getValues() {
		return values;
	}

	public Object get(String key) {
		return values.get(key);
	}

	public Iterator getKeys() {
		return null;
	}

	public void put(String key, Object value) {
	}

	public Object remove(String key) {
		return null;
	}
}
