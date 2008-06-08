package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.template.element.Element;

public class MacroAttribute {

	private String name;
	private Element defaultValue;
	private boolean isRequired;

	public MacroAttribute (String name, Element defaultValue, boolean isRequired) {
		this.name = name;
		this.defaultValue = defaultValue;
		this.isRequired = isRequired;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Element getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Element defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
}