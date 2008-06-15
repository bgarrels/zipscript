package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.lang.IdentifierElement;

import java.util.List;

public class VarSpecialElement extends IdentifierElement implements VariableTokenSeparatorElement {

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters)
			throws ParseException {
		return null;
	}

	public Object execute(Object source, ZSContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return "?";
	}

	public boolean requiresInput(ZSContext context) {
		return true;
	}
}
