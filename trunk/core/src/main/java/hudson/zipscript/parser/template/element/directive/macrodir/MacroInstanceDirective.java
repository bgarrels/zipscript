package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
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

public class MacroInstanceDirective extends NestableElement {

	private boolean isFlat = false;
	private boolean isOrdinal = true;
	private String contents;
	private String name;

	List attributes = new ArrayList();
	Map attributeMap;
	MacroDirective macro;

	public MacroInstanceDirective (
			String contents, boolean isFlat, ParsingSession session, int contentPosition) throws ParseException {
		this.contents = contents;
		this.isFlat = isFlat;
		parseContents(contents, session, contentPosition);
	}

	protected void parseContents (String contents, ParsingSession session, int contentPosition) throws ParseException {
		java.util.List elements = parseElements(contents, session, contentPosition);
		if (elements.size() == 0)
			throw new ParseException(this, "Macro name was not specified");
		Element e;
		e = (Element) elements.remove(0);
		if (e instanceof SpecialStringElement)
			name = ((SpecialStringElement) e).getTokenValue();
		// validate name
		for (int i=0; i<name.length(); i++) {
			char c = name.charAt(i);
			if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-'))
				throw new ParseException(this, "Invalid macro name '" + name + "'");
		}

		// determine parameter type
		if (elements.size() != 0) {
			if (elements.size() == 1)
				isOrdinal = true;
			else if (elements.get(1) instanceof AssignmentElement) {
				isOrdinal = false;
				attributeMap = new HashMap();
			}

			// look for attributes
			while (true) {
				MacroAttribute attribute = getAttribute(elements, session);
				if (null == attribute) break;
				else {
					this.attributes.add(attribute);
					if (!isOrdinal) attributeMap.put(attribute.getName(), attribute);
				}
			}
		}
	}

	public MacroAttribute getAttribute (String name) {
		if (null == attributeMap) return null;
		else return (MacroAttribute) attributeMap.get(name);
	}

	protected MacroAttribute getAttribute(List elements, ParsingSession session)
	throws ParseException {
		if (elements.size() == 0) return null;

		if (isOrdinal) {
			Element e = (Element) elements.remove(0);
			if (e instanceof AssignmentElement)
				throw new ParseException(this, "Unexpected token '=' found when parsing ordinal macro attributes");
			return new MacroAttribute(null, e, false);
		}
		else {
			String name = null;
			Element valueElemeent = null;
			
			Element e;
			e = (Element) elements.remove(0);
			if (e instanceof SpecialStringElement)
				name = ((SpecialStringElement) e).getTokenValue();
			else if (e instanceof TextElement)
				name = ((TextElement) e).getText();
			else
				throw new ParseException(this, "Unexpected element, expecting macro attribute name.  Found '" + e + "'");
			// validate name
			for (int i=0; i<name.length(); i++) {
				char c = name.charAt(i);
				if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-'))
					throw new ParseException(this, "Invalid macro attribute name '" + name + "'");
			}

			// attribute properties
			if (elements.size() > 0) {
				e = (Element) elements.get(0);
				if (e instanceof AssignmentElement) {
					elements.remove(0);
					if (elements.size() == 0) {
						throw new ParseException(this, "Unexpected content '" + e + "'");
					}
					else {
						// value
						e = (Element) elements.remove(0);
						if (e instanceof AssignmentElement) {
							throw new ParseException(this, "Unexpected content '" + e + "'");
						}
						else {
							MacroAttribute attribute = new MacroAttribute(
									name, e, true);
							return attribute;
						}
					}
				}
				else {
					throw new ParseException(this, "Unexpected element, expecting '='.  Found '" + e + "'");
				}
			}
			else {
				throw new ParseException(this, "Missing macro value for '" + name + "' in " + this.toString());
			}
		}
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		ElementIndex rtn = null;
		if (isFlat) rtn = null;
		else rtn = super.normalize(index, elementList, session);

		// check for end new line
		if (getChildren().size() > 0) {
			Element e = (Element) getChildren().get(getChildren().size()-1);
			if (e instanceof TextElement) {
				TextElement te = (TextElement) e;
				if (te.getText().endsWith("\r\n"))
					te.setText(te.getText().substring(0, te.getText().length()-2));
				else if (te.getText().endsWith("\n"))
					te.setText(te.getText().substring(0, te.getText().length()-1));
			}
		}

		// find macro in session
		macro = session.getMacroDirective(getName());
		if (null == macro) {
			// maybe a template defined parameter
			boolean isTDP = false;
			if (!isOrdinal || this.getAttributes().size() == 0) {
				for (Iterator i=session.getNestingStack().iterator(); i.hasNext(); ) {
					Element e = (Element) i.next();
					if (e instanceof MacroInstanceDirective) {
						// assume that maybe this is a TDP
						// this issue with this is that it will show up as an execution error
						// as opposed to a parsing error
						isTDP = true;
						break;
					}
				}
			}
			else  {
				throw new ParseException(this, "The template defined parameter '" + getName() + "' can have only named parameters");
			}
			if (!isTDP) {
				throw new ParseException(this, "Undefined macro name '" + getName() + "'");
			}
		}
		return rtn;
	}

	protected boolean isStartElement(Element e) {
		return (e instanceof MacroInstanceDirective
				&& ((MacroInstanceDirective) e).getName().equals(getName()));
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndMacroInstanceDirective
				&& ((EndMacroInstanceDirective) e).getName().equals(getName()));
	}

	protected boolean allowSelfNesting() {
		return false;
	}

	public String toString() {
		return "[@" + contents + "]";
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		macro.executeMacro(
				context, isOrdinal(), getAttributes(), new MacroInstanceExecutor(
						this, context), sw);
	}

	public String getNestedContent (ZSContext context) throws ExecutionException {
		StringWriter sw = new StringWriter();
		for (Iterator i=getChildren().iterator(); i.hasNext(); ) {
			((Element) i.next()).merge(context, sw);
		}
		return sw.toString();
	}

	public MacroDirective getMacroDefinition () {
		return macro;
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

	public boolean isOrdinal() {
		return isOrdinal;
	}

	public void setOrdinal(boolean isOrdinal) {
		this.isOrdinal = isOrdinal;
	}

	public boolean isFlat() {
		return isFlat;
	}
}
