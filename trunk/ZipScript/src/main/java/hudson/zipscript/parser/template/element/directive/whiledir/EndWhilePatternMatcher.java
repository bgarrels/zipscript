package hudson.zipscript.parser.template.element.directive.whiledir;

import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;


public class EndWhilePatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(char[] startToken, String s) {
		return new EndWhileDirective(s);
	}

	protected String getDirectiveName() {
		return "while";
	}
}