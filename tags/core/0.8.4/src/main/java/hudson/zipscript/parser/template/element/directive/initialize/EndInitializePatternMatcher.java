package hudson.zipscript.parser.template.element.directive.initialize;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;


public class EndInitializePatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(
			char[] startToken, String s, int contentIndex, ParsingSession parseData) {
		return new EndInitializeDirective(s);
	}

	protected String getDirectiveName() {
		return "initialize";
	}
}