package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;
import hudson.zipscript.parser.template.element.comparator.ComparatorElement;
import hudson.zipscript.parser.template.element.group.GroupElement;
import hudson.zipscript.parser.template.element.group.MapElement;
import hudson.zipscript.parser.template.element.lang.CommaElement;
import hudson.zipscript.parser.template.element.lang.DotElement;
import hudson.zipscript.parser.template.element.lang.TextElement;
import hudson.zipscript.parser.template.element.lang.WhitespaceElement;
import hudson.zipscript.parser.template.element.lang.variable.special.VarSpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;
import hudson.zipscript.parser.util.SessionUtil;
import hudson.zipscript.parser.util.StringUtil;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VariableElement extends AbstractElement implements Element {

	boolean silence = false;
	private List children;
	private String pattern;
	private List specialElements;

	private boolean suppressNullErrors;

	public VariableElement (
			boolean silence, String pattern, ParsingSession session, int contentIndex) throws ParseException {
		setElementPosition(contentIndex);
		setElementLength(pattern.length());
		this.silence = silence;
		setPattern(pattern, session, contentIndex);
		this.suppressNullErrors = SessionUtil.getProperty(Constants.SUPPRESS_NULL_ERRORS, false, session);
	}

	public VariableElement (List elements, ParsingSession session) throws ParseException {
		this.children = parse(elements, session);
	}

	public void setPattern (String pattern, ParsingSession session, int contentIndex) throws ParseException {
		pattern = pattern.trim();
		
		if (null == this.children) this.children = new ArrayList();
		this.children.clear();
		if (!quickScan(pattern)) {
			if (session.isVariablePatternRecognized(pattern))
				throw new ParseException(this, "Invalid variable reference '" + pattern + "'");
			session.setReferencedVariable(pattern);
			ParseParameters parameters = new ParseParameters(false, true);
			ParseParameters currentParameters = session.getParameters();
			session.setParameters(parameters);
			java.util.List elements = ExpressionParser.getInstance().parse(
					pattern, ZipEngine.VARIABLE_MATCHERS, SpecialVariableDefaultEelementFactory.getInstance(),
					session, contentIndex).getElements();
			session.setParameters(currentParameters);
			this.children = parse(elements, session);
			session.markValidVariablePattern(pattern);
		}
	}

	private static final char[] normalChars = new char[] {'_','-'};
	private boolean quickScan (String pattern) throws ParseException {
		int trimIndex = 0;
		if (pattern.startsWith("$")) trimIndex ++;
		if (pattern.indexOf('!') == trimIndex) trimIndex ++;
		if (pattern.indexOf('{') == trimIndex) trimIndex ++;
		if (trimIndex > 0) {
			if (pattern.length() > 1)
				pattern = pattern.substring(trimIndex, pattern.length()-1);
			else
				throw new ParseException(this, "Invalid variable reference '" + this + "'");
		}
		
		for (int i=0; i<pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == ':')) return false;
		}
		this.children.add(new RootChild(pattern));
		return true;
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		Object obj = objectValue(context);
		if (null != obj) {
			if (obj instanceof ToStringWithContextElement) {
				((ToStringWithContextElement) obj).append(context, sw);
			}
			else {
				StringUtil.append(obj.toString(), sw);
			}
		}
		else {
			if (!silence) {
				if (suppressNullErrors)
					StringUtil.append(toString(), sw);
				else
					throw new ExecutionException("Value evaluated as null " + this.toString(), this);
			}
		}
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		Object rtn = null;
		int count = 0;
		boolean isNullAllowed = false;
		if (null == pattern) {
			for (Iterator i=children.iterator(); i.hasNext(); ) {
				VariableChild child = (VariableChild) i.next();
				rtn = child.execute(rtn, context);
				if (!child.shouldReturnSomething()) isNullAllowed = true;
				if (null == rtn) {
					break;
				}
				count ++;
			}
		}
		else {
			// bypass path and get the full path from the context
			rtn = context.get(pattern);
		}
		if (isNullAllowed && null == rtn) return "";
		if (null == rtn && count == 0) {
			StringBuffer sb = new StringBuffer();
			for (Iterator i=children.iterator(); i.hasNext(); ) {
				if (sb.length() > 0) sb.append('.');
				VariableChild child = (VariableChild) i.next();
				sb.append(child.getPropertyName());
			}
			pattern = sb.toString();
			rtn = context.get(pattern);
		}
		if (null != specialElements) {
			if (null == rtn
					&& (((VariableTokenSeparatorElement) specialElements.get(0)).requiresInput(context))) {
				return null;
			}
			for (int i=0; i<specialElements.size(); i++) {
				VariableTokenSeparatorElement e = (VariableTokenSeparatorElement) specialElements.get(i);
				if (!e.requiresInput(context)) {
					// a default element
					if (null == rtn) rtn = e.execute(rtn, context);
					if (null != rtn) return rtn;
				}
				else {
					rtn = e.execute(rtn, context);
				}
			}
		}
		return rtn;
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		Object obj = objectValue(context);
		if (null == obj)
			throw new ExecutionException("The variable '" + this + "' is null and can not be evaluated to a boolean", this);
		else if (obj instanceof Boolean)
			return ((Boolean) obj).booleanValue();
		else
			throw new ExecutionException("The variable " + this + " could not be evaluated to a boolean", this);
	}

	public ElementIndex normalize(
			int index, List elementList, ParsingSession session) throws ParseException {
		// if the next element over is a special char - process for variable
		if (elementList.size() > index) {
			Element nextElement = (Element) elementList.get(index);
			while (nextElement instanceof VarDefaultElement
					|| nextElement instanceof VarSpecialElement) {
				if (null == specialElements) specialElements = new ArrayList(1);
	
				Element e = (Element) elementList.remove(index);
				ElementIndex ei = e.normalize(index, elementList, session);
				if (null != ei) {
					index = ei.getIndex();
					e = ei.getElement();
				}
				specialElements.add(e);
			}
		}
		return null;
	}

	public String getPattern () {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<children.size(); i++) {
			if (i > 0) sb.append(".");
			sb.append(children.get(i));
		}
		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('$');
		if (silence) sb.append('!');
		sb.append('{');
		sb.append(getPattern());
		sb.append('}');
		return sb.toString();
	}

	private List parse (
			List elements, ParsingSession session) throws ParseException {
		List children = new ArrayList();
		boolean started = false;
		boolean wasWhitespace = false;
		boolean wasSeparator = false;
		for (int i=0; i<elements.size(); i++) {
			Element e = (Element) elements.get(i);
			if (!started && e instanceof WhitespaceElement)
				continue;
			started = true;

			if (e instanceof WhitespaceElement) {
				wasWhitespace = true;
			}
			else if (e instanceof DotElement) {
				wasSeparator = true;
				wasWhitespace = false;
			}
			else if (e instanceof SpecialElement) {
				if (wasWhitespace)
					throw new ParseException(e, "Invalid whitespace");
				if (!wasSeparator && children.size() > 0)
					throw new ParseException(e, "Invalid sequence after sparator '" + e.toString() + "'");
				wasWhitespace = false;
				wasSeparator = false;
				if (children.size() == 0)
					children.add(new RootChild(((SpecialElement) e).getTokenValue()));
				else
					children.add(new PropertyChild(((SpecialElement) e).getTokenValue(), this));
			}
			else if (e instanceof SpecialStringElement) {
				if (wasWhitespace)
					throw new ParseException(e, "Invalid whitespace");
				if (!wasSeparator && children.size() > 0)
					throw new ParseException(e, "Invalid sequence after sparator '" + e.toString() + "'");
				wasWhitespace = false;
				wasSeparator = false;
				addChildProperty(((SpecialStringElement) e).getTokenValue(), children);
				if (e instanceof VariableElement) {
					// check for special directives
					if (null != ((VariableElement) e).specialElements) {
						if (null == this.specialElements) specialElements = new ArrayList(1);
						this.specialElements.addAll(((VariableElement) e).specialElements);
					}
				}
			}
			else if (e instanceof TextElement) {
				if (wasWhitespace)
					throw new ParseException(e, "Invalid whitespace");
				if (!wasSeparator && children.size() > 0)
					throw new ParseException(e, "Invalid sequence after sparator '" + e.toString() + "'");
				wasWhitespace = false;
				wasSeparator = false;
				if (children.size() == 0) {
					// qualified path to use as context key
					children.add(new RootChild(((TextElement) e).getText()));
				}
				else {
					addChildProperty(((TextElement) e).getText(), children);
				}
			}
			else if (e instanceof VariableElement) {
				if (wasSeparator) {
					// dynamic path
					children.add(new DynamicChild(e));
					wasSeparator = false;
				}
				else {
					if (elements.size() == 1) {
						// single variable element - just use it
						return ((VariableElement) e).children;
					}
					else {
						throw new ParseException(e, "Invalid dynamic variable element '" + e.toString() + "'.  This must be the first token or follow a separator");
					}
				}
			}
			else if (e instanceof GroupElement) {
				if (children.size() == 0)
					throw new ParseException(e, "Invalid element '" + e.toString() + "'");
				else if (wasWhitespace)
					throw new ParseException(e, "Invalid sequence after whitespace '" + e.toString() + "'");
				else if (wasSeparator)
					throw new ParseException(e, "Invalid sequence after separator '" + e.toString() + "'");
				else {
					VariableChild child = (VariableChild) children.remove(children.size()-1);
					if (null == child.getPropertyName())
						throw new ParseException(e, "Invalid sequence '" + e.toString() + "'");
					List parameters = getMethodParameters((GroupElement) e, session);
					children.add(new MethodChild(child.getPropertyName(), parameters, this));
				}
				wasWhitespace = false;
			}
			else if (e instanceof VarDefaultElement || e instanceof VarSpecialElement) {
				if (null == specialElements) specialElements = new ArrayList();
				specialElements.add(elements.get(i));
			}
			else if (e instanceof MapElement) {
				MapElement me = (MapElement) e;
				if (me.getChildren().size() == 1) {
					children.add(new MapChild((Element) me.getChildren().get(0)));
				}
				else {
					children.add(new MapChild(
							new VariableElement(me.getChildren(), session)));
				}
			}
			else if (e instanceof ComparatorElement && elements.size() == 1) {
				children.add(new ElementWrapperChild(e));
			}
			else {
				throw new ParseException(e, "Invalid element detected '" + e.toString() + "'");
			}
		}
		return children;
	}

	private void addChildProperty (String s, List children) {
		if (children.size() == 0)
			children.add(new RootChild(s));
		else
			children.add(new PropertyChild(s, this));
	}

	private List getMethodParameters (GroupElement ge, ParsingSession parseData) throws ParseException {
		List parameters = new ArrayList();
		List t = new ArrayList();
		CommaElement lastSeparator = null;
		boolean lastElementWasComma = false;
		for (int i=0; i<ge.getChildren().size(); i++) {
			Element e = (Element) ge.getChildren().get(i);
			if (e instanceof CommaElement) {
				Element mpe = getMethodParameterElement(t, parseData);
				if (null != mpe)
					parameters.add(mpe);
				t.clear();
				lastSeparator = (CommaElement) e;
				lastElementWasComma = true;
			}
			else {
				if (lastElementWasComma && e instanceof WhitespaceElement)
					continue;
				t.add(e);
				lastElementWasComma = false;
			}
		}

		boolean whitespaceOnly = true;
		for (int i=0; i<t.size(); i++)
			if (!(t.get(i) instanceof WhitespaceElement)) whitespaceOnly = false;
		if (whitespaceOnly && null != lastSeparator)
			throw new ParseException(lastSeparator, "Invalid sequence '" + lastSeparator.toString() + "'");
		if (!whitespaceOnly) {
			Element mpe = getMethodParameterElement(t, parseData);
			if (null != mpe)
				parameters.add(mpe);
		}
		return parameters;
	}

	private Element getMethodParameterElement (
			List elements, ParsingSession parseData) throws ParseException {
		if (null == elements || elements.size() == 0) return null;
		if (elements.size() == 1) {
			Element e = (Element) elements.get(0);
			if (e instanceof SpecialStringElement) {
				return new VariableElement(
						false, ((SpecialStringElement) e).getTokenValue(), parseData, (int) e.getElementPosition());
			}
			else {
				return e;
			}
		}
		else {
			return new VariableElement(elements, parseData);
		}
	}

	protected void addSpecialElement (Element e) {
		if (null == specialElements) specialElements = new ArrayList(1);
		specialElements.add(e);
	}

	public List getChildren() {
		return null;
	}
}