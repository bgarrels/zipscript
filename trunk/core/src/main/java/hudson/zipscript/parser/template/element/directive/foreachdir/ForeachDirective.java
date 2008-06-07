package hudson.zipscript.parser.template.element.directive.foreachdir;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.lang.WhitespaceElement;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;
import hudson.zipscript.parser.template.element.special.InElement;
import hudson.zipscript.parser.template.element.special.InPatternMatcher;
import hudson.zipscript.parser.template.element.special.SpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialStringDefaultEelementFactory;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;
import hudson.zipscript.parser.template.element.special.SpecialVariableDefaultEelementFactory;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

public class ForeachDirective extends NestableElement {

	public static final String TOKEN_INDEX = "i";
	public static final String TOKEN_HASNEXT = "hasNext";

	private static PatternMatcher[] MATCHERS;
	static {
		PatternMatcher[] matchers = ZipEngine.VARIABLE_MATCHERS;
		MATCHERS = new PatternMatcher[matchers.length+1];
		System.arraycopy(matchers, 0, MATCHERS, 1, matchers.length);
		MATCHERS[0] = new InPatternMatcher();
	}

	private String varName;
	private VariableElement listElement;

	public ForeachDirective (String contents, ParsingSession parseData) throws ParseException {
		parseContents(contents, parseData);
	}

	private void parseContents (String contents, ParsingSession session) throws ParseException {
		java.util.List elements = parseElements(contents, session);
		try {
			if (elements.get(0) instanceof SpecialStringElement) {
				this.varName = ((SpecialStringElement) elements.get(0)).getTokenValue();
				elements.remove(0);
			}
			else {
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid sequence.  Expecting variable name");
			}
			if (!(elements.remove(0) instanceof InElement))
				throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Improperly formed for expression: 'in' should be second token");
			this.listElement = new VariableElement(elements, session);
		}
		catch (IndexOutOfBoundsException e) {
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Improperly formed for expression: must have at least 3 tokens");
		}
	}

	protected DefaultElementFactory getContentParsingDefaultElementFactory() {
		return SpecialVariableDefaultEelementFactory.getInstance();
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		Object list = listElement.objectValue(context);
		if (null != list) {
			if (list instanceof Object[]) {
				Object[] arr = (Object[]) list;
				if (arr.length > 0) {
					int i=0;
					context = new NestedContextWrapper(context);
					context.put(TOKEN_INDEX, new Integer(0));
					int checkNum = arr.length-1;
					context.put(TOKEN_HASNEXT, Boolean.TRUE);
					while (i<arr.length) {
						if (i >= checkNum) context.put(TOKEN_HASNEXT, Boolean.FALSE);
						context.put(varName, arr[i]);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i));
					}
				}
			}
			else if (list instanceof Collection) {
				Collection c = (Collection) list;
				if (c.size() > 0) {
					int i=0;
					context = new NestedContextWrapper(context);
					context.put(TOKEN_INDEX, new Integer(0));
					context.put(TOKEN_HASNEXT, Boolean.TRUE);
					for (Iterator iter=c.iterator(); iter.hasNext(); ) {
						context.put(varName, iter.next());
						if (!iter.hasNext()) context.put(TOKEN_HASNEXT, Boolean.FALSE);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i));
					}
				}
			}
			else if (list instanceof Iterator) {
				Iterator iter = (Iterator) list;
				if (iter.hasNext()) {
					int i=0;
					context = new NestedContextWrapper(context);
					context.put(TOKEN_INDEX, new Integer(0));
					context.put(TOKEN_HASNEXT, Boolean.TRUE);
					while (iter.hasNext()) {
						context.put(varName, iter.next());
						if (!iter.hasNext()) context.put(TOKEN_HASNEXT, Boolean.FALSE);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i));
					}
				}
			}
			else if (list instanceof Enumeration) {
				Enumeration enum = (Enumeration) list;
				if (enum.hasMoreElements()) {
					int i=0;
					context = new NestedContextWrapper(context);
					context.put(TOKEN_INDEX, new Integer(0));
					context.put(TOKEN_HASNEXT, Boolean.TRUE);
					while (enum.hasMoreElements()) {
						context.put(varName, enum.nextElement());
						if (enum.hasMoreElements()) context.put(TOKEN_HASNEXT, Boolean.FALSE);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i));
					}
				}
			}
			else {
				throw new ExecutionException("List entry is not of a list nature");
			}
		}
	}

	protected PatternMatcher[] getContentParsingPatternMatchers() {
		return MATCHERS;
	}

	protected boolean isStartElement(Element e) {
		return (e instanceof ForeachDirective);
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndForeachDirective);
	}

	public String toString() {
		return "[#foreach " + varName + " in " + listElement + "]";
	}
}