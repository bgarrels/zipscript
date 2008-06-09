package hudson.zipscript.parser.template.element.lang.variable;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MapChild implements VariableChild {

	private static final int TYPE_MAP = 1;
	private static final int TYPE_ARRAY = 2;
	private static final int TYPE_LIST = 3;
	private static final int TYPE_COLLECTION = 4;

	private Element keyElement;
	private int type = Integer.MIN_VALUE;

	public MapChild (Element keyElement) {
		this.keyElement = keyElement;
	}

	public Object execute(Object parent, ZSContext context) throws ExecutionException {
		if (null == parent) return null;
		if (type == Integer.MIN_VALUE) {
			if (parent instanceof Map)
				type = TYPE_MAP;
			else if (parent instanceof Object[])
				type = TYPE_ARRAY;
			else if (parent instanceof List)
				type = TYPE_LIST;
			else if (parent instanceof Collection)
				type = TYPE_COLLECTION;
			else
				throw new ExecutionException("Unmappable type");
		}
		Object key = keyElement.objectValue(context);
		if (type == TYPE_MAP) {
			return ((Map) parent).get(key);
		}
		int index = 0;
		if (!(key instanceof Number))
			throw new ExecutionException("Invalid type key");
		else
			index = ((Number) key).intValue();
		if (type == TYPE_ARRAY) {
			return ((Object[]) parent)[index];
		}
		else if (type == TYPE_LIST) {
			return ((List) parent).get(index);
		}
		else if (type == TYPE_COLLECTION) {
			return ((Collection) parent).toArray()[index];
		}
		else
			return null;
	}

	public String getPropertyName() {
		return null;
	}

	public String toString () {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		sb.append(keyElement.toString());
		sb.append(']');
		return sb.toString();
	}
}