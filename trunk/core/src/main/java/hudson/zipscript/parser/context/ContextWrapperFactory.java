package hudson.zipscript.parser.context;

import hudson.zipscript.ResourceContainer;
import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.util.ClassUtil;
import hudson.zipscript.parser.util.I18NResource;
import hudson.zipscript.parser.util.I18NResourceImpl;
import hudson.zipscript.parser.util.MathUtil;
import hudson.zipscript.parser.util.UniqueIdGenerator;
import hudson.zipscript.parser.util.UniqueIdGeneratorImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class ContextWrapperFactory {

	private static final MathUtil mathUtil = new MathUtil();

	private static ContextWrapperFactory instance;
	public static final ContextWrapperFactory getInstance () {
		if (null == instance)
			instance = new ContextWrapperFactory();
		return instance;
	}

	public ExtendedContext wrap (
			Object obj, ParsingSession parsingSession, ResourceContainer resourceContainer) {
		ExtendedContext context = null;

		// wrap the context if necessary
		if (null == obj)
			context = new MapContextWrapper(new HashMap(2));
		if (obj instanceof ExtendedContext)
			context = (ExtendedContext) obj;
		else if (obj instanceof Context) {
			context = new SimpleContextWrapper((Context) obj);
		}
		// add plugin wrapping here
		if (null != resourceContainer.getPlugins()) {
			Context ctx = null;
			for (int i=0; i<resourceContainer.getPlugins().length; i++) {
				ctx = resourceContainer.getPlugins()[i].wrapContextObject(obj);
				if (null != ctx) {
					if (ctx instanceof ExtendedContext)
						context = (ExtendedContext) ctx;
					else
						context = new SimpleContextWrapper(ctx);
				}
			}
		}
		if (null == context) {
			if (obj instanceof Map)
				context = new MapContextWrapper((Map) obj);
			else
				context = new ObjectContextWrapper(obj);
		}

		// initialize the context
		context.put(Constants.NOW, new Date(), false);
		context.put(Constants.VARS, context, false);
		context.put(Constants.GLOBAL, context, false);
		context.put(Constants.UNIQUE_ID, ClassUtil.loadResource(
				"uniqueIdGenerator", resourceContainer.getInitParameters(), UniqueIdGenerator.class,
				UniqueIdGeneratorImpl.class, null), false);
		context.put(Constants.RESOURCE, ClassUtil.loadResource(
				"i18n", resourceContainer.getInitParameters(), I18NResource.class,
				I18NResourceImpl.class, null), false);
		context.put(Constants.MATH, mathUtil, false);
		if (null != resourceContainer.getPlugins()) {
			for (int i=0; i<resourceContainer.getPlugins().length; i++) {
				resourceContainer.getPlugins()[i].initialize(context);
			}
		}

		if (null != parsingSession) {
			// initialize macro imports
			Map staticImports = parsingSession.getStaticMacroImports();
			if (null != staticImports) {
				for  (Iterator i=staticImports.entrySet().iterator(); i.hasNext(); ) {
					Map.Entry entry = (Map.Entry) i.next();
					context.addMacroImport((String) entry.getKey(), (String) entry.getValue());
				}
			}
		}
		
		if (null == context.getLocale())
			context.setLocale(Locale.getDefault());

		return context;
	}
}
