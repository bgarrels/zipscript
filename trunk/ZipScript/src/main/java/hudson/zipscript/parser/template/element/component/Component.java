package hudson.zipscript.parser.template.element.component;

import hudson.zipscript.parser.template.element.PatternMatcher;

public interface Component {

	public PatternMatcher[] getPatternMatchers();
}
