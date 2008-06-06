package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;

public class TextDefaultElementFactory implements DefaultElementFactory {

	public Element createDefaultElement(String text) {
		TextElement element = new TextElement(text);
		return element;
	}

	public boolean doAppend(char c) {
		return true;
	}
}
