package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class VariableComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
				new VariablePatternMatcher()
		};
	}
}
