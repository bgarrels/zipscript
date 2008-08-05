/*
 * Copyright (c) 2008 Joe Hudson.  All rights reserved.
 * License: LGPL <http://www.gnu.org/licenses/lgpl.html>
 */

package hudson.zipscript.parser.template.element.special;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.variable.SpecialVariableElementImpl;

public class DefaultVariablePatternMatcher implements DefaultElementFactory {

	private static DefaultVariablePatternMatcher INSTANCE = new DefaultVariablePatternMatcher();

	public static DefaultVariablePatternMatcher getInstance() {
		return INSTANCE;
	}

	public Element createDefaultElement(String text, ParsingSession session,
			int contentPosition) throws ParseException {
		return new SpecialVariableElementImpl(text, session, contentPosition);
	}

	public boolean doAppend(char c) {
		return true;
	}
}