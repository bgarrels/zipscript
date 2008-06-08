package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;


public class MacroPatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(
			char[] startToken, String s, ParsingSession session)
	throws ParseException {
		return new MacroDirective(s, session);
	}

	protected String getDirectiveName() {
		return "macro";
	}
}