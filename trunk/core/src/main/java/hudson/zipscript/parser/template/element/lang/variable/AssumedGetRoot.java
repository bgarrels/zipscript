package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.util.BeanUtil;

import java.lang.reflect.Method;
import java.util.List;

public class AssumedGetRoot implements VariableChild {

	public static final String METHOD_NAME = "get";
	private Element variableElement;
	private String name;
	private List parameters;
	private Method accessorMethod;

	public AssumedGetRoot (String name, List parameters, Element variableElement) {
		this.name = name;
		this.parameters = parameters;
		this.variableElement = variableElement;
	}

	public Object execute(Object parent, ExtendedContext context) throws ExecutionException {
		Object source = context.get(name);
		
		// get the method parameters
		Object[] arr = new Object[parameters.size()];
		for (int i=0; i<parameters.size(); i++) {
			arr[i] = ((Element) parameters.get(i)).objectValue(context);
		}

		if (null == accessorMethod) {
			// initialize
			accessorMethod = BeanUtil.getPropertyMethod(source, METHOD_NAME, arr);
			if (null == accessorMethod) {
				throw new ExecutionException(
						"Unknown method '" + METHOD_NAME + "' on " + variableElement.toString(), null);
			}
		}

		try {
			return accessorMethod.invoke(source, arr);
		}
		catch (Exception e) {
			throw new ExecutionException(e.getMessage(), variableElement, e);
		}
	}

	public String getPropertyName() {
		return null;
	}

	public boolean shouldReturnSomething() {
		return false;
	}

	public String toString () {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append('(');
		for (int i=0; i<parameters.size(); i++) {
			if (i > 0) sb.append(", ");
			sb.append(parameters.get(i));
		}
		sb.append(')');
		return sb.toString();
	}
}