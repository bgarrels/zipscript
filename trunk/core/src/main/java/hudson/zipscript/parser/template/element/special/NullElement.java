package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.util.SpecialElementNormalizer;

import java.io.StringWriter;
import java.util.List;

public class NullElement extends AbstractElement implements SpecialElement {

	private static final NullElement instance = new NullElement();
	public static final NullElement getInstance () {
		return instance;
	}

	private NullElement () {}

	public String getTokenValue() {
		return "null";
	}

	public void merge(ZSContext context, StringWriter sw) {
	}

	public boolean booleanValue(ZSContext context) {
		return false;
	}

	public Object objectValue(ZSContext context) {
		return null;
	}

	public ElementIndex normalize(int index, List elementList, ParsingSession session) throws ParseException {
		return SpecialElementNormalizer.normalizeSpecialElement(this, index, elementList, session);
	}
}
