package hudson.zipscript.parser.template.element.component;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.directive.whiledir.EndWhilePatternMatcher;
import hudson.zipscript.parser.template.element.directive.whiledir.WhilePatternMatcher;

public class WhileComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new WhilePatternMatcher(),
			new EndWhilePatternMatcher()
		};
	}
}