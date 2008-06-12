package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;

import java.util.List;

public class SpecialVariableElementImpl extends VariableElement implements SpecialStringElement {

	public SpecialVariableElementImpl(
			boolean silence, String text, ParsingSession session, int contentPosition) throws ParseException {
		super(silence, text, session, contentPosition);
	}

	public ElementIndex normalize(
			int index, List elementList, ParsingSession session) throws ParseException {
		StringBuffer pattern = null;
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
			else {
				break;
			}
		}
		if (null != pattern)
			setPattern(pattern.toString(), session, 0);
		return null;
	}

	public String getTokenValue() {
		return getPattern();
	}
}