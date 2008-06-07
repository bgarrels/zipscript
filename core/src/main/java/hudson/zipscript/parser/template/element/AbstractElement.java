package hudson.zipscript.parser.template.element;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.comparator.ComparatorPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.math.MathPatternMatcher;
import hudson.zipscript.parser.template.element.group.GroupPatternMatcher;
import hudson.zipscript.parser.template.element.group.MapPatternMatcher;
import hudson.zipscript.parser.template.element.lang.AssignmentPatternMatcher;
import hudson.zipscript.parser.template.element.lang.CommaPatternMatcher;
import hudson.zipscript.parser.template.element.lang.DotPatternMatcher;
import hudson.zipscript.parser.template.element.lang.WhitespacePatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.VariablePatternMatcher;
import hudson.zipscript.parser.template.element.special.BooleanPatternMatcher;
import hudson.zipscript.parser.template.element.special.DefaultVariablePatternMatcher;
import hudson.zipscript.parser.template.element.special.InPatternMatcher;
import hudson.zipscript.parser.template.element.special.NullPatternMatcher;
import hudson.zipscript.parser.template.element.special.NumericPatternMatcher;
import hudson.zipscript.parser.template.element.special.SpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;
import hudson.zipscript.parser.template.element.special.StringPatternMatcher;

import java.util.List;


public abstract class AbstractElement implements Element {

	long elementPosition;
	int elementLength;

	public String toString() {
		if (this instanceof SpecialElement) {
			return ((SpecialElement) this).getTokenValue();
		}
		else if (this instanceof SpecialStringElement) {
			return "'" + ((SpecialStringElement) this).getTokenValue() + "'";
		}
		else return super.toString();
	}

	public long getElementPosition() {
		return elementPosition;
	}

	public void setElementPosition(long elementPosition) {
		this.elementPosition = elementPosition;
	}

	public int getElementLength() {
		return elementLength;
	}

	public void setElementLength(int elementLength) {
		this.elementLength = elementLength;
	}

	protected Element parseElement (String contents) throws ParseException {
		return ExpressionParser.getInstance().parseToElement(
				contents, getContentParsingPatternMatchers(),
				getContentParsingDefaultElementFactory());
	}

	protected List parseElements (String contents) throws ParseException {
		return ExpressionParser.getInstance().parse(
				contents, getContentParsingPatternMatchers(), getContentParsingDefaultElementFactory(),
				new ParseParameters(true, true));
	}

	protected PatternMatcher[] getContentParsingPatternMatchers () {
		return ZipEngine.VARIABLE_MATCHERS;
	}

	protected DefaultElementFactory getContentParsingDefaultElementFactory () {
		return DefaultVariablePatternMatcher.getInstance();
	}
}