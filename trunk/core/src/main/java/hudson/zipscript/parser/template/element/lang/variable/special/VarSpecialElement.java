package hudson.zipscript.parser.template.element.lang.variable.special;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceExecutor;
import hudson.zipscript.parser.template.element.group.GroupElement;
import hudson.zipscript.parser.template.element.lang.IdentifierElement;
import hudson.zipscript.parser.template.element.lang.variable.SpecialVariableElementImpl;
import hudson.zipscript.parser.template.element.lang.variable.VariableTokenSeparatorElement;
import hudson.zipscript.parser.template.element.lang.variable.special.date.JSDateSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.date.JSDateTimeSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.macroinstance.BooleanValueSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.macroinstance.ObjectValueSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.map.KeysSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.map.ValuesSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.number.CeilingSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.number.FloorSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.number.RoundSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.IsBooleanSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.IsDateSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.IsMapSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.IsNumberSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.IsSequenceSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.IsStringSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.StringSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.object.VoidSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.sequence.FirstSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.sequence.LastSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.sequence.LengthSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.ContainsSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.HTMLSpecialMethod;
import hudson.zipscript.parser.template.element.lang.variable.special.string.HumpbackCaseSpecialMethod;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VarSpecialElement extends IdentifierElement implements VariableTokenSeparatorElement {

	private SpecialMethod executor;
	private String method;
	private GroupElement parameters;

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
					parameters = (GroupElement) elementList.remove(index);
					parameters.normalize(index, elementList, session);
				}
			}
			return null;
		}
		else {
			throw new ParseException(this, "Default element detected with no value");
		}
	}

	public Object execute(Object source, ExtendedContext context) {
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

	public boolean requiresInput(ExtendedContext context) {
		return true;
	}

	protected SpecialMethod initializeSpecialMethod (
			Object source, ExtendedContext context) {
		// object methods
		if (method.equals("void"))
			return VoidSpecialMethod.INSTANCE;
		else if (method.equals("isDate"))
			return IsDateSpecialMethod.INSTANCE;
		else if (method.equals("isNumber"))
			return IsNumberSpecialMethod.INSTANCE;
		else if (method.equals("isString"))
			return IsStringSpecialMethod.INSTANCE;
		else if (method.equals("isBoolean"))
			return IsBooleanSpecialMethod.INSTANCE;
		else if (method.equals("isSequence"))
			return IsSequenceSpecialMethod.INSTANCE;
		else if (method.equals("isMap"))
			return IsMapSpecialMethod.INSTANCE;
		else if (source instanceof MacroInstanceExecutor) {
			if (method.equals("objectValue"))
				return new ObjectValueSpecialMethod(this);
			else if (method.equals("booleanValue"))
				return new BooleanValueSpecialMethod();
			else return null;
		}

		// string methods - these are a special case as we will turn objects into strings
		if (method.equals("upperFirst"))
			return getStringSpecialMethod(source, UpperFirstSpecialMethod.INSTANCE);
		else if (method.equals("lowerFirst"))
			return getStringSpecialMethod(source, LowerFirstSpecialMethod.INSTANCE);
		else if (method.equals("lowerCase"))
			return getStringSpecialMethod(source, LowerCaseSpecialMethod.INSTANCE);
		else if (method.equals("humpbackCase"))
			return getStringSpecialMethod(source, HumpbackCaseSpecialMethod.INSTANCE);
		else if (method.equals("upperCase"))
			return getStringSpecialMethod(source, UpperCaseSpecialMethod.INSTANCE);
		else if (method.equals("html"))
			return getStringSpecialMethod(source, HTMLSpecialMethod.INSTANCE);
		else if (method.equals("js"))
			return getStringSpecialMethod(source, JSSpecialMethod.INSTANCE);
		else if (method.equals("rtf"))
			return getStringSpecialMethod(source, RTFSpecialMethod.INSTANCE);
		else if (method.equals("url"))
			return getStringSpecialMethod(source, new URLSpecialMethod(context.getParsingSession()));
		else if (method.equals("xml"))
			return getStringSpecialMethod(source, XMLSpecialMethod.INSTANCE);
		else if (method.equals("leftPad"))
			return getStringSpecialMethod(source, new LPadSpecialMethod(getParameters()));
		else if (method.equals("rightPad"))
			return getStringSpecialMethod(source, new RPadSpecialMethod(getParameters()));
		else if (method.equals("contains"))
			return getStringSpecialMethod(source, new ContainsSpecialMethod(getParameters()));
		else if (method.equals("split"))
			return getStringSpecialMethod(source, new SplitSpecialMethod(getParameters()));

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
		else if (source instanceof Date) {
			if (method.equals("jsDate"))
				return JSDateSpecialMethod.INSTANCE;
			if (method.equals("jsDateTime"))
				return JSDateTimeSpecialMethod.INSTANCE;
			else return null;
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
			if (method.equals("length"))
				return new LengthSpecialMethod();
			else if (method.equals("first"))
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

	private SpecialMethod getStringSpecialMethod (Object source, SpecialMethod specialMethod) {
		if (source instanceof String) return specialMethod;
		else return new StringSpecialMethod(specialMethod);
	}

	private Element[] getParameters () {
		if (null == parameters) return null;
		else {
			return (Element[]) parameters.getChildren().toArray(
					new Element[parameters.getChildren().size()]);
		}
	}
}