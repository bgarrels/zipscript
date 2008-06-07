package hudson.zipscript.parser.template.element.directive.whiledir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;


public class WhilePatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(char[] startToken, String s, ParsingSession parseData) throws ParseException {
		return new WhileDirective(s);
	}

	protected String getDirectiveName() {
		return "while";
	}
}