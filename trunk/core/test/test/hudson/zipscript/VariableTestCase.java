package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;

import test.hudson.zipscript.model.Obj1;

public class VariableTestCase extends TestCase {

	public void testSimpleVariables () throws Exception {
		String mergeTemplate = "/templates/variable_simple_test.zs";
		String resultFile = "/templates/variable_simple_result.txt";
		Map context = null;
		
		context = new HashMap();
		context.put("myObject", new Obj1());
		
//		String contents = IOUtils.toString(getClass().getResourceAsStream(mergeTemplate));
//		System.out.println(ZipEngine.getTemplate(contents).merge(context));
		evalResult(mergeTemplate, resultFile, context);
	}

	private void evalResult (String mergeTemplate, String resultFile, Object context)
	throws ParseException, ExecutionException, IOException {
		String expectedResult = IOUtils.toString(getClass().getResourceAsStream(resultFile));
		String actualResult = merge(mergeTemplate, context);
		assertEquals(expectedResult, actualResult);
	}

	private String merge (String template, Object context)
	throws ParseException, ExecutionException, IOException {
		String contents = IOUtils.toString(getClass().getResourceAsStream(template));
		return ZipEngine.getTemplate(contents).merge(context);
	}
}