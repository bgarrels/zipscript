package hudson.zipscript.parser.template.element.directive.foreachdir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;


public class ForeachPatternMatcher extends AbstractDirectivePatternMatcher {

	private String varName;
	private VariableElement listValue;

	protected Element createElement(char[] startToken, String s) throws ParseException {
		return new ForeachDirective(s);
	}

	protected String getDirectiveName() {
		return "foreach";
	}
}