package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.lang.variable.VariableTokenSeparatorElement;

import java.util.List;

public class DotElement extends IdentifierElement implements VariableTokenSeparatorElement {

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters)
			throws ParseException {
		return null;
	}

	public String toString() {
		return ".";
	}
}
