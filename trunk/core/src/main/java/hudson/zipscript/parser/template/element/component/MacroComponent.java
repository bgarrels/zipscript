package hudson.zipscript.parser.template.element.component;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.directive.macrodir.EndMacroPatternMatcher;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroPatternMatcher;

public class MacroComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new MacroPatternMatcher(),
			new EndMacroPatternMatcher()
		};
	}
}