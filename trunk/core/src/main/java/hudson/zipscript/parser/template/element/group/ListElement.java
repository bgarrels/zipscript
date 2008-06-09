package hudson.zipscript.parser.template.element.group;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.lang.CommaElement;
import hudson.zipscript.parser.template.element.lang.WhitespaceElement;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListElement extends NestableElement {

	private java.util.List listElements = new ArrayList();

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		ElementIndex rtn = super.normalize(index, elementList, session);
		// load the elements into a list
		boolean waslastEntryComma = true;
		for (Iterator i=getChildren().iterator(); i.hasNext(); ) {
			Element e = (Element) i.next();
			if (e instanceof WhitespaceElement) continue;
			if (waslastEntryComma) {
				if (e instanceof CommaElement) {
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Unexpected Comma");
				}
				else {
					waslastEntryComma = false;
					listElements.add(e);
				}
			}
			else {
				if (!(e instanceof CommaElement))
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Expecting comma but found '" + e.toString() + "'");
				else {
					waslastEntryComma = true;
				}
			}
		}
		return rtn;
	}

	public void merge(ZSContext context, StringWriter sw)
			throws ExecutionException {
		throw new ExecutionException("Lists can not be merged directly");
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		List l = new ArrayList();
		for (Iterator i=listElements.iterator(); i.hasNext(); ) {
			l.add(((Element) i.next()).objectValue(context));
		}
		return l;
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		if (getChildren().size() == 1)
			return ((Element) getChildren().get(0)).booleanValue(context);
		else
			throw new ExecutionException("lists can not be evaluated as booleans");
	}

	protected boolean isStartElement(hudson.zipscript.parser.template.element.Element e) {
		return (e instanceof ListElement);
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndListElement);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i=0; i<getChildren().size(); i++) {
			if (i > 0) sb.append(", ");
			sb.append(getChildren().get(i));
		}
		sb.append("}");
		return sb.toString();
	}
}
