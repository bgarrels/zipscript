package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractElement;

import java.io.StringWriter;
import java.util.List;

public class IdentifierElement extends AbstractElement {

	public void merge(ZSContext context, StringWriter sw) {
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("identifiers can not be evaluated as booleans", this);
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		throw new ExecutionException("identifiers can not be evaluated as objects", this);
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}

	public String getTokenValue() {
		return toString();
	}

	public List getChildren() {
		return null;
	}
}