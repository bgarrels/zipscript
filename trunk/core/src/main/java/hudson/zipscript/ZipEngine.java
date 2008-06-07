package hudson.zipscript;

import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.comparator.ComparatorPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.logic.AndLogicPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.logic.OrLogicPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.math.MathPatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;
import hudson.zipscript.parser.template.element.directive.foreachdir.ForeachComponent;
import hudson.zipscript.parser.template.element.directive.ifdir.IfComponent;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroComponent;
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
import hudson.zipscript.parser.template.element.lang.variable.VariableComponent;
import hudson.zipscript.parser.template.element.lang.variable.VariablePatternMatcher;
import hudson.zipscript.parser.template.element.special.BooleanPatternMatcher;
import hudson.zipscript.parser.template.element.special.NullPatternMatcher;
import hudson.zipscript.parser.template.element.special.NumericPatternMatcher;
import hudson.zipscript.parser.template.element.special.SpecialStringDefaultEelementFactory;
import hudson.zipscript.parser.template.element.special.SpecialVariableDefaultEelementFactory;
import hudson.zipscript.parser.template.element.special.StringPatternMatcher;
import hudson.zipscript.template.EvaluationTemplate;
import hudson.zipscript.template.Template;
import hudson.zipscript.template.TemplateImpl;

public class ZipEngine {

	public static final Component[] TEMPLATE_COMPONENTS = new Component[] {
			new IfComponent(),
			new ForeachComponent(),
			new WhileComponent(),
			new MacroComponent(),
			new VariableComponent(),
			new SetComponent()
	};
	public static final PatternMatcher[] VARIABLE_MATCHERS = new PatternMatcher[] {
			new VariablePatternMatcher(),
			new ListPatternMatcher(),
			new NumericPatternMatcher(),
			new CommaPatternMatcher(),
			new StringPatternMatcher(),
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
	};
	private static final DefaultElementFactory mergeElementFactory = new TextDefaultElementFactory();
	private static final DefaultElementFactory evalElementFactory = new SpecialVariableDefaultEelementFactory();
	private static final ParseParameters params = new ParseParameters(false, false);

	public static Template getTemplate (String resourcePath) throws ParseException {
		ParsingResult rtn = ExpressionParser.getInstance().parse(resourcePath, TEMPLATE_COMPONENTS, mergeElementFactory);
		Template template = new TemplateImpl(rtn.getElements());
		return template;
	}

	public static EvaluationTemplate getTemplateForEvaluation (String contents) throws ParseException {
		Element element = ExpressionParser.getInstance().parseToElement(
				contents, VARIABLE_MATCHERS, evalElementFactory);
		return new TemplateImpl(element);
	}
}
