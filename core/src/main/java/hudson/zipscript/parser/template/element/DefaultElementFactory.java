package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;


public interface DefaultElementFactory {

	public Element createDefaultElement (
			String text, ParsingSession session, int contentPosition) throws ParseException;

	public boolean doAppend(char c);
}
