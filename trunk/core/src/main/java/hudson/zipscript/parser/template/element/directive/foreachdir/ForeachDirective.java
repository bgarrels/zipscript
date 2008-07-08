package hudson.zipscript.parser.template.element.directive.foreachdir;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DebugElementContainerElement;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.comparator.InComparatorPatternMatcher;
import hudson.zipscript.parser.template.element.directive.LoopingDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAware;
import hudson.zipscript.parser.template.element.group.ListElement;
import hudson.zipscript.parser.template.element.lang.variable.SpecialVariableDefaultEelementFactory;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;
import hudson.zipscript.parser.template.element.special.InElement;
import hudson.zipscript.parser.template.element.special.InPatternMatcher;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ForeachDirective extends NestableElement implements MacroInstanceAware, LoopingDirective, DebugElementContainerElement {

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
	private boolean isInMacroDefinition;
	private List internalElements;

	public ForeachDirective (String contents, ParsingSession session, int contentPosition)
	throws ParseException {
		setParsingSession(session);
		parseContents(contents, session, contentPosition);
	}

	public List getInternalElements() {
		return internalElements;
	}

	private void parseContents (
			String contents, ParsingSession session, int contentPosition)
	throws ParseException {
		// see if we are in a macro definition
		for (Iterator i=session.getNestingStack().iterator(); i.hasNext(); ) {
			if (i.next() instanceof MacroDirective) {
				isInMacroDefinition = true;
				break;
			}
		}

		List elements = parseElements(contents, session, contentPosition);
		this.internalElements = elements;
		try {
			Element e = (Element) elements.get(0);
			if (e instanceof SpecialStringElement) {
				this.varName = ((SpecialStringElement) elements.get(0)).getTokenValue();
				elements.remove(0);
			}
			else {
				throw new ParseException(e, "Invalid sequence.  Expecting variable name");
			}
			e = (Element) elements.remove(0);
			if (!(e instanceof InElement))
				throw new ParseException(e, "Improperly formed for expression: 'in' should be second token '" + this + "'");
			if (elements.size() == 1 && elements.get(0) instanceof ListElement) {
				// bypass element parsing
				this.listElement = (Element) elements.get(0);
			}
			else {
				this.listElement = new VariableElement(elements, session);
			}
		}
		catch (IndexOutOfBoundsException e) {
			throw new ParseException(contentPosition, "Improperly formed for expression: must have at least 3 tokens");
		}
	}

	protected DefaultElementFactory getContentParsingDefaultElementFactory() {
		return SpecialVariableDefaultEelementFactory.getInstance();
	}

	public void getMatchingTemplateDefinedParameters(
			ZSContext context, List macroInstanceList, MacroDirective macro, Map additionalContextEntries) {
		Object list = listElement.objectValue(context);
		if (null != list) {
			if (list instanceof Object[]) {
				Object[] arr = (Object[]) list;
				if (arr.length > 0) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					Integer int0 = new Integer(0);
					additionalContextEntries.put(TOKEN_INDEX, int0);
					context.put(TOKEN_INDEX, int0, false);
					int checkNum = arr.length-1;
					additionalContextEntries.put(TOKEN_HASNEXT, Boolean.TRUE);
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					while (i<arr.length) {
						if (i >= checkNum) {
							additionalContextEntries.put(TOKEN_HASNEXT, Boolean.FALSE);
							context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						}
						additionalContextEntries.put(varName, arr[i]);
						context.put(varName, arr[i], false);
						appendTemplateDefinedParameters(getChildren(), context, macroInstanceList, macro, additionalContextEntries);
						Integer index = new Integer(++i);
						additionalContextEntries.put(TOKEN_INDEX, index);
						context.put(TOKEN_INDEX, index, false);
					}
				}
			}
			else if (list instanceof Collection) {
				Collection c = (Collection) list;
				if (c.size() > 0) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					Integer int0 = new Integer(0);
					additionalContextEntries.put(TOKEN_INDEX, int0);
					context.put(TOKEN_INDEX, int0, false);
					additionalContextEntries.put(TOKEN_HASNEXT, Boolean.TRUE);
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					for (Iterator iter=c.iterator(); iter.hasNext(); ) {
						Object nextVal = iter.next();
						additionalContextEntries.put(varName, nextVal);
						context.put(varName, nextVal, false);
						if (!iter.hasNext()) {
							additionalContextEntries.put(TOKEN_HASNEXT, Boolean.FALSE);
							context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						}
						appendTemplateDefinedParameters(getChildren(), context, macroInstanceList, macro, additionalContextEntries);
						Integer index = new Integer(++i);
						additionalContextEntries.put(TOKEN_INDEX, index);
						context.put(TOKEN_INDEX, index, false);
					}
				}
			}
			else if (list instanceof Iterator) {
				Iterator iter = (Iterator) list;
				if (iter.hasNext()) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					Integer int0 = new Integer(0);
					additionalContextEntries.put(TOKEN_INDEX, int0);
					context.put(TOKEN_INDEX, int0, false);
					additionalContextEntries.put(TOKEN_HASNEXT, Boolean.TRUE);
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					while (iter.hasNext()) {
						Object nextVal = iter.next();
						additionalContextEntries.put(varName, nextVal);
						context.put(varName, nextVal, false);
						if (!iter.hasNext()) {
							additionalContextEntries.put(TOKEN_HASNEXT, Boolean.FALSE);
							context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						}
						appendTemplateDefinedParameters(getChildren(), context, macroInstanceList, macro, additionalContextEntries);
						Integer index = new Integer(++i);
						additionalContextEntries.put(TOKEN_INDEX, index);
						context.put(TOKEN_INDEX, index, false);
					}
				}
			}
			else if (list instanceof Enumeration) {
				Enumeration enumeration = (Enumeration) list;
				if (enumeration.hasMoreElements()) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					Integer int0 = new Integer(0);
					additionalContextEntries.put(TOKEN_INDEX, int0);
					context.put(TOKEN_INDEX, int0, false);
					additionalContextEntries.put(TOKEN_HASNEXT, Boolean.TRUE);
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					while (enumeration.hasMoreElements()) {
						Object nextVal = enumeration.nextElement();
						additionalContextEntries.put(varName, nextVal);
						context.put(varName, nextVal, false);
						if (enumeration.hasMoreElements()) {
							additionalContextEntries.put(TOKEN_HASNEXT, Boolean.FALSE);
							context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						}
						appendTemplateDefinedParameters(getChildren(), context, macroInstanceList, macro, additionalContextEntries);
						Integer index = new Integer(++i);
						additionalContextEntries.put(TOKEN_INDEX, index);
						context.put(TOKEN_INDEX, index, false);
					}
				}
			}
			else {
				// just put the object in the context and loop 1 time
				Integer int0 = new Integer(0);
				context = new NestedContextWrapper(context, this);
				additionalContextEntries.put(TOKEN_INDEX, int0);
				context.put(TOKEN_INDEX, int0, false);
				additionalContextEntries.put(TOKEN_HASNEXT, Boolean.FALSE);
				context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
				additionalContextEntries.put(varName, list);
				context.put(varName, list, false);
				appendTemplateDefinedParameters(getChildren(), context, macroInstanceList, macro, additionalContextEntries);
			}
		}
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		Object list = listElement.objectValue(context);
		if (null != list) {
			if (list instanceof Object[]) {
				Object[] arr = (Object[]) list;
				if (arr.length > 0) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					context.put(TOKEN_INDEX, new Integer(0), false);
					int checkNum = arr.length-1;
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					while (i<arr.length) {
						if (getParsingSession().isDebug()) {
							System.out.println("Executing: " + this.toString() + " (" + i + ")");
						}
						if (i >= checkNum) context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						context.put(varName, arr[i], false);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i), false);
					}
				}
			}
			else if (list instanceof Collection) {
				Collection c = (Collection) list;
				if (c.size() > 0) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					context.put(TOKEN_INDEX, new Integer(0), false);
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					for (Iterator iter=c.iterator(); iter.hasNext(); ) {
						if (getParsingSession().isDebug()) {
							System.out.println("Executing: " + this.toString() + " (" + i + ")");
						}
						context.put(varName, iter.next(), false);
						if (!iter.hasNext()) context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i), false);
					}
				}
			}
			else if (list instanceof Iterator) {
				Iterator iter = (Iterator) list;
				if (iter.hasNext()) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					context.put(TOKEN_INDEX, new Integer(0), false);
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					while (iter.hasNext()) {
						if (getParsingSession().isDebug()) {
							System.out.println("Executing: " + this.toString() + " (" + i + ")");
						}
						context.put(varName, iter.next(), false);
						if (!iter.hasNext()) context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i), false);
					}
				}
			}
			else if (list instanceof Enumeration) {
				Enumeration enumeration = (Enumeration) list;
				if (enumeration.hasMoreElements()) {
					int i=0;
					context = new NestedContextWrapper(context, this);
					context.put(TOKEN_INDEX, new Integer(0), false);
					context.put(TOKEN_HASNEXT, Boolean.TRUE, false);
					while (enumeration.hasMoreElements()) {
						if (getParsingSession().isDebug()) {
							System.out.println("Executing: " + this.toString() + " (" + i + ")");
						}
						context.put(varName, enumeration.nextElement(), false);
						if (enumeration.hasMoreElements()) context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
						appendElements(getChildren(), context, sw);
						context.put(TOKEN_INDEX, new Integer(++i), false);
					}
				}
			}
			else {
				// just put the object in the context and loop 1 time
				if (getParsingSession().isDebug()) {
					System.out.println("Executing: " + this.toString() + " (0)");
				}
				context = new NestedContextWrapper(context, this);
				context.put(TOKEN_INDEX, new Integer(0), false);
				context.put(TOKEN_HASNEXT, Boolean.FALSE, false);
				context.put(varName, list, false);
				appendElements(getChildren(), context, sw);
			}
		}
		if (getParsingSession().isDebug()) {
			System.out.println("Completed:" + this.toString());
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
		if (null != listElement)
			return "[#foreach " + varName + " in " + listElement + "]";
		else
			return "[#foreach " + varName + " in ?]";
	}

	public boolean isInMacroDefinition() {
		return isInMacroDefinition;
	}
}