package hudson.zipscript;

import hudson.zipscript.parser.Constants;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.exception.InitializationException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.comment.CommentComponent;
import hudson.zipscript.parser.template.element.comparator.ComparatorPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.InComparatorPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.logic.AndLogicPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.logic.OrLogicPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.math.MathPatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;
import hudson.zipscript.parser.template.element.directive.calldir.CallComponent;
import hudson.zipscript.parser.template.element.directive.escape.EscapeComponent;
import hudson.zipscript.parser.template.element.directive.foreachdir.ForeachComponent;
import hudson.zipscript.parser.template.element.directive.ifdir.IfComponent;
import hudson.zipscript.parser.template.element.directive.initialize.InitializeComponent;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroComponent;
import hudson.zipscript.parser.template.element.directive.setdir.GlobalComponent;
import hudson.zipscript.parser.template.element.directive.setdir.SetComponent;
import hudson.zipscript.parser.template.element.directive.whiledir.WhileComponent;
import hudson.zipscript.parser.template.element.group.GroupPatternMatcher;
import hudson.zipscript.parser.template.element.group.ListPatternMatcher;
import hudson.zipscript.parser.template.element.group.MapPatternMatcher;
import hudson.zipscript.parser.template.element.lang.AssignmentPatternMatcher;
import hudson.zipscript.parser.template.element.lang.CommaPatternMatcher;
import hudson.zipscript.parser.template.element.lang.DotPatternMatcher;
import hudson.zipscript.parser.template.element.lang.TextDefaultElementFactory;
import hudson.zipscript.parser.template.element.lang.WhitespacePatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.SpecialVariableDefaultEelementFactory;
import hudson.zipscript.parser.template.element.lang.variable.VarDefaultElementPatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.VariableComponent;
import hudson.zipscript.parser.template.element.lang.variable.VariablePatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.format.VarFormattingElementPatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.special.VarSpecialElementPatternMatcher;
import hudson.zipscript.parser.template.element.special.BooleanPatternMatcher;
import hudson.zipscript.parser.template.element.special.NullPatternMatcher;
import hudson.zipscript.parser.template.element.special.NumericPatternMatcher;
import hudson.zipscript.parser.template.element.special.StringPatternMatcher;
import hudson.zipscript.parser.util.ClassUtil;
import hudson.zipscript.parser.util.IOUtil;
import hudson.zipscript.resource.ClasspathResourceLoader;
import hudson.zipscript.resource.Resource;
import hudson.zipscript.resource.ResourceLoader;
import hudson.zipscript.resource.StringResourceLoader;
import hudson.zipscript.resource.macrolib.MacroManager;
import hudson.zipscript.template.Evaluator;
import hudson.zipscript.template.Template;
import hudson.zipscript.template.TemplateImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class used to retrieve templates and evaluators for the ZipScript expression language.
 * For default engine: <code>ZipEngine.createInstance()...</code>
 * For customized engine: <code>ZipEngine.createInstance(Map properties)...</code>
 * <p>
 * The default behavior is to load template and macro resources from the classpath and evaluate the
 * string source directly that is passed to the getEvaluator(String) method.  Properties can be set to alter the behavior
 * of the resource locators and other functionality.  Reference all available properties <a href="http://www.zipscript.org/docs/current/bk02ch04.html">here</a>.
 * </p>
 * <p>
 * // standard usage
 * <pre>
 * Properties props = new Properties();
 * // set properties
 * engine.init(props);
 * ZipEngine engine = ZipEngine.newInstance(props);
 * Map context = new HashMap();
 * // load the context
 * 
 * // performing template merging
 * Template t = engine.getTemplate("myResource.zs");
 * String mergeResults = t.merge(context);
 * 
 * // performing boolean evaluation
 * Evaluator e = engine.getEvaluator("foo > bar");
 * boolean evalResult = e.booleanValue(context);
 * 
 * // object retrieval
 * Evaluator e = engine.getEvaluator("foo");
 * Object result = e.objectValue(context);
 * </pre>
 * </p>
 * 
 * @author Joe Hudson
 */
public class ZipEngine {

	private ResourceLoader templateResourceloader = new ClasspathResourceLoader();
	private ResourceLoader macroLibResourceloader = templateResourceloader;
	private ResourceLoader evalResourceLoader = new StringResourceLoader();
	
	private Map initParameters;

	/**
	 * newInstance should be used
	 */
	private ZipEngine () {}

	/**
	 * Retrurn new instanceof ZipEngine
	 */
	public static ZipEngine createInstance ()  {
		return new ZipEngine();
	}

	/**
	 * Retrurn new instanceof ZipEngine initialized with property map
	 */
	public static ZipEngine createInstance (Map properties) {
		ZipEngine zipEngine = new ZipEngine();
		zipEngine.init(properties);
		return zipEngine;
	}

	public static final Component[] TEMPLATE_COMPONENTS = new Component[] {
			new CommentComponent(),
			new IfComponent(),
			new ForeachComponent(),
			new WhileComponent(),
			new MacroComponent(),
			new VariableComponent(),
			new SetComponent(),
			new GlobalComponent(),
			new CallComponent(),
			new EscapeComponent(),
			new InitializeComponent()
	};
	public static final PatternMatcher[] VARIABLE_MATCHERS = new PatternMatcher[] {
			new VariablePatternMatcher(),
			new ListPatternMatcher(),
			new NumericPatternMatcher(),
			new CommaPatternMatcher(),
			new StringPatternMatcher(),
			new InComparatorPatternMatcher(),
			new AndLogicPatternMatcher(),
			new OrLogicPatternMatcher(),
			new BooleanPatternMatcher(),
			new ComparatorPatternMatcher(),
			new MathPatternMatcher(),
			new NullPatternMatcher(),
			new GroupPatternMatcher(),
			new WhitespacePatternMatcher(),
			new MapPatternMatcher(),
			new DotPatternMatcher(),
			new AssignmentPatternMatcher(),
			new VarDefaultElementPatternMatcher(),
			new VarSpecialElementPatternMatcher(),
			new VarFormattingElementPatternMatcher()
	};
	private static final DefaultElementFactory mergeElementFactory = TextDefaultElementFactory.INSTANCE;
	private static final DefaultElementFactory evalElementFactory = new SpecialVariableDefaultEelementFactory();
	private MacroManager macroManager = new MacroManager();

	/**
	 * Initialize with property map
	 * @param properties
	 */
	private void init (Map properties) {
		// get the default resource loader
		String s = (String) properties.get(Constants.TEMPLATE_RESOURCE_LOADER_CLASS);
		if (null != s) {
			try {
				this.templateResourceloader = (ResourceLoader) ClassUtil.loadResource("templateResourceLoader", properties,
						ResourceLoader.class, ClasspathResourceLoader.class, Constants.RESOURCE_LOADER_TYPES);
			}
			catch (ClassCastException e) {
				throw new InitializationException("The resource loader '" + s + "' must extend hudson.zipscript.resource.ResourceLoader", e);
			}
		}
		s = (String) properties.get(Constants.MACROLIB_RESOURCE_LOADER_CLASS);
		if (null != s) {
			try {
				this.macroLibResourceloader = (ResourceLoader) ClassUtil.loadResource("macroLibResourceLoader", properties,
						ResourceLoader.class, templateResourceloader.getClass(), Constants.RESOURCE_LOADER_TYPES);
			}
			catch (ClassCastException e) {
				throw new InitializationException("The resource loader '" + s + "' must extend hudson.zipscript.resource.ResourceLoader", e);
			}
		}
		s = (String) properties.get(Constants.EVAL_RESOURCE_LOADER_CLASS);
		if (null != s) {
			try {
				this.evalResourceLoader = (ResourceLoader) ClassUtil.loadResource("evalResourceLoader", properties,
						ResourceLoader.class, StringResourceLoader.class, Constants.RESOURCE_LOADER_TYPES);
			}
			catch (ClassCastException e) {
				throw new InitializationException("The resource loader '" + s + "' must extend hudson.zipscript.resource.ResourceLoader", e);
			}
		}
		initParameters = properties;
	}

	/**
	 * Add a reference to a macro library
	 * @param namespace the namespace reference for macros within this resource
	 * @param resourcePath the resource path for the macro library resource
	 * @throws ParseException
	 */
	public void addMacroLibrary (String namespace, String resourcePath) throws ParseException {
		macroManager.addMacroLibrary(
				namespace, resourcePath, macroLibResourceloader);
	}

	/**
	 * Add a reference to a macro library file (or add a macro lib directory).  All macro lib resource must end with '.zsm'
	 * @param file the file or directory name
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public void addMacroLibrary (File file) throws FileNotFoundException, ParseException {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i=0; i<files.length; i++) {
				addMacroLibrary(files[i]);
			}
		}
		else  if (file.getName().endsWith(".zsm")) {
			String namespace = file.getName().substring(
					0, file.getName().length()-4);
			String contents = IOUtil.toString(new FileInputStream(file)); 
			macroManager.addMacroLibrary(
					namespace, contents, StringResourceLoader.INSTANCE);
		}
	}

	// internal cache
	private Map resourceMap = new HashMap();

	/**
	 * Return a template used for merging static data with an object model.  This template will be cached as long
	 * as the same instance of the ZipEngine is used for template retrieval.  The template resource loader will
	 * be used to load the template.  This can be modified using the 'templateResourceLoader.class' init property.
	 * @param source the template resource source path & name
	 * @return the Template
	 * @throws ParseException
	 */
	public Template getTemplate (String source) throws ParseException {
		try {
			TemplateResource tr = (TemplateResource) resourceMap.get(source);
			if (null == tr) {
				tr = loadTemplate(source, TEMPLATE_COMPONENTS, null, new ParseParameters(false, false, initParameters));
			}
			if (tr.resource.hasBeenModifiedSince(tr.lastModified)) {
				// reload the resource
				tr = loadTemplate(source, TEMPLATE_COMPONENTS, null, new ParseParameters(false, false, initParameters));
			}
			tr.template.setMacroManager(macroManager);
			return tr.template;
		}
		catch (ParseException e) {
			e.setResource(source);
			throw e;
		}
	}

	/**
	 * Return an expression evaluator.  The expression resource loader will be used to load the template.
	 * This can be modified using the 'evalResourceLoader.class' init property.
	 * @param contents the expression or resource reference (depending on resource loader)
	 * @return the evaluator
	 * @throws ParseException
	 */
	public Evaluator getEvaluator (String contents) throws ParseException {
		try {
			Element element = ExpressionParser.getInstance().parseToElement(
					contents, VARIABLE_MATCHERS, evalElementFactory, 0, macroManager, initParameters);
			TemplateImpl evaluator = new TemplateImpl(element);
			evaluator.setMacroManager(macroManager);
			return evaluator;
		}
		catch (ParseException e) {
			e.setResource(contents);
			throw e;
		}
	}

	/**
	 * Load a template
	 * @param source the source
	 * @param components all components for pattern matching
	 * @param patternMatchers all pattern matchers
	 * @param parseParameters parsing parameters
	 * @return the template
	 * @throws ParseException
	 */
	protected TemplateResource loadTemplate (
			String source, Component[] components, PatternMatcher[] patternMatchers, ParseParameters parseParameters)
	throws ParseException {
		Resource resource = templateResourceloader.getResource(source);
		String contents = IOUtil.toString(resource.getInputStream());;
		ParsingResult pr = null;
		if (null != components) {
			pr = ExpressionParser.getInstance().parse(
					contents, components, mergeElementFactory, 0, macroManager, initParameters);
		}
		else {
			pr = ExpressionParser.getInstance().parse(
					contents, patternMatchers, mergeElementFactory,
					new ParsingSession(parseParameters, macroManager), 0);
		}
		return new TemplateResource(
				new TemplateImpl(pr.getElements(), pr.getParsingSession(), pr), resource);
	}

	protected Template loadTemplateForEvaluation (
			String source, Component[] components, PatternMatcher[] patternMatchers, ParseParameters parseParameters)
	throws ParseException {
		Resource resource = evalResourceLoader.getResource(source);
		String contents = IOUtil.toString(resource.getInputStream());
		Element element = null;
		if (null != components) {
			element = ExpressionParser.getInstance().parseToElement(
					contents, components, mergeElementFactory, 0, macroManager);
		}
		else {
			element = ExpressionParser.getInstance().parseToElement(
					source, patternMatchers, mergeElementFactory, 0, macroManager, initParameters);
		}
		return new TemplateImpl(element);
	}

	private class TemplateResource {
		public long lastModified;
		public Template template;
		public Resource resource;
		public TemplateResource (Template template, Resource resource){
			this.template = template;
			this.resource = resource;
			this.lastModified = System.currentTimeMillis();
		}
	}
}
