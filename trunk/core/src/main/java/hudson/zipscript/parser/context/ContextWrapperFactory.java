package hudson.zipscript.parser.context;

import java.util.Date;
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
		ZSContext context = null;
		if (null == obj)
			context = new MapContextWrapper(new HashMap(2));
		if (obj instanceof ZSContext)
			context = (ZSContext) obj;
		else if (obj instanceof Map)
			context = new MapContextWrapper((Map) obj);
		else
			context = new ObjectContextWrapper(obj);
		context.put("now", new Date(), false);
		context.put("vars", context, false);
		return context;
	}
}
