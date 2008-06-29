package hudson.zipscript.parser.template.element.comment;

import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;

public class CommentComponent implements Component {

	public PatternMatcher[] getPatternMatchers() {
		return new PatternMatcher[] {
				new CommentPatternMatcher()
		};
	}
}
