package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class DynamicChild implements VariableChild {

	private Element evaluator;
	private Map pathChildren = new HashMap();

	public DynamicChild (Element evaluator) {
		this.evaluator = evaluator;
	}

	public Object execute(Object parent, ExtendedContext context)
	throws ExecutionException {
		if (null == parent) return null;
		String path = evaluator.objectValue(context).toString();
		List children = (List) pathChildren.get(path);
		if (null == children) {
			// initialize
			children = initialize(path);
			pathChildren.put(path, children);
		}
		for (Iterator i=children.iterator(); i.hasNext(); ) {
			if (null == parent) return null;
			parent = ((VariableChild) i.next()).execute(parent, context);
		}
		return parent;
	}

	protected List initialize (String path) {
		List children = new ArrayList();
		StringTokenizer st = new StringTokenizer(path, ".");
		while (st.hasMoreElements()) {
			children.add(new PropertyChild(st.nextToken(), evaluator));
		}
		return children;
	}

	public boolean shouldReturnSomething() {
		return true;
	}

	public String getPropertyName() {
		return evaluator.toString();
	}
}