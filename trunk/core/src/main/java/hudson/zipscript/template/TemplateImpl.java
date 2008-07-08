package hudson.zipscript.template;

import hudson.zipscript.parser.context.ContextWrapperFactory;
import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TemplateImpl implements Template, Evaluator, Element {

	private Element element;
	private List elements;
	private ParsingSession parsingSession;
	private ParsingResult parsingResult;
	private MacroManager macroManager;

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
		return booleanValue(context, null);
	}

	public boolean booleanValue(Object context, Locale locale) throws ExecutionException {
		return booleanValue(getContext(context, locale));
	}

	public Object objectValue(Object context) throws ExecutionException {
		return objectValue(context, null);
	}

	public Object objectValue(Object context, Locale locale) throws ExecutionException {
		return objectValue(getContext(context, locale));
	}

	public String merge(Object context) throws ExecutionException {
		return merge(context, (Locale) null);
	}

	public String merge(Object context, Locale locale) throws ExecutionException {
		StringWriter sw = new StringWriter();
		merge(context, sw, locale);
		return sw.toString();
	}

	public void merge(Object context, Writer sw) throws ExecutionException {
		merge(context, sw, null);
	}

	public void merge(Object context, Writer sw, Locale locale) throws ExecutionException {
		merge(getContext(context, locale), sw);
	}


	/** Element Methods **/
	public void merge(ExtendedContext context, Writer sw)
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

	public boolean booleanValue(ExtendedContext context) throws ExecutionException {
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

	public Object objectValue(ExtendedContext context) throws ExecutionException {
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

	private ExtendedContext getContext (Object obj, Locale locale) {
		Map initParameters = null;
		if (null != parsingSession && null != parsingSession.getParameters())
			initParameters = parsingSession.getParameters().getInitParameters();
		ExtendedContext context = ContextWrapperFactory.getInstance().wrap(obj, initParameters);
		context.setMacroManager(macroManager);
		context.setParsingSession(parsingSession);
		if (null != locale) context.setLocale(locale);
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

	public void validate(ParsingSession session) throws ParseException {	
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

	public MacroManager getMacroManager() {
		return macroManager;
	}

	public void setMacroManager(MacroManager macroManager) {
		this.macroManager = macroManager;
	}
}