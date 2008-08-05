/*
 * Copyright (c) 2008 Joe Hudson.  All rights reserved.
 * License: LGPL <http://www.gnu.org/licenses/lgpl.html>
 */

package hudson.zipscript.parser.template.element.directive.setdir;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NonOutputElement;
import hudson.zipscript.parser.template.element.directive.AbstractDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAware;
import hudson.zipscript.parser.template.element.group.MapElement;
import hudson.zipscript.parser.template.element.lang.AssignmentElement;
import hudson.zipscript.parser.template.element.lang.variable.VariableElement;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;

import java.io.Writer;
import java.util.List;
import java.util.Map;

public class SetDirective extends AbstractDirective implements
		MacroInstanceAware, NonOutputElement {

	private String varName;
	private Element setElement;

	// hash setter properties
	private VariableElement setterParent;

	public SetDirective(String contents, ParsingSession session,
			int contentPosition) throws ParseException {
		parseContents(contents, session, contentPosition);
	}

	private void parseContents(String contents, ParsingSession session,
			int contentPosition) throws ParseException {
		java.util.List elements = parseElements(contents, session,
				contentPosition);
		try {
			if (elements.get(0) instanceof SpecialStringElement) {
				this.varName = ((SpecialStringElement) elements.remove(0))
						.getTokenValue();
			} else {
				throw new ParseException(this,
						"Invalid sequence.  Expecting variable name");
			}
			Element e = (Element) elements.remove(0);
			if (e instanceof AssignmentElement) {
				// standard set
				if (elements.size() > 1 || elements.size() == 0)
					throw new ParseException(this,
							"Invalid sequence.  Improperly formed set expression");
				this.setElement = (Element) elements.get(0);
				// see if we are creating a new Map or Sequence
				if (this.setElement instanceof MapElement) {
					this.setElement = new NewMapElement(
							(MapElement) this.setElement, session, this);
				}
			} else {
				// complex set
				StringBuffer sb = new StringBuffer();
				sb.append(varName);
				while (!(e instanceof AssignmentElement)) {
					sb.append(e);
					if (elements.size() == 0) {
						throw new ParseException(this,
								"Invalid sequence.  Expecting '='");
					}
					e = (Element) elements.remove(0);
				}
				this.varName = null;
				this.setterParent = new VariableElement(false, true, sb
						.toString(), session, contentPosition);

				if (elements.size() > 1 || elements.size() == 0)
					throw new ParseException(this,
							"Invalid sequence.  Improperly formed set expression");
				this.setElement = (Element) elements.get(0);
			}
		} catch (IndexOutOfBoundsException e) {
			throw new ParseException(this,
					"Improperly formed set expression: must have at least 3 tokens");
		}
	}

	public void merge(ExtendedContext context, Writer sw)
			throws ExecutionException {
		Object value = setElement.objectValue(context);
		if (null != setterParent) {
			setterParent.put(value, context);
		} else {
			setInContext(varName, value, true, context);
		}
	}

	protected void setInContext(String key, Object value, boolean travelUp,
			ExtendedContext context) {
		context.put(key, value, travelUp);
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}

	public List getChildren() {
		return null;
	}

	public void getMatchingTemplateDefinedParameters(ExtendedContext context,
			List list, MacroDirective macro, Map additionalContextEntries) {
		additionalContextEntries.put(varName, setElement.objectValue(context));
	}

	public boolean generatesOutput() {
		return false;
	}
}