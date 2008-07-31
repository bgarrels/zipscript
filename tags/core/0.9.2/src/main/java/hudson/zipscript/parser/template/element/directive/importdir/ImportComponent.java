package hudson.zipscript.parser.template.element.directive.importdir;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class ImportComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
			new ImportPatternMatcher()
		};
	}
}