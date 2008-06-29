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
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MacroInstanceDirective extends NestableElement implements MacroInstanceAware {

	private boolean isOrdinal = true;
	private String contents;
	private String name;
	boolean isInMacroDefinition;

	List attributes = new ArrayList();
	Map attributeMap;
	MacroDirective macro;

	// private ParsingSession parsingSession;
	private int contentPosition;

	public MacroInstanceDirective (
			String contents, boolean isFlat, ParsingSession parsingSession, int contentPosition) throws ParseException {
		this.contents = contents;
		setFlat(isFlat);
		// this.parsingSession = parsingSession;
		this.contentPosition = contentPosition;
		parseContents(contents, parsingSession, contentPosition);
	}

	protected void parseContents (
			String contents, ParsingSession session, int contentPosition)
	throws ParseException {
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
			if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.'))
				throw new ParseException(contentPosition, "Invalid macro name '" + name + "'");
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

	private void loadTDPs (Element e, Map map) {
		if (null == e) return;
		if (e instanceof MacroInstanceDirective) {
			MacroInstanceDirective dir = (MacroInstanceDirective) e;
			if (!isOrdinal() && null != getMacroDefinition() && null != getMacroDefinition().getAttribute(dir.getName())) {
				// this is a template defined parameter
				map.put(dir.getName(), e);
			}
			else {
				loadTDPs(dir.getMacroDefinition(), map);
			}
		}
		else if (e instanceof MacroInstanceAware && null != e.getChildren()) {
			for (Iterator i=e.getChildren().iterator(); i.hasNext(); ) {
				loadTDPs((Element) i.next(), map);
			}
		}
	}

	private void validate (ParsingSession session) throws ParseException {
		// see if we are in a macro definition
		for (Iterator i=session.getNestingStack().iterator(); i.hasNext(); ) {
			if (i.next() instanceof MacroDirective) {
				isInMacroDefinition = true;
				break;
			}
		}

		// find macro in session
		macro = session.getMacroManager().getMacro(
				getName(), session);

		Map tdps = new HashMap();
		if (null != getChildren()) {
			for (Iterator i=getChildren().iterator(); i.hasNext(); ) {
				loadTDPs((Element) i.next(), tdps);
			}
		}

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
		else {
			if (!isOrdinal) {
				// make sure all attributes are defined
				for (int i=0; i<attributes.size(); i++) {
					MacroAttribute attribute = (MacroAttribute) attributes.get(i);
					if (null == macro.getAttribute(attribute.getName())) {
						throw new ParseException(contentPosition, "Undefined macro attribute '" + attribute.getName() + "'");
					}
				}
				// make sure we're passing all non-required attributes
				for (int i=0; i<macro.getAttributes().size(); i++) {
					MacroAttribute attribute = (MacroAttribute) macro.getAttributes().get(i);
					if (null == attribute.getDefaultValue() && null==tdps.get(attribute.getName())) {
						// it's required
						if (null == attributeMap.get(attribute.getName()) && null == tdps.get(attribute.getName())) {
							throw new ParseException(contentPosition, "Undefined required macro attriute '" + attribute.getName() + "'");
						}
					}
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
		if (isFlat()) rtn = null;
		else rtn = super.normalize(index, elementList, session);

		// check for end new line
		if (null != getChildren()) {
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
		}

		validate(session);
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

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		if (null == macro) {
			// we might need to lazy load
			macro = context.getMacroManager().getMacro(getName(), context.getParsingSession());
		}
		if (null == macro) {
			throw new ExecutionException("Undefined macro '" + getName() + "'", this);
		}
		macro.executeMacro(
				context, isOrdinal(), getAttributes(),
				new MacroInstanceExecutor(this, context), sw);
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

	public void appendMacroInstances(ZSContext context,
			List macroInstanceList, MacroDirective macro, Map additionalContextEntries) {
		super.appendMacroInstances(
				getChildren(), context, macroInstanceList, macro, additionalContextEntries);
	}

	public void getMacroInstances(
			ZSContext context, List macroInstanceList, MacroDirective macro, Map additionalContextEntries) {
		super.appendMacroInstances(getChildren(), context, macroInstanceList, macro, additionalContextEntries);	
	}

	public boolean isInMacroDefinition() {
		return isInMacroDefinition;
	}
}