/*
 * Copyright (c) 2008 Joe Hudson.  All rights reserved.
 * License: LGPL <http://www.gnu.org/licenses/lgpl.html>
 */

package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;

public interface DefaultElementFactory {

	public Element createDefaultElement(String text, ParsingSession session,
			int contentPosition) throws ParseException;

	public boolean doAppend(char c);
}
