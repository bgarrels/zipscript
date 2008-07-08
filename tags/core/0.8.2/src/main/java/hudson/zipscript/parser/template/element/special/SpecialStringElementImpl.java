package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.TextElement;

import java.util.List;

public class SpecialStringElementImpl extends TextElement implements SpecialStringElement {

	public SpecialStringElementImpl(String text) {
		super(text);
	}

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters) throws ParseException {
		while (elementList.size() > index) {
			Element e = (Element) elementList.get(index);
			if (e instanceof SpecialElement) {
				elementList.remove(index);
				setText(getText() + ((SpecialElement) e).getTokenValue());
			}
			else {
				break;
			}
		}
		return null;
	}

	public String getTokenValue() {
		return getText();
	}

	public String toString() {
		return "'" + getText() + "'";
	}
}