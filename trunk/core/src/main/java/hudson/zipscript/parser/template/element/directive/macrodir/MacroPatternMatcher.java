package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;


public class MacroPatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(char[] startToken, String s) {
		return new MacroDirective(s);
	}

	protected String getDirectiveName() {
		return "macro";
	}
}