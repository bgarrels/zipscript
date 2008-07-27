package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.template.element.lang.IdentifierElement;

public class RequiredIdentifierElement extends IdentifierElement implements SpecialElement {

	private static final RequiredIdentifierElement instance = new RequiredIdentifierElement();
	public static final RequiredIdentifierElement getInstance () {
		return instance;
	}

	private RequiredIdentifierElement () {}

	public String getTokenValue() {
		return "*";
	}

}
