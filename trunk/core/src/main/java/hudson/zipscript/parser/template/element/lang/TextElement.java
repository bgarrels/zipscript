package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.template.element.Element;

import java.io.StringWriter;
import java.util.List;


public class TextElement extends AbstractElement implements Element {

	private String text;

	public TextElement (String text) {
		this.text = text;
	}

	public Object objectValue(ZSContext context) {
		return text;
	}

	public boolean booleanValue(ZSContext context) {
		return false;
	}

	public void merge(ZSContext context, StringWriter sw) {
		if (null != text)
			sw.write(text);
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return "'" + text + "'";
	}

	public ElementIndex normalize(
			int index, List elementList, ParsingSession session) throws ParseException {
		return null;
	}

	public String getText() {
		return text;
	}

	public List getChildren() {
		return null;
	}
}