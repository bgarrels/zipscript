package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.Constants;
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
import hudson.zipscript.parser.util.SessionUtil;
import hudson.zipscript.parser.util.StringUtil;

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
	private String namespace;

	private List attributes = new ArrayList();
	private Map attributeMap;
	private MacroDirective macro;

	private MacroHeaderElement header;
	private MacroFooterElement footer;

	// for a template defined parameter
	private MacroDirective baseMacroDefinition;
	boolean isTemplateDefinedParameter;
	boolean isInMacroDefinition;
	private MacroDefinitionAttribute templateDefinedParameterDefinition;
	// for a template defined parameter reference inside a common macro
	boolean isTemplateDefinedParameterInMacroDefinition;

	// text normalization
	TextElement previousTextElement;

	// private ParsingSession parsingSession;
	private int contentPosition;

	public MacroInstanceDirective (
			String contents, boolean isFlat, ParsingSession parsingSession, int contentPosition) throws ParseException {
		this(contents, isFlat, false, parsingSession, contentPosition);
	}

	public MacroInstanceDirective (
			String contents, boolean isFlat, boolean isTemplateDefinedParamterInMacroDefinition, ParsingSession parsingSession, int contentPosition) throws ParseException {
		this.contents = contents;
		setFlat(isFlat);
		// this.parsingSession = parsingSession;
		this.contentPosition = contentPosition;
		this.isTemplateDefinedParameterInMacroDefinition = isTemplateDefinedParamterInMacroDefinition;
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
		int index = name.indexOf('.');
		if (index > 0) {
			String s = name;
			namespace = s.substring(0, index);
			name = s.substring(index+1, s.length());
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
				MacroInstanceAttribute attribute = getAttribute(elements, session);
				if (null == attribute) break;
				else {
					this.attributes.add(attribute);
					if (!isOrdinal) attributeMap.put(attribute.getName(), attribute);
				}
			}
		}
		else {
			isOrdinal = false;
		}
	}

	public void validate (ParsingSession session) throws ParseException {
		// see if we are in a macro definition
		for (Iterator i=session.getNestingStack().iterator(); i.hasNext(); ) {
			Element e = (Element) i.next();
			if (e instanceof MacroInstanceDirective) {
				MacroInstanceDirective mid = (MacroInstanceDirective) e;
				if (!mid.isTemplateDefinedParameter) {
					baseMacroDefinition = session.getMacroManager().getMacro(
							mid.getName(), mid.getNamespace(), session);
					isInMacroDefinition = true;
					break;
				}
			}
			else if (e instanceof MacroDirective) {
				isInMacroDefinition = true;
				break;
			}
		}
		if (isTemplateDefinedParameterInMacroDefinition && !isInMacroDefinition) {
			throw new ParseException(this, "the '[.@' syntax can only be used inside a macro definition");
		}

		if (!isTemplateDefinedParameterInMacroDefinition) {
			// find macro in session
			// if we're in the header/footer - we can't validate until the whole template is parsed - how to do that?
			macro = session.getMacroManager().getMacro(
					getName(), getNamespace(), session);
	
			if (null == macro && !isOrdinal() && null == namespace && null != baseMacroDefinition) {
				// might be a template defined parameter
				MacroDefinitionAttribute attr = null;
				if (null != baseMacroDefinition) attr = baseMacroDefinition.getTemplateDefinedParameterAttribute(getName());
				if (null == attr) {
					// we can't find any matching defined template parameters
					throw new ParseException(this, "Undefined macro name '" + getName() + "'");
				}
				else {
					// it is a template defined parameter
					this.isTemplateDefinedParameter = true;
					this.templateDefinedParameterDefinition = attr;
					List attributes = attr.getTDPAttributes();
					// make sure all required and/or defaulted attributes are defined
					for (int i=0; i<attributes.size(); i++) {
						MacroDefinitionAttribute attribute = (MacroDefinitionAttribute) attributes.get(i);
						validateTemplateAttribute(attribute, this);
					}
					if (null != previousTextElement)
						StringUtil.trimLastEmptyLine(previousTextElement);
				}
			}
			else {
				if (null == macro) {
					throw new ParseException(this, "Undefined macro name '" + getName() + "'");
				}
				// its not a template defined parameter
				if (!isOrdinal) {
					// make sure all attributes are defined
					for (int i=0; i<attributes.size(); i++) {
						MacroInstanceAttribute attribute = (MacroInstanceAttribute) attributes.get(i);
						if (null == macro.getAttribute(attribute.getName())) {
							throw new ParseException(contentPosition, "Undefined macro attribute '" + attribute.getName() + "'");
						}
					}
					// make sure we're passing all non-required attributes
					for (int i=0; i<macro.getAttributes().size(); i++) {
						MacroDefinitionAttribute attribute = (MacroDefinitionAttribute) macro.getAttributes().get(i);
						if (attribute.isTemplateDefinedParameter()) {
							validateTemplateAttribute(attribute, this);
						}
						else if (attribute.isRequired() && null == attribute.getDefaultValue() && null == getAttribute(attribute.getName())) {
							// it's required
							throw new ParseException(contentPosition, "Undefined required macro attriute '" + attribute.getName() + "'");
						}
					}
				}
			}
		}

		if (null != header)
			header.validate(session);
		if (null != footer)
			footer.validate(session);

		if (null != getChildren() && SessionUtil.getProperty(
				Constants.TRIM_MACRO_BODY, true, session)) {
			// trim the body
			StringUtil.trim(getChildren());
		}
	}

	protected void validateTemplateAttribute(MacroDefinitionAttribute attribute, MacroInstanceDirective mid) throws ParseException {
		if (attribute.isTemplateDefinedParameter()) {
			// make sure we have the parameter
			List macroInstanceDirectives = new ArrayList();
			loadTemplateDefinedAttributes(attribute.getName(), mid, macroInstanceDirectives);
			if (macroInstanceDirectives.size() == 0 && attribute.isRequired())
				throw new ParseException(mid, "Missing template defined parameter '" + attribute.getName() + "'");
			for (Iterator i=macroInstanceDirectives.iterator(); i.hasNext(); ) {
				MacroInstanceDirective subMid = (MacroInstanceDirective) i.next();
				for (Iterator j=attribute.getTDPAttributes().iterator(); j.hasNext(); ) {
					MacroDefinitionAttribute mda = (MacroDefinitionAttribute) j.next();
					if (mda.isTemplateDefinedParameter())  {
						validateTemplateAttribute(mda, subMid);
					}
				}
			}
		}
		else {
			if (attribute.isRequired() && null == mid.getAttribute(attribute.getName()))
				throw new ParseException(mid, "Missing template defined parameter attribute '" + attribute.getName() + "'");
		}
	}

	protected void loadTemplateDefinedAttributes (String name, Element e, List l) {
		List children = e.getChildren();
		if (null != e.getChildren()) {
			for (Iterator i=children.iterator(); i.hasNext(); ) {
				Element subE = (Element) i.next();
				if (subE instanceof MacroInstanceDirective) {
					MacroInstanceDirective mid = (MacroInstanceDirective) subE;
					if (null == mid.getNamespace() && mid.getName().equals(name)) l.add(mid);
				}
				else loadTemplateDefinedAttributes(name, subE, l);
			}
		}
	}

	public MacroInstanceAttribute getAttribute (String name) {
		if (null == attributeMap) return null;
		else return (MacroInstanceAttribute) attributeMap.get(name);
	}

	protected MacroInstanceAttribute getAttribute(List elements, ParsingSession session)
	throws ParseException {
		if (elements.size() == 0) return null;

		if (isOrdinal) {
			Element e = (Element) elements.remove(0);
			if (e instanceof AssignmentElement)
				throw new ParseException(this, "Unexpected token '=' found when parsing ordinal macro attributes");
			return new MacroInstanceAttribute(null, e);
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
							return new MacroInstanceAttribute(name, e);
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
		StringUtil.trimLastEmptyLine(getChildren());
		if (index > 0 && elementList.get(index-1) instanceof TextElement)
			previousTextElement = (TextElement) elementList.get(index-1);
		return rtn;
	}

	protected boolean isStartElement(Element e) {
		return (e instanceof MacroInstanceDirective
				&& ((MacroInstanceDirective) e).getName().equals(getName()));
	}

	protected boolean isEndElement(Element e) {
		if (e instanceof EndMacroInstanceDirective) {
			EndMacroInstanceDirective eid = (EndMacroInstanceDirective) e;
			if (eid.getName().equals(getFullName())
					&& eid.isTemplateDefinedParameterInMacroDefinition() == isTemplateDefinedParameterInMacroDefinition())
				return true;
		}
		return false;
	}

	protected boolean allowSelfNesting() {
		return false;
	}

	public String toString() {
		return "[@" + contents + "]";
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		if (!isTemplateDefinedParameter) {
			if (null == macro) {
				// we might need to lazy load
				macro = context.getMacroManager().getMacro(getName(), getNamespace(), context.getParsingSession());
			}
			if (null == macro) {
				throw new ExecutionException("Undefined macro '" + getName() + "'", this);
			}
			macro.executeMacro(
					context, isOrdinal(), getAttributes(),
					new MacroInstanceExecutor(this, context), sw);
		}
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
	
	public void getMatchingTemplateDefinedParameters(ZSContext context,
			List list, MacroDirective macro, Map additionalContextEntries) {
		if (isTemplateDefinedParameterInMacroDefinition && null != macro.getAttribute(getName())) {
			list.add(new MacroInstanceEntity(
					(MacroInstanceDirective) this, context, additionalContextEntries));
		}
		else {
			super.appendTemplateDefinedParameters(getChildren(), context, list, macro, additionalContextEntries);
		}
	}

	public boolean isInMacroDefinition() {
		return isInMacroDefinition;
	}

	public boolean isTemplateDefinedParameter() {
		return isTemplateDefinedParameter;
	}

	public void setTemplateDefinedParameter(boolean isTemplateDefinedParameter) {
		this.isTemplateDefinedParameter = isTemplateDefinedParameter;
	}

	public String getNamespace() {
		return namespace;
	}

	public MacroDirective getBaseMacroDefinition() {
		return baseMacroDefinition;
	}

	public MacroDefinitionAttribute getTemplateDefinedParameterDefinition() {
		return templateDefinedParameterDefinition;
	}

	public void setInMacroDefinition(boolean isInMacroDefinition) {
		this.isInMacroDefinition = isInMacroDefinition;
	}

	public String getFullName () {
		if (null == namespace) return name;
		else return namespace + '.' + name;
	}

	public List getMacroDefinitionAttributes (ZSContext context) {
		if (isTemplateDefinedParameterInMacroDefinition()) {
			// find the associated macro in the parsing session stack
			List elements = new ArrayList();
			context.addToElementScope(elements);
			for (int i=0; i<elements.size(); i++) {
				Element e = (Element) elements.get(i);
				if (e instanceof MacroDirective) {
					MacroDirective md = (MacroDirective) e;
					// make sure we've got a match
					MacroDefinitionAttribute attr = md.getAttribute(getName());
					if (null != attr && attr.isTemplateDefinedParameter()) {
						return attr.getTDPAttributes();
					}
				}
			}
			throw new ExecutionException("Unknown template=defined parameter '" + getName() + "'", this);
		}
		else {
			if (null != templateDefinedParameterDefinition) {
				return templateDefinedParameterDefinition.getTDPAttributes();
			}
			else if (null != macro) {
				return macro.getAttributes();
			}
			else return baseMacroDefinition.getAttributes();
		}
	}

	public boolean isTemplateDefinedParameterInMacroDefinition() {
		return isTemplateDefinedParameterInMacroDefinition;
	}

	public MacroHeaderElement getHeader() {
		return header;
	}

	public void setHeader(MacroHeaderElement header) {
		this.header = header;
	}

	public MacroFooterElement getFooter() {
		return footer;
	}

	public void setFooter(MacroFooterElement footer) {
		this.footer = footer;
	}
}