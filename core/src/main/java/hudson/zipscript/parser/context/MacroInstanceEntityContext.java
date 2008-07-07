package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDefinitionAttribute;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAttribute;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MacroInstanceEntityContext implements ZSContext {

	private ZSContext preMacroContext;
	private ZSContext postMacroContext;
	private Map additionalContextEntries;
	private List macroAttributes;
	private List macroDefinitionAttributes;

	public MacroInstanceEntityContext (
			ZSContext preMacroContext, Map additionalContextEntries, List macroAttributes, List macroDefinitionAttributes) {
		this.preMacroContext = preMacroContext;
		this.additionalContextEntries = additionalContextEntries;
		this.macroAttributes = macroAttributes;
		this.macroDefinitionAttributes = macroDefinitionAttributes;
	}

	public Object get(Object key) {
		Object obj = additionalContextEntries.get(key);
		if (null != postMacroContext && null == obj) obj = postMacroContext.get(key);
		if (null == obj) obj = preMacroContext.get(key);
		return obj;
	}

	public void setPostMacroContext (ZSContext context) {
		this.postMacroContext = context;
		initializeMacroAttributes(this);
	}

	public Iterator getKeys() {
		return preMacroContext.getKeys();
	}

	public Locale getLocale() {
		return preMacroContext.getLocale();
	}

	public MacroManager getMacroManager() {
		return preMacroContext.getMacroManager();
	}

	public ParsingSession getParsingSession() {
		return preMacroContext.getParsingSession();
	}

	public ZSContext getRootContext() {
		return preMacroContext.getRootContext();
	}

	public void put(Object key, Object value, boolean travelUp) {
		additionalContextEntries.put(key, value);
	}

	public void putGlobal(Object key, Object value) {
		preMacroContext.putGlobal(key, value);
	}

	public Object remove(Object key) {
		return postMacroContext.remove(key);
	}

	public void setMacroManager(MacroManager macroManager) {
		preMacroContext.setMacroManager(macroManager);
	}

	public void setParsingSession(ParsingSession session) {
		preMacroContext.setParsingSession(session);
	}

	public void appendMacroNestedAttributes(Map m) {
		// if we are using this context we are in a body statement
		// and not in a macro definition
	}

	private void initializeMacroAttributes (ZSContext context) {
		if (null != macroAttributes) {
			// save any specified attribute values
			for (java.util.Iterator i=macroAttributes.iterator(); i.hasNext(); ) {
				MacroInstanceAttribute attribute = (MacroInstanceAttribute) i.next();
				Element val = attribute.getValue();
				if (null != val) {
					additionalContextEntries.put(attribute.getName(), val.objectValue(context));
				}
			}
			if (null != macroDefinitionAttributes) {
				for (Iterator i=macroDefinitionAttributes.iterator(); i.hasNext(); ) {
					MacroDefinitionAttribute attribute = (MacroDefinitionAttribute) i.next();
					if (null != attribute.getDefaultValue() && null == additionalContextEntries.get(attribute.getName())) {
						Object val = attribute.getDefaultValue().objectValue(this);
						if (null != val) {
							additionalContextEntries.put(attribute.getName(), val);
						}
					}
				}
			}
		}
	}
}