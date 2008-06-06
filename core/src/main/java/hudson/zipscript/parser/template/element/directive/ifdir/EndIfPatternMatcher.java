package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;


public class EndIfPatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(char[] startToken, String s) {
		return new EndIfDirective(s);
	}

	protected String getDirectiveName() {
		return "if";
	}
}