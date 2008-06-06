package hudson.zipscript.parser.template.element.lang;

import java.io.StringWriter;
import java.util.List;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.template.element.special.SpecialElement;

public class IdentifierElement extends AbstractElement {

	public void merge(ZSContext context, StringWriter sw) {
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("identifiers can not be evaluated as booleans");
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("identifiers can not be evaluated as objects");
	}

	public ElementIndex normalize(int index, List elementList,
			ParseParameters parameters) throws ParseException {
		return null;
	}

	public String getTokenValue() {
		return toString();
	}
}