package hudson.zipscript.parser.template.element.directive.ifdir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class IfComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new IfPatternMatcher(),
			new ElseIfPatternMatcher(),
			new ElsePatternMatcher(),
			new EndIfPatternMatcher()
		};
	}
}