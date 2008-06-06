package hudson.zipscript.parser.template.element.directive.foreachdir;

import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;


public class EndForeachPatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(char[] startToken, String s) {
		return new EndForeachDirective(s);
	}

	protected String getDirectiveName() {
		return "foreach";
	}
}