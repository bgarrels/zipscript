package hudson.zipscript.parser.template.element.directive.setdir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;

public class SetPatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(char[] startToken, String s, ParsingSession session)
			throws ParseException {
		return new SetDirective(s, session);
	}

	protected char[] getEndChars() {
		return "/]".toCharArray();
	}

	protected String getDirectiveName() {
		return "set";
	}
}