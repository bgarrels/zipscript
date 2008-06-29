package hudson.zipscript.parser.template.data;

import hudson.zipscript.parser.template.element.Element;

public class ElementIndex {

	private Element element;
	private int index;

	public ElementIndex (Element element, int index) {
		this.element = element;
		this.index = index;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}