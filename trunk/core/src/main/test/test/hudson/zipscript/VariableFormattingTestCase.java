package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.template.EvaluationTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class VariableFormattingTestCase extends TestCase {

	public void testVariableDefaultExpressions () throws Exception {
		Date date = new SimpleDateFormat("MM/dd/yyyy").parse("07/21/1975");
		Map context = new HashMap();
		context.put("myDate", date);
		context.put("myNumber", new BigDecimal("12345.67"));
		assertEquals("Jul 21, 1975", eval("myDate|'MMM dd, yyyy'", context));
		assertEquals("12,345.67", eval("myNumber|'##,###.00'", context));
	}

	private Object eval (String s)
	throws ParseException, ExecutionException {
		return eval(s, null);
	}

	private Object eval (String s, Object context)
	throws ParseException, ExecutionException {
		EvaluationTemplate t = ZipEngine.getInstance().getTemplateForEvaluation(s);
		return t.objectValue(context);
	}
}
