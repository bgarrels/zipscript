package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.lang.AssignmentElement;
import hudson.zipscript.parser.template.element.lang.TextElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MacroDirective extends NestableElement {

	private String contents;
	private String name;
	private List attributes = new ArrayList();
	private Map attributeMap = new HashMap();
	

	public MacroDirective (String contents, ParsingSession session, int contentPosition) throws ParseException {
		this.contents = contents;
		parseContents(contents, session, contentPosition);
		session.addInlineMacroDefinition(this);
	}

	protected void parseContents (String contents, ParsingSession session, int contentPosition) throws ParseException {
		java.util.List elements = parseElements(contents, session, contentPosition);
		if (elements.size() == 0)
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Macro name was not specified");
		Element e;
		e = (Element) elements.remove(0);
		if (e instanceof SpecialStringElement)
			name = ((SpecialStringElement) e).getTokenValue();
		// validate name
		for (int i=0; i<name.length(); i++) {
			char c = name.charAt(i);
			if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-'))
				throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid macro name '" + name + "'");
		}

		// look for attributes
		while (true) {
			MacroAttribute attribute = getAttribute(elements, session);
			if (null == attribute) break;
			else this.attributes.add(attribute);
		}
		for (Iterator i=getAttributes().iterator(); i.hasNext(); ) {
			MacroAttribute attr = (MacroAttribute) i.next();
			attributeMap.put(attr.getName(), attr);
		}
	}

	public MacroAttribute getAttribute (String name) {
		return (MacroAttribute) attributeMap.get(name);
	}

	protected MacroAttribute getAttribute(List elements, ParsingSession session)
	throws ParseException {
		String name = null;
		Element defaultVal = null;
		boolean required = false;
		if (elements.size() == 0)
			return null;
		Element e;
		e = (Element) elements.remove(0);
		if (e instanceof SpecialStringElement)
			name = ((SpecialStringElement) e).getTokenValue();
		else if (e instanceof TextElement)
			name = ((TextElement) e).getText();
		else
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Unexpected element, expecting macro attribute name.  Found '" + e + "'");
		// validate name
//		if (name.startsWith("*")) {
//			required = true;
//			name = name.substring(1, name.length());
//		}
		for (int i=0; i<name.length(); i++) {
			char c = name.charAt(i);
			if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-'))
				throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid macro attribute name '" + name + "'");
		}

		// attribute properties
		if (elements.size() > 0) {
			e = (Element) elements.get(0);
			if (e instanceof AssignmentElement) {
				elements.remove(0);
				if (elements.size() == 0) {
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Unexpected content '" + e + "'");
				}
				else {
					// default
					e = (Element) elements.get(0);
					if (e instanceof AssignmentElement) {
						throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Unexpected content '" + e + "'");
					}
					else {
						defaultVal = e;
					}
				}
			}
		}
		MacroAttribute attribute = new MacroAttribute(
				name, defaultVal, required);
		return attribute;
	}

	public void executeMacro (
			ZSContext context, boolean isOrdinal, List attributes,
			MacroInstanceExecutor nestedContent, StringWriter sw)
	throws ExecutionException {
		context = new NestedContextWrapper(context);
		// add attributes to context
		if (isOrdinal) {
			for (int i=0; i<attributes.size(); i++) {
				MacroAttribute defAttribute = (MacroAttribute) getAttributes().get(i);
				MacroAttribute instAttribute = (MacroAttribute) attributes.get(i);
				Object val = instAttribute.getDefaultValue().objectValue(context);
				if (null == val) {
					
					// do we default
					if (null != defAttribute.getDefaultValue())
						val = defAttribute.getDefaultValue();
				}
				if (null != val) context.put(defAttribute.getName(), val);
			}
		}
		else {
			for (int i=0; i<attributes.size(); i++) {
				MacroAttribute instAttribute = (MacroAttribute) attributes.get(i);
				Object val = instAttribute.getDefaultValue().objectValue(context);
				if (null == val) {
					MacroAttribute defAttribute = (MacroAttribute) attributeMap.get(instAttribute.getName());
					// do we default
					if (null != defAttribute.getDefaultValue())
						val = defAttribute.getDefaultValue();
				}
				if (null != val) context.put(instAttribute.getName(), val);
			}
		}
		
		context.put("nested", nestedContent);

		// add template defined parameters
		List tdp = new ArrayList();
		appendMacroInstances(nestedContent.getChildren(), context, tdp);
		for (Iterator i=tdp.iterator(); i.hasNext(); ) {
			MacroInstanceEntity mie = (MacroInstanceEntity) i.next();
			Object obj = context.get(mie.getMacroInstance().getName());
			if (null == obj) {
				context.put(mie.getMacroInstance().getName(), mie);
			}
			else if (obj instanceof List) {
				((List) obj).add(mie);
			}
			else if (obj instanceof MacroInstanceEntity) {
				List l = new ArrayList();
				l.add(obj);
				l.add(mie);
				context.put(mie.getMacroInstance().getName(), l);
			}
			else {
				context.put(mie.getMacroInstance().getName(), mie);
			}
		}

		// execute macro
		for (Iterator i=getChildren().iterator(); i.hasNext(); ) {
			((Element) i.next()).merge(context, sw);
		}
	}

	protected boolean isStartElement(Element e) {
		return (e instanceof MacroDirective);
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndMacroDirective);
	}

	protected boolean allowSelfNesting() {
		return false;
	}

	public String toString() {
		return "[#macro " + contents + "]";
	}

	public void merge(ZSContext context, StringWriter sw) {
	}

	public String getContents() {
		return contents;
	}

	public String getName() {
		return name;
	}

	public List getAttributes() {
		return attributes;
	}

	public Map getAttributeMap() {
		return attributeMap;
	}
}
