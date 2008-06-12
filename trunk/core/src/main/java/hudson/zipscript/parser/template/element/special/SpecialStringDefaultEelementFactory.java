package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;

public class SpecialStringDefaultEelementFactory implements DefaultElementFactory {

	private static final SpecialStringDefaultEelementFactory INSTANCE = new SpecialStringDefaultEelementFactory();
	public static final SpecialStringDefaultEelementFactory getInstance () {
		return INSTANCE;
	}

	public Element createDefaultElement(
			String text, ParsingSession session, int contentPosition) {
		SpecialStringElementImpl element = new SpecialStringElementImpl(text);
		return element;
	}

	public boolean doAppend(char c) {
		return true;
	}
}
