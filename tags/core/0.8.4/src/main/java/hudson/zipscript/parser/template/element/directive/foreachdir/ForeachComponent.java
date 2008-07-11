package hudson.zipscript.parser.template.element.directive.foreachdir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class ForeachComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new ForeachPatternMatcher(),
			new EndForeachPatternMatcher()
		};
	}
}