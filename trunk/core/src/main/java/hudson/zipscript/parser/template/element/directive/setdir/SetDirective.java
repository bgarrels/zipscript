package hudson.zipscript.parser.template.element.directive.setdir;

import java.io.StringWriter;
import java.util.List;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirective;
import hudson.zipscript.parser.template.element.lang.AssignmentElement;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;
import hudson.zipscript.parser.template.element.special.InElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

public class SetDirective extends AbstractDirective {

	private String varName;
	private Element setElement;

	public SetDirective (String contents, ParsingSession session) throws ParseException {
		parseContents(contents, session);
	}

	private void parseContents (String contents, ParsingSession session) throws ParseException {
		java.util.List elements = parseElements(contents, session);
		try {
			if (elements.get(0) instanceof SpecialStringElement) {
				this.varName = ((SpecialStringElement) elements.remove(0)).getTokenValue();
			}
			else {
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid sequence.  Expecting variable name");
			}
			if (!(elements.remove(0) instanceof AssignmentElement))
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid sequence.  Expecting '='");
			if (elements.size() > 1)
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid sequence.  Improperly formed set expression");
			else
				this.setElement = (Element) elements.get(0);
		}
		catch (IndexOutOfBoundsException e) {
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Improperly formed set expression: must have at least 3 tokens");
		}
	}

	public void merge(ZSContext context, StringWriter sw)
			throws ExecutionException {
		context.put(varName, setElement.objectValue(context));
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}
}