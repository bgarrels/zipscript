package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;


public class ElseIfPatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(char[] startToken, String s, ParsingSession parseData) {
		return new ElseIfDirective(s);
	}

	protected String getDirectiveName() {
		return "elseif";
	}
}