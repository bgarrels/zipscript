package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.ZipEngine;
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
import hudson.zipscript.parser.template.element.group.GroupElement;
import hudson.zipscript.parser.template.element.group.MapElement;
import hudson.zipscript.parser.template.element.lang.CommaElement;
import hudson.zipscript.parser.template.element.lang.DotElement;
import hudson.zipscript.parser.template.element.lang.TextElement;
import hudson.zipscript.parser.template.element.lang.WhitespaceElement;
import hudson.zipscript.parser.template.element.lang.variable.special.VarSpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class VariableElement extends AbstractElement implements Element {

	boolean silence = false;
	private List children;
	private String originalContent;
	private List specialElements;

	public VariableElement (
			boolean silence, String pattern, ParsingSession session, int contentIndex) throws ParseException {
		this.silence = silence;
		setPattern(pattern, session, contentIndex);
	}

	public VariableElement (List elements, ParsingSession session) throws ParseException {
		this.children = parse(elements, session);
	}

	public void setPattern (String pattern, ParsingSession session, int contentIndex) throws ParseException {
		if (!silence)
			originalContent = pattern;
		pattern = pattern.trim();
		
		if (null == this.children) this.children = new ArrayList();
		this.children.clear();
		if (!quickScan(pattern)) {
			if (session.isVariablePatternRecognized(pattern))
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid variable reference '" + pattern + "'");
			session.setReferencedVariable(pattern);
			ParseParameters parameters = new ParseParameters(false, true);
			ParseParameters currentParameters = session.getParameters();
			session.setParameters(parameters);
			java.util.List elements = ExpressionParser.getInstance().parse(
					pattern, ZipEngine.VARIABLE_MATCHERS, SpecialVariableDefaultEelementFactory.getInstance(),
					session, contentIndex).getElements();
			session.setParameters(currentParameters);
			this.children = parse(elements, session);
		}
	}

	private static final char[] normalChars = new char[] {'_','-'};
	private boolean quickScan (String pattern) {
		for (int i=0; i<pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == ':')) return false;
		}
		this.children.add(new RootChild(pattern));
		return true;
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		Object obj = objectValue(context);
		if (null != obj) {
			if (obj instanceof ToStringWithContextElement) {
				sw.write(((ToStringWithContextElement) obj).toString(context));
			}
			else {
				sw.write(obj.toString());
			}
		}
		else {
			if (!silence) {
				throw new ExecutionException("Value evaluated as null " + this.toString(), this);
			}
		}
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		Object rtn = null;
		for (Iterator i=children.iterator(); i.hasNext(); ) {
			rtn = ((VariableChild) i.next()).execute(rtn, context);
			if (null == rtn) break;
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
		if (null == obj) return false;
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

	private List parse (List elements, ParsingSession parseData) throws ParseException {
		List children = new ArrayList();
		if (elements.size() == 1) {
			elements.add(0, new ElementWrapperChild((Element) elements.remove(0)));
			return elements;
		}

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
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid whitespace");
				if (!wasSeparator && children.size() > 0)
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid sequence after sparator '" + e.toString() + "'");
				wasWhitespace = false;
				wasSeparator = false;
				if (children.size() == 0)
					children.add(new RootChild(((SpecialElement) e).getTokenValue()));
				else
					children.add(new PropertyChild(((SpecialElement) e).getTokenValue(), this));
			}
			else if (e instanceof SpecialStringElement) {
				if (wasWhitespace)
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid whitespace");
				if (!wasSeparator && children.size() > 0)
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid sequence after sparator '" + e.toString() + "'");
				wasWhitespace = false;
				wasSeparator = false;
				addChildProperty(((SpecialStringElement) e).getTokenValue(), children);
			}
			else if (e instanceof TextElement) {
				if (children.size() == 0)
					addChildProperty(((TextElement) e).getText(), children);
				else
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid text element detected '" + e.toString() + "'");
				wasWhitespace = false;
			}
			else if (e instanceof GroupElement) {
				if (children.size() == 0)
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid element '" + e.toString() + "'");
				else if (wasWhitespace)
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid sequence after whitespace '" + e.toString() + "'");
				else if (wasSeparator)
					throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid sequence after separator '" + e.toString() + "'");
				else {
					VariableChild child = (VariableChild) children.remove(children.size()-1);
					if (null == child.getPropertyName())
						throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid sequence '" + e.toString() + "'");
					List parameters = getMethodParameters((GroupElement) e, parseData);
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
							new VariableElement(me.getChildren(), parseData)));
				}
			}
			else {
				throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, e, "Invalid element detected '" + e.toString() + "'");
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
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, lastSeparator, "Invalid sequence '" + lastSeparator.toString() + "'");
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
}