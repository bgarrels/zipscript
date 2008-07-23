package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class MacroComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new MacroPatternMatcher(),
			new EndMacroPatternMatcher(),
			new MacroInstancePatternMatcher(),
			new EndMacroInstancePatternMatcher(),
			new TemplateDefinedParameterPatternMatcher(),
			new EndTemplateDefinedParameterPatternMatcher()
			// I think this might be too advanced and confuse users
			// new MacroInstanceHeaderFooterPatternMatcher()
		};
	}
}