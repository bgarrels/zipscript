package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;

import java.util.List;

public class SpecialVariableElementImpl extends VariableElement implements SpecialStringElement {

	public SpecialVariableElementImpl(boolean silence, String text) throws ParseException {
		super(silence, text);
	}

	public ElementIndex normalize(
			int index, List elementList, ParseParameters parameters) throws ParseException {
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
			setPattern(pattern.toString());
		return null;
	}

	public String getTokenValue() {
		return getPattern();
	}
}