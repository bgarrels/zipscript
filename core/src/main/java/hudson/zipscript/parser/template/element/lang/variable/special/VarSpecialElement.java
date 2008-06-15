package hudson.zipscript.parser.template.element.lang.variable.special;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.group.GroupElement;
import hudson.zipscript.parser.template.element.lang.IdentifierElement;
import hudson.zipscript.parser.template.element.lang.variable.SpecialVariableElementImpl;
import hudson.zipscript.parser.template.element.lang.variable.VariableTokenSeparatorElement;
import hudson.zipscript.parser.template.element.lang.variable.special.string.HTML;
import hudson.zipscript.parser.template.element.lang.variable.special.string.JS;
import hudson.zipscript.parser.template.element.lang.variable.special.string.LowerCase;
import hudson.zipscript.parser.template.element.lang.variable.special.string.LowerFirst;
import hudson.zipscript.parser.template.element.lang.variable.special.string.RTF;
import hudson.zipscript.parser.template.element.lang.variable.special.string.URL;
import hudson.zipscript.parser.template.element.lang.variable.special.string.UpperCase;
import hudson.zipscript.parser.template.element.lang.variable.special.string.UpperFirst;
import hudson.zipscript.parser.template.element.lang.variable.special.string.XML;
import hudson.zipscript.parser.template.element.special.SpecialElement;

import java.util.List;

public class VarSpecialElement extends IdentifierElement implements VariableTokenSeparatorElement {

	private SpecialMethod executor;
	private String method;
	private GroupElement parameters;

	public ElementIndex normalize(int index, List elementList, ParsingSession session)
			throws ParseException {
		if (elementList.size() >= index) {
			Element e = (Element) elementList.remove(index);
			e.normalize(index, elementList, session);
			if (e instanceof SpecialVariableElementImpl)
				method = ((SpecialVariableElementImpl) e).getTokenValue();
			else if (e instanceof SpecialElement)
				method = ((SpecialElement) e).getTokenValue();
			// see if we've got parameters
			if (elementList.size() > index) {
				if (elementList.get(index) instanceof GroupElement) {
					parameters = (GroupElement) elementList.remove(index);
				}
			}
			return null;
		}
		else {
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Default elements must have a value '" + this + "'");
		}
	}

	public Object execute(Object source, ZSContext context) {
		try {
			if (null == source) return null;
			if (null == executor) {
				executor = initializeSpecialMethod(source, context);
			}
			return executor.execute(source, context);
		}
		catch (Exception e) {
			if (e instanceof ExecutionException) {
				((ExecutionException) e).setElement(this);
				throw (ExecutionException) e;
			}
			else
				throw new ExecutionException(e.getMessage(), this);
		}
	}

	public String toString() {
		return "?";
	}

	public boolean requiresInput(ZSContext context) {
		return true;
	}

	protected SpecialMethod initializeSpecialMethod (
			Object source, ZSContext context) {
		if (source instanceof String) {
			if (method.equals("upperFirst"))
				return UpperFirst.INSTANCE;
			else if (method.equals("lowerFirst"))
				return LowerFirst.INSTANCE;
			else if (method.equals("lowerCase"))
				return LowerCase.INSTANCE;
			else if (method.equals("upperCase"))
				return UpperCase.INSTANCE;
			else if (method.equals("html"))
				return HTML.INSTANCE;
			else if (method.equals("js"))
				return JS.INSTANCE;
			else if (method.equals("rtf"))
				return RTF.INSTANCE;
			else if (method.equals("url"))
				return new URL(context.getParsingSession());
			else if (method.equals("xml"))
				return XML.INSTANCE;
			else return null;
		}
		else return null;
	}
}