package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;


public class EndMacroPatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(char[] startToken, String s, ParsingSession parseData) {
		return new EndMacroDirective(s);
	}

	protected String getDirectiveName() {
		return "macro";
	}
}