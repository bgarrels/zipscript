package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.context.ZSContextRequiredGetter;
import hudson.zipscript.parser.template.element.Element;

import java.util.HashMap;

public class MacroInstanceEntity implements ZSContextRequiredGetter{

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


	public Object get(String key, ZSContext context) {
		if (key.equals("body"))
			return macroInstance.getNestedContent(context);
		else {
			Object obj = values.get(key);
			if (null == obj) return context.get(key);
			else return obj;
		}
	}

	public String toString() {
		if (null != macroInstance)
			return macroInstance.toString();
		else return super.toString();
	}
}