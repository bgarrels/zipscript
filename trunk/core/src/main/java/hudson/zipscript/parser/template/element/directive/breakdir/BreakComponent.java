package hudson.zipscript.parser.template.element.directive.breakdir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class BreakComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new BreakPatternMatcher()
		};
	}
}