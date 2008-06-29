package hudson.zipscript.parser.template.element;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.special.DefaultVariablePatternMatcher;
import hudson.zipscript.parser.template.element.special.SpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

import java.util.List;


public abstract class AbstractElement implements Element {

	private long elementPosition;
	private int elementLength;
	private ParsingSession parsingSession;

	public AbstractElement() {
		this.parsingSession = parsingSession;
	}

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

	protected Element parseElement (
			String contents, int startPosition, ParsingSession parsingSession)
	throws ParseException {
		return ExpressionParser.getInstance().parseToElement(
				contents, getContentParsingPatternMatchers(),
				getContentParsingDefaultElementFactory(), startPosition, parsingSession.getMacroManager());
	}

	protected List parseElements (String contents, ParsingSession session, int startPosition) throws ParseException {
		ParseParameters oldParameters = session.getParameters();
		session.setParameters(new ParseParameters(true, true));
		List rtn = ExpressionParser.getInstance().parse(
				contents, getContentParsingPatternMatchers(), getContentParsingDefaultElementFactory(),
				session, startPosition).getElements();
		session.setParameters(oldParameters);
		return rtn;
	}

	protected PatternMatcher[] getContentParsingPatternMatchers () {
		return ZipEngine.VARIABLE_MATCHERS;
	}

	protected DefaultElementFactory getContentParsingDefaultElementFactory () {
		return DefaultVariablePatternMatcher.getInstance();
	}

	public ParsingSession getParsingSession() {
		return parsingSession;
	}

	public void setParsingSession(ParsingSession parsingSession) {
		this.parsingSession = parsingSession;
	}
}