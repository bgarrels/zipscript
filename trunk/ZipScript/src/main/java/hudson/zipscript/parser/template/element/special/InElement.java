package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.lang.IdentifierElement;
import hudson.zipscript.parser.util.SpecialElementNormalizer;

import java.util.List;

public class InElement extends IdentifierElement implements SpecialElement {

	private static final InElement instance = new InElement();
	public static final InElement getInstance () {
		return instance;
	}

	private InElement () {}

	public String getTokenValue() {
		return "in";
	}

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters) throws ParseException {
		return SpecialElementNormalizer.normalizeSpecialElement(this, index, elementList, parameters);
	}

	
}
