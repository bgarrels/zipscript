package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;


public class EndMacroPatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(char[] startToken, String s) {
		return new EndMacroDirective(s);
	}

	protected String getDirectiveName() {
		return "macro";
	}
}