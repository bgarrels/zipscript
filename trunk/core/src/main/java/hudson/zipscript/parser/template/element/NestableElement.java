package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.HeaderElementList;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.directive.AbstractDirective;
import hudson.zipscript.parser.util.ElementNormalizer;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class NestableElement extends AbstractDirective {

	private List children;

	/**
	 * @return the children
	 */
	public List getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List children) {
		this.children = children;
	}

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters)
	throws ParseException {
		int nestedIndex = 1;
		Element element = null;
		for (int i=index; i<elementList.size(); i++) {
			element = (Element) elementList.get(i);
			if (isStartElement(element)) {
				nestedIndex ++;
			}
			else if (isEndElement(element)) {
				nestedIndex --;
				if (nestedIndex == 0) {
					return endMatchFound(index, element, elementList, parameters);
				}
			}
		}
		// no end element was found
		throw new ParseException(
				ParseException.TYPE_EOF, this, "no end element was found");
	}

	protected ElementIndex endMatchFound (
			int startIndex, Element endMatch, List elementList, ParseParameters parameters)
	throws ParseException {
		List l = new ArrayList();
		Element topLevelElement = null;
		boolean foundEndelement = false;
		Element element = null;
		for (int i=startIndex; i<elementList.size(); i++) {
			element = (Element) elementList.get(i);
			if (element == endMatch) {
				foundEndelement = true;
				break;
			}
			if (isTopLevelElement(element)) {
				if (null != topLevelElement) {
					ElementNormalizer.normalize(l, parameters, false);
					setTopLevelElements(new HeaderElementList(topLevelElement, l));
				}
				else {
					setChildren(l);
				}
				topLevelElement = element;
				l = new ArrayList();
			}
			else {
				l.add(element);
			}
		}
		if (foundEndelement) {
			// remove grouped elements from list
			while (true) {
				if (endMatch == elementList.remove(startIndex)) {
					break;
				}
			}
			ElementNormalizer.normalize(l, parameters, false);
			if (null != topLevelElement) {
				setTopLevelElements(
						new HeaderElementList(topLevelElement, l));
			}
			else {
				setChildren(l);
			}
			return null;
		}
		else{
			throw new ParseException(ParseException.TYPE_EOF, this, "No end element found");
		}
	}

	protected abstract boolean isStartElement (Element e);

	protected abstract boolean isEndElement (Element e);

	protected boolean isTopLevelElement (Element e) {
		return false;
	}

	protected void setTopLevelElements (HeaderElementList elements) throws ParseException {
	}

	protected boolean allowSelfNesting () {
		return true;
	}

	protected void appendElements (
			List elements, ZSContext context, StringWriter sw)
	throws ExecutionException {
		for (Iterator i=elements.iterator(); i.hasNext(); ) {
			((Element) i.next()).merge(context, sw);
		}
	}
}