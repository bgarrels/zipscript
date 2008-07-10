package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.group.GroupElement;
import hudson.zipscript.parser.template.element.lang.DotElement;
import hudson.zipscript.parser.template.element.special.SpecialElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

import java.util.List;

public class SpecialVariableElementImpl extends VariableElement implements SpecialStringElement {

	// normally we won't do this but there are occasions with variable defaults
	private boolean shouldEvaluateSeparators = true;
	
	public SpecialVariableElementImpl(
			String text, ParsingSession session, int contentPosition) throws ParseException {
		super(false, false, text, session, contentPosition);
	}

	/**
	 * Create the pattern text and have the VariableElement manage parsing the pattern
	 */
	public ElementIndex normalize(
			int index, List elementList, ParsingSession session) throws ParseException {
		StringBuffer pattern = null;
		boolean wasWhitespace = false;
		while (elementList.size() > index) {
			Element e = (Element) elementList.get(index);
			if (e instanceof SpecialElement) {
				elementList.remove(index);
				if (null == pattern) {
					pattern = new StringBuffer();
					pattern.append(getPattern());
				}
				pattern.append(((SpecialElement) e).getTokenValue());
				continue;
			}
			else if (e instanceof VariableTokenSeparatorElement) {
				elementList.remove(index);
				e.normalize(index, elementList, session);
				addSpecialElement(e);
			}
//			else if (e instanceof VariableElement) {
//				// dynamics variable path
//				if (null == pattern) {
//					pattern = new StringBuffer();
//					pattern.append(getPattern());
//				}
//				pattern.append(e);
//			}
			else if (isShouldEvaluateSeparators() && e instanceof GroupElement) {
				elementList.remove(index);
				if (null == pattern) {
					pattern = new StringBuffer();
					pattern.append(getPattern());
				}
				e.normalize(index, elementList, session);
				pattern.append(e);
			}
			else if (isShouldEvaluateSeparators() && e instanceof DotElement) {
				elementList.remove(index);
				if (null == pattern) {
					pattern = new StringBuffer();
					pattern.append(getPattern());
				}
				pattern.append(e.toString());
				if (elementList.size() > index) {
					e = (Element) elementList.remove(index);
					if (e instanceof SpecialElement) {
						pattern.append(((SpecialElement) e).getTokenValue());
						continue;
					}
					else if (e instanceof SpecialVariableElementImpl) {
						pattern.append(((SpecialVariableElementImpl) e).getTokenValue());
						continue;
					}
					else {
						throw new ParseException(this, "Invalid variable sequence '" + pattern + "'");
					}
				}
				else {
					throw new ParseException(this, "Invalid variable sequence '" + pattern + "'");
				}

			}
			else {
				break;
			}
		}
		if (null != pattern)
			setPattern(getStartToken() + pattern.toString() + getEndToken(), session, 0);
		return null;
	}

	public String getTokenValue() {
		return getPattern();
	}

	public boolean isShouldEvaluateSeparators() {
		return shouldEvaluateSeparators;
	}

	public void setShouldEvaluateSeparators(boolean shouldEvaluateSeparators) {
		this.shouldEvaluateSeparators = shouldEvaluateSeparators;
	}

	public String getStartToken () {
		return "${";
	}

	public String getEndToken () {
		return "}";
	}
}