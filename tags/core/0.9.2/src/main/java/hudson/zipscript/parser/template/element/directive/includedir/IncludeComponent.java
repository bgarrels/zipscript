package hudson.zipscript.parser.template.element.directive.includedir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class IncludeComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new IncludePatternMatcher()
		};
	}
}