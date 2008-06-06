package test.hudson.zipscript.parser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import hudson.zipscript.ExpressEngine;
import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.context.MapContextWrapper;
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
import hudson.zipscript.parser.template.element.lang.TextElement;
import hudson.zipscript.parser.template.element.lang.WhitespacePatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.VariablePatternMatcher;
import hudson.zipscript.parser.template.element.special.BooleanPatternMatcher;
import hudson.zipscript.parser.template.element.special.NullPatternMatcher;
import hudson.zipscript.parser.template.element.special.NumericPatternMatcher;
import hudson.zipscript.parser.template.element.special.StringPatternMatcher;
import hudson.zipscript.template.EvaluationTemplate;
import hudson.zipscript.template.Template;
import junit.framework.TestCase;


public class ParserTest extends TestCase {

//	public static TestSuite suite () {
//		TestSuite sute = new TestSu
//	}

	public ParserTest (String name) {
		super(name);
	}

	public void testDirectives () throws Exception {
		List l = new ArrayList();
		l.add(new Integer(1));
		l.add("foo");
		Map context = new HashMap();
		context.put("bar", l);
		context.put("va2", "bar");

		String contents = IOUtils.toString(getClass().getResourceAsStream("/template.etl"));
		Template template = ExpressEngine.getTemplate(contents);
		System.out.println(template.merge(context));
	}

	public void testConditionalOperators () throws Exception {
		Map context = new HashMap();
		TextElement textElement = new TextElement("this is a test");
		context.put("foo", textElement);
		EvaluationTemplate template = null;
		template = ExpressEngine.getTemplateForEvaluation(
			"${foo.text}");
		System.out.println(template.objectValue(context));
		template = ExpressEngine.getTemplateForEvaluation(
			"1+2<4");
		System.out.println(template.objectValue(context));
	}

	private void print(String s, Component[] components, DefaultElementFactory factory)
	throws ParseException {
		ParseData parseData = ExpressionParser.getInstance().parse(s, components, factory);
		print (parseData.getElements());
	}

	private static final ParseParameters parameters = new ParseParameters(false, true);
	private void print(String s, PatternMatcher[] matchers, DefaultElementFactory factory)
	throws ParseException {
		List elements = ExpressionParser.getInstance().parse(s, matchers, factory, parameters);
		print (elements);
	}

	private void print (List l) {
		for (int i=0; i<l.size(); i++) {
			System.out.println(l.get(i));
		}
	}

	private void print (Element[] l) {
		for (int i=0; i<l.length; i++) {
			System.out.println(l[i]);
		}
	}
}
