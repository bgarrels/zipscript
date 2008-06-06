package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.exception.ParseException;


public interface DefaultElementFactory {

	public Element createDefaultElement (String text) throws ParseException;

	public boolean doAppend(char c);
}
