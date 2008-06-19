package hudson.zipscript.template;

import hudson.zipscript.parser.context.ContextWrapperFactory;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TemplateImpl implements Template, EvaluationTemplate, Element {

	private Element element;
	private List elements;
	private ParsingSession parsingSession;
	private ParsingResult parsingResult;

	public TemplateImpl (List elements, ParsingSession parsingSession, ParsingResult result) {
		this.elements = elements;
		this.parsingSession = parsingSession;
		this.parsingResult = result;
	}

	public TemplateImpl (Element element) {
		this.element = element;
	}

	/** Template Methods **/
	public boolean booleanValue(Object context) throws ExecutionException {
		return booleanValue(getContext(context));
	}

	public Object objectValue(Object context) throws ExecutionException {
		return objectValue(getContext(context));
	}

	public String merge(Object context) throws ExecutionException {
		StringWriter sw = new StringWriter();
		merge(context, sw);
		return sw.toString();
	}

	public void merge(Object context, StringWriter sw) throws ExecutionException {
		merge(getContext(context), sw);
	}


	/** Element Methods **/
	public void merge(ZSContext context, StringWriter sw)
	throws ExecutionException {
		try {
			for (Iterator i=elements.iterator(); i.hasNext(); ) {
				((Element) i.next()).merge(context, sw);
			}
		}
		catch (ExecutionException e) {
			e.setParsingResult(parsingResult);
			throw (e);
		}
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		if (null != element)
			try {
				return element.booleanValue(
						context);
			}
			catch (ExecutionException e) {
				e.setParsingResult(parsingResult);
				throw (e);
			}
		else
			throw new ExecutionException("Invalid boolean expression", null);
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		if (null != element)
			try {
				return element.objectValue(
						context);
			}
			catch (ExecutionException e) {
				e.setParsingResult(parsingResult);
				throw (e);
			}
		else
			throw new ExecutionException("Invalid object expression", null);
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

	private ZSContext getContext (Object obj) {
		ZSContext context = ContextWrapperFactory.getInstance().wrap(obj);
		context.setParsingSession(parsingSession);
		return context;
	}

	public ParsingSession getParsingSession() {
		return parsingSession;
	}

	public ParsingResult getParsingResult() {
		return parsingResult;
	}

	public Element getElement() {
		return element;
	}

	public List getElements() {
		return elements;
	}

	public List getChildren() {
		if (null != elements)
			return elements;
		else if (null != element) {
			elements = new ArrayList();
			elements.add(element);
			return elements;
		}
		else return null;
	}
}