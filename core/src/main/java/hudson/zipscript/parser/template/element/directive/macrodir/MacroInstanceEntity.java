package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.BiContext;
import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.context.ZSContextRequiredGetter;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.LoopingDirective;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MacroInstanceEntity implements ZSContextRequiredGetter{

	private MacroInstanceDirective macroInstance;
	private java.util.Map values;
	private Map additionalContextAttributes;

	public MacroInstanceEntity (
			MacroInstanceDirective macroInstance, ZSContext context) {
		this.macroInstance = macroInstance;
		// if we are in a looping scoped context - copy the context values
		if (isLooping(context)) {
			Map values = ((NestedContextWrapper) context).getMap();
			additionalContextAttributes = new HashMap(values.size());
			for (Iterator i=values.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry entry = (Map.Entry) i.next();
				additionalContextAttributes.put(entry.getKey(), entry.getValue());
			}
		}
		
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
			if (null == additionalContextAttributes)
				return macroInstance.getNestedContent(context);
			else
				return macroInstance.getNestedContent(
						new BiContext(this.additionalContextAttributes, context));
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

	public void put (String key, Object value) {
		values.put(key, value);
	}

	private boolean isLooping (ZSContext context) {
		while (context instanceof NestedContextWrapper) {
			NestedContextWrapper nested = (NestedContextWrapper) context;
			if (nested.getScopedElement() instanceof LoopingDirective)
				return true;
			else
				context = nested.getParentContext();
		}
		return false;
	}
}