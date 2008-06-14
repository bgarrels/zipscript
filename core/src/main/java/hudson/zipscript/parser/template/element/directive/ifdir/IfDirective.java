package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.HeaderElementList;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAware;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class IfDirective extends NestableElement implements MacroInstanceAware {

	public Element ifElement;
	private List elseifScenarios;
	private List elseElements;

	public IfDirective (String contents, int contentIindex) throws ParseException {
		parseContents(contents, contentIindex);
	}

	private void parseContents (String contents, int contentIindex) throws ParseException {
		ifElement = parseElement(contents, contentIindex);
		if (null == ifElement) throw new ParseException(
				ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid element syntax '" + contents + "'");
	}

	protected boolean isTopLevelElement(Element e) {
		return (e instanceof ElseIfDirective || e instanceof ElseDirective);
	}

	protected void setTopLevelElements(HeaderElementList elements) throws ParseException {
		if (elements.getHeader() instanceof ElseIfDirective) {
			if (null == elseifScenarios)
				elseifScenarios = new ArrayList();
			ElseIfDirective directive = (ElseIfDirective) elements.getHeader();
			Element element = parseElement(directive.getContents(), (int) elements.getHeader().getElementPosition());
			elements.setHeader(element);
			elseifScenarios.add(elements);
		}
		else if (elements.getHeader() instanceof ElseDirective) {
			elseElements = elements.getChildren();
		}
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		boolean done = false;
		if (ifElement.booleanValue(context)) {
			appendElements(getChildren(), context, sw);
			done = true;
		}
		if (null != elseifScenarios) {
			for (int i=0; i<elseifScenarios.size() && !done; i++) {
				HeaderElementList elements = (HeaderElementList) elseifScenarios.get(i);
				if (elements.getHeader().booleanValue(context)) {
					appendElements(elements.getChildren(), context, sw);
					done = true;
				}
			}
		}
		if (!done && null != elseElements) { 
			appendElements(elseElements, context, sw);
		}
	}

	public void getMacroInstances(
			ZSContext context, List macroInstanceList) throws ExecutionException {
		boolean done = false;
		if (ifElement.booleanValue(context)) {
			appendMacroInstances(getChildren(), context, macroInstanceList);
			done = true;
		}
		if (null != elseifScenarios) {
			for (int i=0; i<elseifScenarios.size() && !done; i++) {
				HeaderElementList elements = (HeaderElementList) elseifScenarios.get(i);
				if (elements.getHeader().booleanValue(context)) {
					appendMacroInstances(elements.getChildren(), context, macroInstanceList);
					done = true;
				}
			}
		}
		if (!done && null != elseElements) { 
			appendMacroInstances(elseElements, context, macroInstanceList);
		}
	}

	protected boolean isStartElement(Element e) {
		return (e instanceof IfDirective);
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndIfDirective);
	}

	/**
	 * @return the elseifScenarios
	 */
	public List getElseifScenarios() {
		return elseifScenarios;
	}

	/**
	 * @return the elseElements
	 */
	public List getElseElements() {
		return elseElements;
	}

	public String toString() {
		return "[#if " + ifElement + "]";
	}
}