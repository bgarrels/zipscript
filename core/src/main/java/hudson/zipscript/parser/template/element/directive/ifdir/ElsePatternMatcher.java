package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;


public class ElsePatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(char[] startToken, String s) {
		return new ElseDirective(s);
	}

	protected String getDirectiveName() {
		return "else";
	}

	protected boolean onlyAllowEmpty() {
		return true;
	}
}