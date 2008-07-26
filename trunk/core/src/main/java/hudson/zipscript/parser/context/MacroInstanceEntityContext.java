package hudson.zipscript.parser.context;

import hudson.zipscript.ResourceContainer;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.ElementAttribute;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDefinitionAttribute;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MacroInstanceEntityContext implements ExtendedContext {

	private ExtendedContext preMacroContext;
	private ExtendedContext postMacroContext;
	private Map additionalContextEntries;
	private List macroAttributes;
	private List macroDefinitionAttributes;
	private Element scopedElement;

	public MacroInstanceEntityContext (
			Element scopedElement, ExtendedContext preMacroContext, Map additionalContextEntries, List macroAttributes, List macroDefinitionAttributes) {
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

	public void setPostMacroContext (ExtendedContext context) {
		this.postMacroContext = context;
		initializeMacroAttributes(this);
	}

	public Set getKeys() {
		return preMacroContext.getKeys();
	}

	public Locale getLocale() {
		return preMacroContext.getLocale();
	}

	public void setLocale(Locale locale) {
		preMacroContext.setLocale(locale);
	}

	public ResourceContainer getResourceContainer() {
		return preMacroContext.getResourceContainer();
	}

	public void setResourceContainer(ResourceContainer resourceContainer) {
		preMacroContext.setResourceContainer(resourceContainer);
		
	}

	public ParsingSession getParsingSession() {
		return preMacroContext.getParsingSession();
	}

	public ExtendedContext getRootContext() {
		return preMacroContext.getRootContext();
	}

	public void put(Object key, Object value, boolean travelUp) {
		additionalContextEntries.put(key, value);
	}

	public void put(Object key, Object value) {
		this.put(key, value, false);
	}

	public void putGlobal(Object key, Object value) {
		preMacroContext.putGlobal(key, value);
	}

	public Object remove(Object key) {
		return postMacroContext.remove(key);
	}

	public void setParsingSession(ParsingSession session) {
		preMacroContext.setParsingSession(session);
	}

	public void appendMacroNestedAttributes(Map m) {
		// if we are using this context we are in a body statement
		// and not in a macro definition
	}

	private void initializeMacroAttributes (ExtendedContext context) {
		if (null != macroAttributes) {
			// save any specified attribute values
			for (java.util.Iterator i=macroAttributes.iterator(); i.hasNext(); ) {
				ElementAttribute attribute = (ElementAttribute) i.next();
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

	public void addToElementScope(List nestingStack) {
		preMacroContext.addToElementScope(nestingStack);
		nestingStack.add(scopedElement);
	}

	public void setInitialized(boolean val) {
		preMacroContext.setInitialized(val);
	}

	public boolean isInitialized() {
		return postMacroContext.isInitialized();
	}

	public MacroDirective getMacro (String name) {
		return getParsingSession().getMacro(name);
	}

	public String getMacroImportPath (String namespace) {
		return preMacroContext.getMacroImportPath(namespace);
	}

	public void addMacroImport(String namespace, String macroPath) {
		preMacroContext.addMacroImport(namespace, macroPath);
	}
}