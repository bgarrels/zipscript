package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.lang.IdentifierElement;

import java.util.List;

public class VarSpecialElement extends IdentifierElement {

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters)
			throws ParseException {
		return null;
	}

	public String toString() {
		return "?";
	}
}
