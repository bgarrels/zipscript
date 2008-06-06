package hudson.zipscript.parser.template.element.component;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.directive.ifdir.ElseIfPatternMatcher;
import hudson.zipscript.parser.template.element.directive.ifdir.ElsePatternMatcher;
import hudson.zipscript.parser.template.element.directive.ifdir.EndIfPatternMatcher;
import hudson.zipscript.parser.template.element.directive.ifdir.IfPatternMatcher;

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