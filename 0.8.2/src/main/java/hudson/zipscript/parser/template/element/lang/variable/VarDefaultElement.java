package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.IdentifierElement;

import java.util.List;

public class VarDefaultElement extends IdentifierElement implements VariableTokenSeparatorElement {

	private Element executeElement;
	public ElementIndex normalize(int index, List elementList, ParsingSession session)
			throws ParseException {
		if (elementList.size() > index) {
			executeElement = (Element) elementList.remove(index);
			if (executeElement instanceof SpecialVariableElementImpl)
				((SpecialVariableElementImpl) executeElement).setShouldEvaluateSeparators(true);
			executeElement.normalize(index, elementList, session);
			return null;
		}
		else {
			throw new ParseException(this, "Default elements must have a value");
		}
	}

	public String toString() {
		return "!" + executeElement;
	}

	public Object execute(Object source, ZSContext context) {
		return objectValue(context);
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		return executeElement.objectValue(context);
	}

	public boolean requiresInput(ZSContext context) {
		return false;
	}
}