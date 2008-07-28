package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.context.ExtendedContext;

public class RootChild implements VariableChild {

	private short TYPE_STANDARD = 1;
	private short TYPE_GET_GLOBAL = 2;
	private short TYPE_GET_RESOURCES = 3;
	private short TYPE_GET_UNIQUEID = 4;

	public String name;
	private short type = TYPE_STANDARD;

	public RootChild (String name) {
		this.name = name;
		if (name.equals(Constants.GLOBAL))
			type = TYPE_GET_GLOBAL;
		else if (name.equals(Constants.RESOURCE))
			type = TYPE_GET_RESOURCES;
		else if (name.equals(Constants.UNIQUE_ID))
			type = TYPE_GET_UNIQUEID;
	}

	public Object execute(Object parent, ExtendedContext context) {
		if (type == TYPE_GET_GLOBAL)
			return context.getRootContext().get(Constants.GLOBAL);
		else if (type == TYPE_GET_RESOURCES)
			return context.getRootContext().get(Constants.RESOURCE);
		else if (type == TYPE_GET_UNIQUEID)
			return context.getRootContext().get(Constants.UNIQUE_ID);
		else
			return context.get(name);
	}

	public String getPropertyName() {
		return name;
	}

	public boolean shouldReturnSomething() {
		return true;
	}

	public String toString() {
		return name;
	}
}