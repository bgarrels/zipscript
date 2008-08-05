/*
 * Copyright (c) 2008 Joe Hudson.  All rights reserved.
 * License: LGPL <http://www.gnu.org/licenses/lgpl.html>
 */

package hudson.zipscript.parser.template.element.directive.breakdir;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.AbstractDirectivePatternMatcher;

public class BreakPatternMatcher extends AbstractDirectivePatternMatcher {

	protected Element createElement(char[] startToken, String s,
			int contentStartTPosition, ParsingSession session)
			throws ParseException {
		return new BreakDirective(contentStartTPosition);
	}

	protected String getDirectiveName() {
		return "break";
	}

	protected boolean allowEmpty() {
		return true;
	}

	protected boolean onlyAllowEmpty() {
		return true;
	}

	protected char[] getEndChars() {
		return "/]".toCharArray();
	}
}