package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroAttribute;
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

	public MacroInstanceEntityContext (
			ZSContext preMacroContext, Map additionalContextEntries, List macroAttributes) {
		this.preMacroContext = preMacroContext;
		this.additionalContextEntries = additionalContextEntries;
		this.macroAttributes = macroAttributes;
	}

	public Object get(String key) {
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

	public void put(String key, Object value) {
		additionalContextEntries.put(key, value);
	}

	public void putGlobal(String key, Object value) {
		preMacroContext.putGlobal(key, value);
	}

	public Object remove(String key) {
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
				MacroAttribute attribute = (MacroAttribute) i.next();
				Element val = attribute.getDefaultValue();
				if (null != val) {
					additionalContextEntries.put(attribute.getName(), val.objectValue(context));
				}
			}
		}
	}
}