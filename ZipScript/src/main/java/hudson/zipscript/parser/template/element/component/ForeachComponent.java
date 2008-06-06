package hudson.zipscript.parser.template.element.component;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.directive.foreachdir.EndForeachPatternMatcher;
import hudson.zipscript.parser.template.element.directive.foreachdir.ForeachPatternMatcher;

public class ForeachComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new ForeachPatternMatcher(),
			new EndForeachPatternMatcher()
		};
	}
}