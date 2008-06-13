package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.util.BeanUtil;

import java.lang.reflect.Method;
import java.util.Map;

public class PropertyChild implements VariableChild {

	private static final short TYPE_MAP = 1;
	private static final short TYPE_CONTEXT = 2;
	private static final short TYPE_OBJECT = 3;

	private Element variableElement;
	private Short type;
	private String name;
	private Method accessorMethod;

	public PropertyChild (String name, Element variableElement) {
		this.name = name;
		this.variableElement = variableElement;
	}

	public Object execute(Object parent, ZSContext context) throws ExecutionException {
		if (null == parent) return null;
		try {
			if (null == type) {
				// initialize type
				if (parent instanceof Map) {
					type = new Short(TYPE_MAP);
				}
				else if (parent instanceof ZSContext) {
					type = new Short(TYPE_CONTEXT);
				}
				else {
					type = new Short(TYPE_OBJECT);
					accessorMethod = BeanUtil.getPropertyMethod(
							parent, name, null);
					if (null == accessorMethod) {
						throw new ExecutionException(
								"Unknown property '" + name + "' on " + variableElement.toString(), null);
					}
				}
			}

			if (type.shortValue() == TYPE_MAP) {
				return ((Map) parent).get(name);
			}
			else if (type.shortValue() == TYPE_CONTEXT) {
				return ((ZSContext) parent).get(name);
			}
			else {
				return accessorMethod.invoke(parent, null);
			}
		}
		catch (Exception e) {
			throw new ExecutionException(e.getMessage(), variableElement, e);
		}
	}

	public String getPropertyName() {
		return name;
	}

	public String toString() {
		return name;
	}
}
