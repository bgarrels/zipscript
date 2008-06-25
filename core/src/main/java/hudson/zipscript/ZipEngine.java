package hudson.zipscript;

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
import hudson.zipscript.parser.template.element.directive.foreachdir.ForeachComponent;
import hudson.zipscript.parser.template.element.directive.ifdir.IfComponent;
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
import java.util.Properties;

public class ZipEngine {

	private ResourceLoader templateResourceloader = new ClasspathResourceLoader();
	private ResourceLoader macroLibResourceloader = templateResourceloader;
	private ResourceLoader evalResourceLoader = new StringResourceLoader();

	private static ZipEngine instance;
	public static ZipEngine getInstance ()  {
		if (null == instance)
			instance = new ZipEngine();
		return instance;
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
			new CallComponent()
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


	public void init () throws InitializationException {
	}

	public void init (Properties properties) {
		init ((Map) properties);
	}
	
	public void init (Map properties) {
		// get the default resource loader
		String s = (String) properties.get("templateResourceLoader.class");
		if (null != s) {
			try {
				this.templateResourceloader = (ResourceLoader) ClassUtil.loadClass(s, "resource loader", null);
			}
			catch (ClassCastException e) {
				throw new InitializationException("The resource loader '" + s + "' must extend hudson.zipscript.resource.ResourceLoader", e);
			}
		}
		s = (String) properties.get("macroLibResourceLoader.class");
		if (null != s) {
			try {
				this.macroLibResourceloader = (ResourceLoader) ClassUtil.loadClass(s, "resource loader", null);
			}
			catch (ClassCastException e) {
				throw new InitializationException("The resource loader '" + s + "' must extend hudson.zipscript.resource.ResourceLoader", e);
			}
		}
		s = (String) properties.get("evalResourceLoader.class");
		if (null != s) {
			try {
				this.evalResourceLoader = (ResourceLoader) ClassUtil.loadClass(s, "resource loader", null);
			}
			catch (ClassCastException e) {
				throw new InitializationException("The resource loader '" + s + "' must extend hudson.zipscript.resource.ResourceLoader", e);
			}
		}
	}

	public void addMacroLibrary (String namespace, String resourcePath) throws ParseException {
		macroManager.addMacroLibrary(
				namespace, resourcePath, macroLibResourceloader);
	}

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

	public Template getTemplate (String source) throws ParseException {
		try {
			TemplateResource tr = (TemplateResource) resourceMap.get(source);
			if (null == tr) {
				tr = loadTemplate(source, TEMPLATE_COMPONENTS, null, new ParseParameters(false, false));
			}
			if (tr.resource.hasBeenModifiedSince(tr.lastModified)) {
				// reload the resource
				tr = loadTemplate(source, TEMPLATE_COMPONENTS, null, new ParseParameters(false, false));
			}
			tr.template.setMacroManager(macroManager);
			return tr.template;
		}
		catch (ParseException e) {
			e.setResource(source);
			throw e;
		}
	}

	public Evaluator getEvaluator (String contents) throws ParseException {
		try {
			Element element = ExpressionParser.getInstance().parseToElement(
					contents, VARIABLE_MATCHERS, evalElementFactory, 0, macroManager);
			Evaluator evaluator = new TemplateImpl(element);
			evaluator.setMacroManager(macroManager);
			return evaluator;
		}
		catch (ParseException e) {
			e.setResource(contents);
			throw e;
		}
	}

	protected TemplateResource loadTemplate (
			String source, Component[] components, PatternMatcher[] patternMatchers, ParseParameters parseParameters)
	throws ParseException {
		Resource resource = templateResourceloader.getResource(source);
		String contents = IOUtil.toString(resource.getInputStream());;
		ParsingResult pr = null;
		if (null != components) {
			pr = ExpressionParser.getInstance().parse(
					contents, components, mergeElementFactory, 0, macroManager);
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
					source, patternMatchers, mergeElementFactory, 0, macroManager);
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
