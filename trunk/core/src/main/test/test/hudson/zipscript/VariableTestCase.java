package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.util.IOUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import test.hudson.zipscript.model.Obj1;

public class VariableTestCase extends TestCase {

	public void testSimpleVariables () throws Exception {
		String mergeTemplate = "templates/variable_simple_test.zs";
		String resultFile = "/templates/variable_simple_result.txt";
		Map context = null;
		
		context = new HashMap();
		context.put("myObject", new Obj1());
		context.put("myString", "this is a test");
		context.put("myList", new ArrayList());
		evalResult(mergeTemplate, resultFile, context);
	}

	private void evalResult (String mergeTemplate, String resultFile, Object context)
	throws ParseException, ExecutionException, IOException {
		String expectedResult = IOUtil.toString(getClass().getResourceAsStream(resultFile));
		String actualResult = merge(mergeTemplate, context);
		assertEquals(expectedResult, actualResult);
	}

	private String merge (String template, Object context)
	throws ParseException, ExecutionException, IOException {
		return ZipEngine.getInstance().getTemplate(template).merge(context);
	}
}