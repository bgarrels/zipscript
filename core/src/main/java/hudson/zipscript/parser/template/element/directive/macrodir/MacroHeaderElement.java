package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;
import hudson.zipscript.parser.template.element.lang.TextDefaultElementFactory;
import hudson.zipscript.parser.template.element.lang.TextElement;
import hudson.zipscript.parser.template.element.lang.WhitespaceElement;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class MacroHeaderElement extends AbstractElement implements ToStringWithContextElement {

	private List children;
	private String contents;
	private int position;

	public MacroHeaderElement(
			String contents, ParsingSession session, int position) throws ParseException {
		this.contents = contents;
		this.position = position;
	}

	public List getChildren() {
		return children;
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		if (null != children) {
			for (Iterator i=children.iterator(); i.hasNext(); ) {
				((Element) i.next()).merge(context, sw);
			}
		}
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		// set element in macro
		((MacroInstanceDirective) session.getNestingStack().get(session.getNestingStack().size()-1)).setHeader(this);
		return new ElementIndex(null, -1);
	}

	public void validate(ParsingSession session) throws ParseException {
		if (null != contents) {
			ParsingResult result = ExpressionParser.getInstance().parse(
					contents, ZipEngine.TEMPLATE_COMPONENTS, TextDefaultElementFactory.INSTANCE,
					position, session);
			children = result.getElements();
			// trim
			if (children.size() > 0) {
				for (int i=0; i<children.size(); ) {
					if (children.get(i) instanceof WhitespaceElement)
						children.remove(0);
					else
						break;
				}
				for (int i=children.size()-1; i>=0; i--) {
					if (children.get(i) instanceof WhitespaceElement)
						children.remove(0);
					else
						break;
				}
				Element e = (Element) children.get(0);
				if (e instanceof TextElement) {
					String text = ((TextElement) e).getText();
					StringBuffer sb = new StringBuffer();
					for (int i=0; i<text.length(); i++) {
						if (!Character.isWhitespace(text.charAt(i))) {
							text = text.substring(i);
							((TextElement) e).setText(text);
							break;
						}
					}
				}
				e = (Element) children.get(children.size()-1);
				if (e instanceof TextElement) {
					String text = ((TextElement) e).getText();
					StringBuffer sb = new StringBuffer();
					for (int i=text.length()-1; i>=0; i--) {
						if (!Character.isWhitespace(text.charAt(i))) {
							text = text.substring(0, i+1);
							((TextElement) e).setText(text);
							break;
						}
					}
				}
			}
			this.contents = null;
		}
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		return null;
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		return false;
	}

	public String toString(ZSContext context) {
		StringWriter sw = new StringWriter();
		merge(context, sw);
		return sw.toString();
	}
}