package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.util.SpecialElementNormalizer;

import java.io.StringWriter;
import java.util.List;

public class BooleanElement extends AbstractElement implements SpecialElement {

	private Boolean value;

	public BooleanElement (boolean value) {
		this.value = new Boolean(value);
	}

	public String getTokenValue() {
		if (value.booleanValue())
			return "true";
		else
			return "false";
	}

	public ElementIndex normalize(int index, List elementList, ParseParameters parameters) throws ParseException {
		return SpecialElementNormalizer.normalizeSpecialElement(
				this, index, elementList, parameters);
	}

	public Object objectValue(ZSContext context) {
		return value;
	}

	public void merge(ZSContext context, StringWriter sw) {
		sw.append(value.toString());
	}

	public boolean booleanValue(ZSContext context) {
		return value.booleanValue();
	}
}
