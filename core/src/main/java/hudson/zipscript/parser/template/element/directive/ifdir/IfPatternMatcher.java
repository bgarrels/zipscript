package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;


public class IfPatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(
			char[] startToken, String s, int contentIndex, ParsingSession parseData) throws ParseException {
		return new IfDirective(s, contentIndex);
	}

	protected String getDirectiveName() {
		return "if";
	}
}