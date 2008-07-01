package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.template.element.Element;

public class MacroInstanceAttribute {

	private String name;
	private Element value;

	public MacroInstanceAttribute (String name, Element value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Element getValue() {
		return value;
	}

	public void setValue(Element value) {
		this.value = value;
	}
}