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
		Date date = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse("07/21/1975 10:37:18");
		Map context = new HashMap();
		context.put("myDate", date);
		context.put("myNumber", new BigDecimal("12345.67"));
		context.put("myPercent", new BigDecimal(".67"));

		assertEquals("7/21/75", eval("myDate|short", context));
		assertEquals("Jul 21, 1975", eval("myDate|medium", context));
		assertEquals("July 21, 1975", eval("myDate|long", context));
		assertEquals("10:37 AM", eval("myDate|t:short", context));
		assertEquals("10:37:18 AM", eval("myDate|t:medium", context));
		assertEquals("10:37:18 AM EDT", eval("myDate|t:long", context));
		assertEquals("7/21/75 10:37:18 AM EDT", eval("myDate|short:long", context));
		assertEquals("Jul 21, 1975 10:37 AM", eval("myDate|medium:short", context));
		assertEquals("Jul 21, 1975", eval("myDate|'MMM dd, yyyy'", context));
		assertEquals("12,345.67", eval("myNumber|'##,###.00'", context));
		assertEquals("12,345.67", eval("myNumber|number", context));
		assertEquals("67%", eval("myPercent|percent", context));
		assertEquals("$12,345.67", eval("myNumber|currency", context));
	}

	private Object eval (String s, Object context)
	throws ParseException, ExecutionException {
		EvaluationTemplate t = ZipEngine.getInstance().getTemplateForEvaluation(s);
		return t.objectValue(context);
	}
}
