package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.template.element.Element;

public class WithPatternMatcher extends WordPatternMatcher {

	protected String getWord() {
		return "with";
	}

	public Element getElement() {
		return WithElement.getInstance();
	}
}