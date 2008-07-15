package hudson.zipscript.parser.template.element.lang.variable.adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StandardVariableAdapterFactory implements VariableAdapterFactory {

	public MapAdapter getMapAdapter(Object map) {
		if (map instanceof Map)
			return JavaMapAdapter.INSTANCE;
		return null;
	}

	public ObjectAdapter getObjectAdapter(Object object) {
		return new JavaObjectAdapter();
	}

	public SequenceAdapter getSequenceAdapter(Object sequence) {
		if (sequence instanceof Object[])
			return ObjectArrayAdapter.INSTANCE;
		else if (sequence instanceof List)
			return ListAdapter.INSTANCE;
		else if (sequence instanceof Collection)
			return CollectionAdapter.INSTANCE;
		else if (sequence instanceof Iterator)
			return IteratorAdapter.INSTANCE;
		else return null;
	}

}
