package hudson.zipscript.parser.template.element.directive.initialize;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class InitializeComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new InitializePatternMatcher(),
			new EndInitializePatternMatcher()
		};
	}
}