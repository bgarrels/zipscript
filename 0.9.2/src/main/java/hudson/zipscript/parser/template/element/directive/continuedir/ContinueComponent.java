package hudson.zipscript.parser.template.element.directive.continuedir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class ContinueComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new ContinuePatternMatcher()
		};
	}
}