package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;

public class SpecialVariableDefaultEelementFactory implements DefaultElementFactory {

	private static final SpecialVariableDefaultEelementFactory INSTANCE = new SpecialVariableDefaultEelementFactory();
	public static final SpecialVariableDefaultEelementFactory getInstance () {
		return INSTANCE;
	}

	public Element createDefaultElement(
			String text, ParsingSession session, int contentPosition) throws ParseException {
		SpecialVariableElementImpl element = new SpecialVariableElementImpl(
				text, session, contentPosition);
		element.setShouldEvaluateSeparators(false);
		element.setElementPosition(contentPosition);
		element.setElementLength(text.length());
		return element;
	}

	public boolean doAppend(char c) {
		return true;
	}
}
