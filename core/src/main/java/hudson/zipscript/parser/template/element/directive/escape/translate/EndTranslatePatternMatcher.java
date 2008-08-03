package hudson.zipscript.parser.template.element.directive.escape.translate;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;


public class EndTranslatePatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(
			char[] startToken, String s, int contentIndex, ParsingSession parseData) {
		return new EndTranslateDirective(s);
	}

	protected String getDirectiveName() {
		return "translate";
	}
}