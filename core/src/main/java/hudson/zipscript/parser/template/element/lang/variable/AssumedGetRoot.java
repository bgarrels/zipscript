package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.util.BeanUtil;

import java.lang.reflect.Method;
import java.util.List;

public class AssumedGetRoot implements VariableChild {

	public static final String METHOD_NAME = "get";

	private short TYPE_STANDARD = 1;
	private short TYPE_GET_GLOBAL = 2;
	private short TYPE_GET_RESOURCES = 3;
	private short TYPE_GET_UNIQUEID = 4;

	private Element variableElement;
	private String name;
	private List parameters;
	private Method accessorMethod;
	private short type = TYPE_STANDARD;

	public AssumedGetRoot (String name, List parameters, Element variableElement) {
		this.name = name;
		this.parameters = parameters;
		this.variableElement = variableElement;
		if (name.equals(Constants.GLOBAL))
			type = TYPE_GET_GLOBAL;
		else if (name.equals(Constants.RESOURCE))
			type = TYPE_GET_RESOURCES;
		else if (name.equals(Constants.UNIQUE_ID))
			type = TYPE_GET_UNIQUEID;
	}

	public Object execute(Object parent, ExtendedContext context) throws ExecutionException {
		Object source = null;
		if (type == TYPE_STANDARD)
			context.get(name);
		else if (type == TYPE_GET_GLOBAL)
			source = context.getRootContext().get(Constants.GLOBAL);
		else if (type == TYPE_GET_RESOURCES)
			source = context.getRootContext().get(Constants.RESOURCE);
		else if (type == TYPE_GET_UNIQUEID)
			source = context.getRootContext().get(Constants.UNIQUE_ID);
		
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