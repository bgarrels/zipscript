/*
 * Copyright (c) 2008 Joe Hudson.  All rights reserved.
 * License: LGPL <http://www.gnu.org/licenses/lgpl.html>
 */

package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

public class TextElementRootChild implements VariableChild {

	private String text;
	private RetrievalContext retrievalContext;
	private String contextHint;

	public TextElementRootChild(String text) {
		this.text = text;
	}

	public Object execute(Object parent, ExtendedContext context) {
		return text;
	}

	public boolean shouldReturnSomething() {
		return true;
	}

	public String toString() {
		return text;
	}

	public String getPropertyName() {
		return text;
	}

	public RetrievalContext getRetrievalContext() {
		return retrievalContext;
	}

	public void setRetrievalContext(RetrievalContext retrievalContext) {
		this.retrievalContext = retrievalContext;
	}

	public String getContextHint() {
		return contextHint;
	}

	public void setContextHint(String contextHint) {
		this.contextHint = contextHint;
	}
}