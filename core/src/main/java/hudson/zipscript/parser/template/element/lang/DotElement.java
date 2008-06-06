package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;

import java.util.List;

public class DotElement extends IdentifierElement {

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters)
			throws ParseException {
		return null;
	}

	public String toString() {
		return ".";
	}
}
