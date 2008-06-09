package hudson.zipscript.parser.template.element.lang.variable;

import java.util.Map;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;

import org.apache.commons.beanutils.PropertyUtils;

public class PropertyChild implements VariableChild {

	private static final short TYPE_MAP = 1;
	private static final short TYPE_CONTEXT = 2;
	private static final short TYPE_OBJECT = 3;

	private Short type;
	private String name;
	public PropertyChild (String name) {
		this.name = name;
	}

	public Object execute(Object parent, ZSContext context) throws ExecutionException {
		if (null == parent) return null;
		try {
			if (null == type) {
				if (parent instanceof Map) {
					type = new Short(TYPE_MAP);
				}
				else if (parent instanceof ZSContext) {
					type = new Short(TYPE_CONTEXT);
				}
				else {
					type = new Short(TYPE_OBJECT);
				}
			}
			if (type.shortValue() == TYPE_MAP) {
				return ((Map) parent).get(name);
			}
			else if (type.shortValue() == TYPE_CONTEXT) {
				return ((ZSContext) parent).get(name);
			}
			else {
				return PropertyUtils.getProperty(parent, name);
			}
		}
		catch (Exception e) {
			throw new ExecutionException(e);
		}
	}

	public String getPropertyName() {
		return name;
	}

	public String toString() {
		return name;
	}
}
