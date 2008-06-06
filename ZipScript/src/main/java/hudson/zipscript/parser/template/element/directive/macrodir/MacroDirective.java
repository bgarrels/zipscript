package hudson.zipscript.parser.template.element.directive.macrodir;

import java.io.StringWriter;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;

public class MacroDirective extends NestableElement {

	public String contents;

	public MacroDirective (String contents) {
		this.contents = contents;
	}

	protected boolean isStartElement(Element e) {
		return (e instanceof MacroDirective);
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndMacroDirective);
	}

	protected boolean allowSelfNesting() {
		return false;
	}

	public String toString() {
		return "[#macro " + contents + "]";
	}

	public void merge(ZSContext context, StringWriter sw) {
	}
}
