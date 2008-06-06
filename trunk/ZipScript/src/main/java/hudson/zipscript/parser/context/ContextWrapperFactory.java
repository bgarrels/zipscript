package hudson.zipscript.parser.context;

import java.util.HashMap;
import java.util.Map;

public class ContextWrapperFactory {

	private static ContextWrapperFactory instance;
	public static final ContextWrapperFactory getInstance () {
		if (null == instance)
			instance = new ContextWrapperFactory();
		return instance;
	}

	public ZSContext wrap (Object obj) {
		if (null == obj)
			return new MapContextWrapper(new HashMap(2));
		if (obj instanceof ZSContext)
			return (ZSContext) obj;
		else if (obj instanceof Map)
			return new MapContextWrapper((Map) obj);
		else
			return null;
	}
}
