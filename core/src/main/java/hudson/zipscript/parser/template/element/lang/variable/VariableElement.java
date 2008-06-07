package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.comparator.math.MathPatternMatcher;
import hudson.zipscript.parser.template.element.group.GroupElement;
import hudson.zipscript.parser.template.element.group.GroupPatternMatcher;
import hudson.zipscript.parser.template.element.group.MapElement;
import hudson.zipscript.parser.template.element.group.MapPatternMatcher;
import hudson.zipscript.parser.template.element.lang.CommaElement;
import hudson.zipscript.parser.template.element.lang.CommaPatternMatcher;
import hudson.zipscript.parser.template.element.lang.DotElement;
import hudson.zipscript.parser.template.element.lang.DotPatternMatcher;
import hudson.zipscript.parser.template.element.lang.TextElement;
import hudson.zipscript.parser.template.element.lang.WhitespaceElement;
import hudson.zipscript.parser.template.element.lang.WhitespacePatternMatcher;
import hudson.zipscript.parser.template.element.special.BooleanPatternMatcher;
import hudson.zipscript.parser.template.element.special.NullPatternMatcher;
import hudson.zipscript.parser.template.element.special.NumericPatternMatcher;
import hudson.zipscript.parser.template.element.special.SpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialStringDefaultEelementFactory;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;
import hudson.zipscript.parser.template.element.special.SpecialVariableDefaultEelementFactory;
import hudson.zipscript.parser.template.element.special.StringPatternMatcher;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class VariableElement extends AbstractElement implements Element {

	boolean silence = false;
	private List children;
	private String originalContent;

	public VariableElement (boolean silence, String pattern) throws ParseException {
		this.silence = silence;
		setPattern(pattern);
	}

	public VariableElement (List elements) throws ParseException {
		this.children = parse(elements);
	}

	public void setPattern (String pattern) throws ParseException {
		if (!silence)
			originalContent = pattern;
		pattern = pattern.trim();
		if (null == this.children) this.children = new ArrayList();
		this.children.clear();
		if (!quickScan(pattern)) {
			java.util.List elements = ExpressionParser.getInstance().parse(
					pattern, ZipEngine.VARIABLE_MATCHERS, SpecialVariableDefaultEelementFactory.getInstance(),
					new ParseParameters(false, true));
			this.children = parse(elements);
		}
	}

	private static final char[] normalChars = new char[] {'_','-'};
	private boolean quickScan (String pattern) {
		for (int i=0; i<pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (!Character.isLetterOrDigit(c) && c!='_' && c!='-') return false;
		}
		this.children.add(new RootChild(pattern));
		return true;
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		Object obj = objectValue(context);
		if (null != obj) {
			sw.append(obj.toString());
		}
		else {
			if (!silence) {
				throw new ExecutionException("Value evaluated as null " + this);
			}
		}
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		Object parent = null;
		for (Iterator i=children.iterator(); i.hasNext(); ) {
			parent = ((VariableChild) i.next()).execute(parent, context);
			if (null == parent) break;
		}
		return parent;
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		Object obj = objectValue(context);
		if (null == obj) return false;
		else if (obj instanceof Boolean)
			return ((Boolean) obj).booleanValue();
		else
			throw new ExecutionException("The variable " + this + " could not be evaluated to a boolean");
	}

	public ElementIndex normalize(
			int index, List elementList, ParseParameters parameters) throws ParseException {
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

	private List parse (List elements) throws ParseException {
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
					children.add(new PropertyChild(((SpecialElement) e).getTokenValue()));
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
					List parameters = getMethodParameters((GroupElement) e);
					children.add(new MethodChild(child.getPropertyName(), parameters));
				}
				wasWhitespace = false;
			}
			else if (e instanceof MapElement) {
				MapElement me = (MapElement) e;
				if (me.getChildren().size() == 1) {
					children.add(new MapChild((Element) me.getChildren().get(0)));
				}
				else {
					children.add(new MapChild(new VariableElement(me.getChildren())));
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
			children.add(new PropertyChild(s));
	}

	private List getMethodParameters (GroupElement ge) throws ParseException {
		List parameters = new ArrayList();
		List t = new ArrayList();
		CommaElement lastSeparator = null;
		boolean lastElementWasComma = false;
		for (int i=0; i<ge.getChildren().size(); i++) {
			Element e = (Element) ge.getChildren().get(i);
			if (e instanceof CommaElement) {
				Element mpe = getMethodParameterElement(t);
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
			Element mpe = getMethodParameterElement(t);
			if (null != mpe)
				parameters.add(mpe);
		}
		return parameters;
	}

	private Element getMethodParameterElement (List elements) throws ParseException {
		if (null == elements || elements.size() == 0) return null;
		if (elements.size() == 1) {
			Element e = (Element) elements.get(0);
			if (e instanceof SpecialStringElement) {
				return new VariableElement(false, ((SpecialStringElement) e).getTokenValue());
			}
			else {
				return e;
			}
		}
		else {
			return new VariableElement(elements);
		}
	}
}