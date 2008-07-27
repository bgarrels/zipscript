package hudson.zipscript.parser.template.element.directive.whiledir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class WhileComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new WhilePatternMatcher(),
			new EndWhilePatternMatcher()
		};
	}
}