package hudson.zipscript.parser.template.element.directive.foreachdir;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.comparator.InComparatorPatternMatcher;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAware;
import hudson.zipscript.parser.template.element.group.ListElement;
import hudson.zipscript.parser.template.element.lang.variable.SpecialVariableDefaultEelementFactory;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;
import hudson.zipscript.parser.template.element.special.InElement;
import hudson.zipscript.parser.template.element.special.InPatternMatcher;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class ForeachDirective extends NestableElement implements MacroInstanceAware {

	public static final String TOKEN_INDEX = "i";
	public static final String TOKEN_HASNEXT = "hasNext";

	private static PatternMatcher[] MATCHERS;
	static {
		PatternMatcher[] matchers = ZipEngine.VARIABLE_MATCHERS;
		MATCHERS = new PatternMatcher[matchers.length];
		for (int i=0; i<matchers.length; i++) {
			if (matchers[i] instanceof InComparatorPatternMatcher) {
				// replace with the standard in element
				MATCHERS[i] = new InPatternMatcher();
			}
			else {
				MATCHERS[i] = matchers[i];
			}
		}
	}

	private String varName;
	private Element listElement;

	public ForeachDirective (String contents, ParsingSession parseData, int contentPosition) throws ParseException {
		parseContents(contents, parseData, contentPosition);
	}

	private void parseContents (String contents, ParsingSession session, int contentPosition) throws ParseException {
		java.util.List elements = parseElements(contents, session, contentPosition);
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
			if (elements.size() == 1 && elements.get(0) instanceof ListElement) {
				// bypass element parsing
				this.listElement = (Element) elements.get(0);
			}
			else {
				this.listElement = new VariableElement(elements, session);
			}
		}
		catch (IndexOutOfBoundsException e) {
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Improperly formed for expression: must have at least 3 tokens");
		}
	}

	protected DefaultElementFactory getContentParsingDefaultElementFactory() {
		return SpecialVariableDefaultEelementFactory.getInstance();
	}



	public void getMacroInstances(ZSContext context, List macroInstanceList) {
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
						appendMacroInstances(getChildren(), context, macroInstanceList);
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
						appendMacroInstances(getChildren(), context, macroInstanceList);
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
						appendMacroInstances(getChildren(), context, macroInstanceList);
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
						appendMacroInstances(getChildren(), context, macroInstanceList);
						context.put(TOKEN_INDEX, new Integer(++i));
					}
				}
			}
			else {
				// just put the object in the context and loop 1 time
				context = new NestedContextWrapper(context);
				context.put(TOKEN_INDEX, new Integer(0));
				context.put(TOKEN_HASNEXT, Boolean.FALSE);
				context.put(varName, list);
				appendMacroInstances(getChildren(), context, macroInstanceList);
			}
		}
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
				// just put the object in the context and loop 1 time
				context = new NestedContextWrapper(context);
				context.put(TOKEN_INDEX, new Integer(0));
				context.put(TOKEN_HASNEXT, Boolean.FALSE);
				context.put(varName, list);
				appendElements(getChildren(), context, sw);
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