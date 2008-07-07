package hudson.zipscript.parser;

import hudson.zipscript.resource.ClasspathResourceLoader;
import hudson.zipscript.resource.FileResourceLoader;
import hudson.zipscript.resource.StringResourceLoader;
import hudson.zipscript.resource.URLResourceLoader;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String SUPPRESS_NULL_ERRORS = "suppressNullErrors";
	public static final String TRIM_MACRO_BODY = "trimMacroBody";
	
	public static final String TEMPLATE_RESOURCE_LOADER_CLASS = "templateResourceLoader.class";
	public static final String MACROLIB_RESOURCE_LOADER_CLASS = "macroLibResourceLoader.class";
	public static final String EVAL_RESOURCE_LOADER_CLASS = "evalResourceLoader.class";

	public static final Map RESOURCE_LOADER_TYPES = new HashMap();
	static {
		RESOURCE_LOADER_TYPES.put("classpath", ClasspathResourceLoader.class);
		RESOURCE_LOADER_TYPES.put("file", FileResourceLoader.class);
		RESOURCE_LOADER_TYPES.put("url", URLResourceLoader.class);
		RESOURCE_LOADER_TYPES.put("URL", URLResourceLoader.class);
		RESOURCE_LOADER_TYPES.put("string", StringResourceLoader.class);
	}
}
