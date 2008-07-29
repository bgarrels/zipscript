package hudson.zipscript.parser.template.element.lang.variable.special;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.group.GroupElement;
import hudson.zipscript.parser.template.element.lang.IdentifierElement;
import hudson.zipscript.parser.template.element.lang.variable.SpecialVariableElementImpl;
import hudson.zipscript.parser.template.element.lang.variable.VariableTokenSeparatorElement;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;
import hudson.zipscript.parser.template.element.special.SpecialElement;

import java.util.List;

public class VarSpecialElement extends IdentifierElement implements VariableTokenSeparatorElement {

	private SpecialMethod executor;
	private String method;
	private Element[] parameters;
	
	public VarSpecialElement () {}

	public VarSpecialElement (SpecialMethod executor) {
		this.executor = executor;
	}

	public ElementIndex normalize(int index, List elementList, ParsingSession session)
			throws ParseException {
		if (elementList.size() > index) {
			Element e = (Element) elementList.remove(index);
			e.normalize(index, elementList, session);
			if (e instanceof SpecialVariableElementImpl)
				method = ((SpecialVariableElementImpl) e).getTokenValue();
			else if (e instanceof SpecialElement)
				method = ((SpecialElement) e).getTokenValue();
			// see if we've got parameters
			if (elementList.size() > index) {
				if (elementList.get(index) instanceof GroupElement) {
					GroupElement parameters = (GroupElement) elementList.remove(index);
					parameters.normalize(index, elementList, session);
					this.parameters = (Element[]) parameters.getChildren().toArray(
							new Element[parameters.getChildren().size()]);
				}
			}
			return null;
		}
		else {
			throw new ParseException(this, "Default element detected with no value");
		}
	}

	public Object execute(Object source, RetrievalContext retrievalContext, ExtendedContext context) {
		try {
			if (null == source) return null;
			if (null == executor) executor = initializeSpecialMethod(source, context);
			if (null == executor) throw new ExecutionException("Unknown special method '" + method + "'", null);
			return executor.execute(source, retrievalContext, context);
		}
		catch (Exception e) {
			if (e instanceof ExecutionException) {
				((ExecutionException) e).setElement(this);
				throw (ExecutionException) e;
			}
			else
				throw new ExecutionException(e.getMessage(), this, e);
		}
	}

	public String toString() {
		return "?" + method;
	}

	public boolean requiresInput(ExtendedContext context) {
		return true;
	}

	protected SpecialMethod initializeSpecialMethod (
			Object source, ExtendedContext context) {
		return context.getResourceContainer().getVariableAdapterFactory().getSpecialMethod(
				method, parameters, source, context, this);
	}

	public RetrievalContext getExpectedType() {
		return RetrievalContext.HASH;
	}
}