package hudson.zipscript.parser.template.data;

import hudson.zipscript.parser.template.element.Element;

public class ElementParsingSession {

	public Element element;
	public ParsingSession parsingSession;

	public ElementParsingSession (Element element, ParsingSession parsingSession) {
		this.element = element;
		this.parsingSession = parsingSession;
	}
}
