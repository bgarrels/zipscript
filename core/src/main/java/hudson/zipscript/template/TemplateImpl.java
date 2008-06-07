package hudson.zipscript.template;

import hudson.zipscript.parser.context.ContextWrapperFactory;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

public class TemplateImpl implements Template, EvaluationTemplate, Element {

	private Element element;
	private List elements;

	public TemplateImpl (List elements) {
		this.elements = elements;
	}

	public TemplateImpl (Element element) {
		this.element = element;
	}

	/** Template Methods **/
	public boolean booleanValue(Object context) throws ExecutionException {
		return booleanValue(ContextWrapperFactory.getInstance().wrap(context));
	}

	public Object objectValue(Object context) throws ExecutionException {
		return objectValue(ContextWrapperFactory.getInstance().wrap(context));
	}

	public String merge(Object context) throws ExecutionException {
		StringWriter sw = new StringWriter();
		merge(context, sw);
		return sw.toString();
	}

	public void merge(Object context, StringWriter sw) throws ExecutionException {
		merge(ContextWrapperFactory.getInstance().wrap(context), sw);
	}


	/** Element Methods **/
	public void merge(ZSContext context, StringWriter sw)
	throws ExecutionException {
		for (Iterator i=elements.iterator(); i.hasNext(); ) {
			((Element) i.next()).merge(context, sw);
		}
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		if (null != element)
			return element.booleanValue(
					context);
		else
			throw new ExecutionException("Invalid boolean expression");
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		if (null != element)
			return element.objectValue(context);
		else
			throw new ExecutionException("Invalid object expression");
	}

	public int getElementLength() {
		return 0;
	}

	public long getElementPosition() {
		return 0;
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}

	public void setElementLength(int length) {
	}

	public void setElementPosition(long position) {
	}
}