package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;

public class DefaultVariablePatternMatcher implements DefaultElementFactory {

	private static DefaultVariablePatternMatcher INSTANCE = new DefaultVariablePatternMatcher();
	public static DefaultVariablePatternMatcher getInstance () {
		return INSTANCE;
	}

	public Element createDefaultElement(
			String text, ParsingSession session, int contentPosition) throws ParseException {
		return new SpecialVariableElementImpl(false, text, session, contentPosition);
	}

	public boolean doAppend(char c) {
		return true;
	}
}