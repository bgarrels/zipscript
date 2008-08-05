/*
 * Copyright (c) 2008 Joe Hudson.  All rights reserved.
 * License: LGPL <http://www.gnu.org/licenses/lgpl.html>
 */

package hudson.zipscript.parser.template.element.directive.whiledir;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractEndPatternMatcher;

public class EndWhilePatternMatcher extends AbstractEndPatternMatcher {

	protected Element createElement(char[] startToken, String s,
			int contentIndex, ParsingSession parseData) {
		return new EndWhileDirective(s);
	}

	protected String getDirectiveName() {
		return "while";
	}
}