package hudson.zipscript;

import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParseData;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.element.DefaultElementFactory;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.comparator.ComparatorPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.logic.AndLogicPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.logic.OrLogicPatternMatcher;
import hudson.zipscript.parser.template.element.comparator.math.MathPatternMatcher;
import hudson.zipscript.parser.template.element.component.Component;
import hudson.zipscript.parser.template.element.component.ForeachComponent;
import hudson.zipscript.parser.template.element.component.IfComponent;
import hudson.zipscript.parser.template.element.component.MacroComponent;
import hudson.zipscript.parser.template.element.component.VariableComponent;
import hudson.zipscript.parser.template.element.component.WhileComponent;
import hudson.zipscript.parser.template.element.group.GroupPatternMatcher;
import hudson.zipscript.parser.template.element.lang.TextDefaultElementFactory;
import hudson.zipscript.parser.template.element.lang.WhitespacePatternMatcher;
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

	private static final Component[] components = new Component[] {
			new IfComponent(),
			new ForeachComponent(),
			new WhileComponent(),
			new MacroComponent(),
			new VariableComponent()
	};
	private static final PatternMatcher[] matchers = new PatternMatcher[] {
			new VariablePatternMatcher(),
			new StringPatternMatcher(),
			new NumericPatternMatcher(),
			new AndLogicPatternMatcher(),
			new OrLogicPatternMatcher(),
			new BooleanPatternMatcher(),
			new ComparatorPatternMatcher(),
			new MathPatternMatcher(),
			new NullPatternMatcher(),
			new GroupPatternMatcher(),
			new WhitespacePatternMatcher()
	};
	private static final DefaultElementFactory mergeElementFactory = new TextDefaultElementFactory();
	private static final DefaultElementFactory evalElementFactory = new SpecialVariableDefaultEelementFactory();
	private static final ParseParameters params = new ParseParameters(false, false);

	public static Template getTemplate (String resourcePath) throws ParseException {
		ParseData pd = ExpressionParser.getInstance().parse(resourcePath, components, mergeElementFactory);
		Template template = new TemplateImpl(pd.getElements());
		return template;
	}

	public static EvaluationTemplate getTemplateForEvaluation (String contents) throws ParseException {
		Element element = ExpressionParser.getInstance().parseToElement(
				contents, matchers, evalElementFactory);
		return new TemplateImpl(element);
	}
}
