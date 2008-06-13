package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.util.BeanUtil;

import java.lang.reflect.Method;
import java.util.List;

public class MethodChild implements VariableChild {

	private Element variableElement;
	private String methodName;
	private List parameters;
	private Method accessorMethod;

	public MethodChild (String name, List parameters, Element variableElement) {
		this.methodName = name;
		this.parameters = parameters;
		this.variableElement = variableElement;
	}

	public Object execute(Object parent, ZSContext context) throws ExecutionException {
		if (null == parent) return null;
		
		// get the method parameters
		Object[] arr = new Object[parameters.size()];
		for (int i=0; i<parameters.size(); i++) {
			arr[i] = ((Element) parameters.get(i)).objectValue(context);
		}

		if (null == accessorMethod) {
			// initialize
			accessorMethod = BeanUtil.getPropertyMethod(parent, methodName, arr);
			if (null == accessorMethod) {
				throw new ExecutionException(
						"Unknown method '" + methodName + "' on " + variableElement.toString(), null);
			}
		}

		try {
			return accessorMethod.invoke(parent, arr);
		}
		catch (Exception e) {
			throw new ExecutionException(e.getMessage(), variableElement, e);
		}
	}

	public String getPropertyName() {
		return null;
	}

	public String toString () {
		StringBuffer sb = new StringBuffer();
		sb.append(methodName);
		sb.append('(');
		for (int i=0; i<parameters.size(); i++) {
			if (i > 0) sb.append(", ");
			sb.append(parameters.get(i));
		}
		sb.append(')');
		return sb.toString();
	}
}