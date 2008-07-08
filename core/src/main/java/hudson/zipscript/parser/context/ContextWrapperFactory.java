package hudson.zipscript.parser.context;

import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.util.ClassUtil;
import hudson.zipscript.parser.util.MathUtil;
import hudson.zipscript.parser.util.UniqueIdGenerator;
import hudson.zipscript.parser.util.UniqueIdGeneratorImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ContextWrapperFactory {

	private static final MathUtil mathUtil = new MathUtil();

	private static ContextWrapperFactory instance;
	public static final ContextWrapperFactory getInstance () {
		if (null == instance)
			instance = new ContextWrapperFactory();
		return instance;
	}

	public ExtendedContext wrap (Object obj, Map params) {
		ExtendedContext context = null;
		if (null == obj)
			context = new MapContextWrapper(new HashMap(2));
		if (obj instanceof ExtendedContext)
			context = (ExtendedContext) obj;
		else if (obj instanceof Context) {
			context = new SimpleContextWrapper((Context) obj);
		}
		else if (obj instanceof Map)
			context = new MapContextWrapper((Map) obj);
		else
			context = new ObjectContextWrapper(obj);
		context.put(Constants.NOW, new Date(), false);
		context.put(Constants.VARS, context, false);
		context.put(Constants.UNIQUE_ID, ClassUtil.loadResource(
				"uniqueIdGenerator", params, UniqueIdGenerator.class,
				UniqueIdGeneratorImpl.class, null), false);
		context.put(Constants.MATH, mathUtil, false);
		return context;
	}
}
