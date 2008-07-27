package hudson.zipscript.parser.template.element.directive;

import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;

public abstract class NestableDirective extends NestableElement {

	protected boolean isEndElement(Element e) {
		return false;
	}

	protected boolean isStartElement(Element e) {
		return false;
	}
}