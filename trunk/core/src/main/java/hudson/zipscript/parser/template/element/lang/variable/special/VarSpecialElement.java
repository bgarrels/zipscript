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
import hudson.zipscript.parser.template.element.lang.variable.special.map.KeysSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.map.ValuesSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.number.CeilingSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.number.FloorSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.number.RoundSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.sequence.FirstSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.sequence.LastSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.ContainsSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.HTMLSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.JSSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.LPadSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.LowerCaseSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.LowerFirstSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.RPadSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.RTFSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.SplitSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.URLSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.UpperCaseSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.UpperFirstSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.XMLSpecialMethod;
import hudson.zipscript.parser.template.element.special.SpecialElement;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
					parameters.normalize(index, elementList, session);
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
				return UpperFirstSpecialMethod.INSTANCE;
			else if (method.equals("lowerFirst"))
				return LowerFirstSpecialMethod.INSTANCE;
			else if (method.equals("lowerCase"))
				return LowerCaseSpecialMethod.INSTANCE;
			else if (method.equals("upperCase"))
				return UpperCaseSpecialMethod.INSTANCE;
			else if (method.equals("html"))
				return HTMLSpecialMethod.INSTANCE;
			else if (method.equals("js"))
				return JSSpecialMethod.INSTANCE;
			else if (method.equals("rtf"))
				return RTFSpecialMethod.INSTANCE;
			else if (method.equals("url"))
				return new URLSpecialMethod(context.getParsingSession());
			else if (method.equals("xml"))
				return XMLSpecialMethod.INSTANCE;
			else if (method.equals("leftPad"))
				return new LPadSpecialMethod(getParameters());
			else if (method.equals("rightPad"))
				return new RPadSpecialMethod(getParameters());
			else if (method.equals("contains"))
				return new ContainsSpecialMethod(getParameters());
			else if (method.equals("split"))
				return new SplitSpecialMethod(getParameters());
			else return null;
		}
		else if (source instanceof Number) {
			if (method.equals("round"))
				return RoundSpecialMethod.INSTANCE;
			else if (method.equals("ceiling"))
				return CeilingSpecialMethod.INSTANCE;
			else if (method.equals("floor"))
				return FloorSpecialMethod.INSTANCE;
			else
				return null;
		}
		else if (source instanceof Map) {
			if (method.equals("keys"))
				return KeysSpecialMethod.INSTANCE;
			else if (method.equals("values"))
				return ValuesSpecialMethod.INSTANCE;
			else
				return null;
		}
		else if (source instanceof Object[]
		        || source instanceof Collection) {
			if (method.equals("first"))
				return new FirstSpecialMethod();
			else if (method.equals("last"))
				return new LastSpecialMethod();
			else if (method.equals("contains"))
				return new hudson.zipscript.parser.template.element.lang.variable.special.sequence.ContainsSpecialMethod(getParameters());
			else
				return null;
		}
		else return null;
	}

	private Element[] getParameters () {
		if (null == parameters) return null;
		else {
			return (Element[]) parameters.getChildren().toArray(
					new Element[parameters.getChildren().size()]);
		}
	}
}