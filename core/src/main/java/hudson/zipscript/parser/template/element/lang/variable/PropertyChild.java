package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;

import org.apache.commons.beanutils.PropertyUtils;

public class PropertyChild implements VariableChild {

	private String name;
	public PropertyChild (String name) {
		this.name = name;
	}

	public Object execute(Object parent, ZSContext context) throws ExecutionException {
		if (null == parent) return null;
		try {
			return PropertyUtils.getProperty(parent, name);
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
