package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.io.IOUtils;

public class DirectiveTestCase extends TestCase {

	public static TestSuite suite () {
		TestSuite suite = new TestSuite();
		suite.addTest(new DirectiveTestCase("testForeach"));
		suite.addTest(new DirectiveTestCase("testWhile"));
		suite.addTest(new DirectiveTestCase("testIf"));
		suite.addTest(new DirectiveTestCase("testMacro"));
		return suite;
	}

	public DirectiveTestCase () {}

	public DirectiveTestCase (String name) {
		super(name);
	}
	
	public void testForeach () throws Exception {
		String mergeTemplate = "templates/foreach_test.zs";
		String resultFile = "/templates/foreach_result.txt";
		Map context = null;
		
		context = new HashMap();
		String[] l1 = new String[] {"abc", "def", "ghi"};
		context.put("theList", l1);
		evalResult(mergeTemplate, resultFile, context);

		context = new HashMap();
		List l2 = new ArrayList();
		l2.add("abc");
		l2.add("def");
		l2.add("ghi");
		context.put("theList", l2);
		evalResult(mergeTemplate, resultFile, context);

		resultFile = "/templates/foreach_result_iter.txt";
		context = new HashMap();
		context.put("theList", l2.iterator());
		evalResult(mergeTemplate, resultFile, context);
	}

	public void testWhile () throws Exception {
		String mergeTemplate = "templates/while_test.zs";
		String resultFile = "/templates/while_result.txt";
		evalResult(mergeTemplate, resultFile, null);
	}

	public void testIf () throws Exception {
		String mergeTemplate = "templates/if_test.zs";
		String resultFile = "/templates/if_result.txt";
		Map context = new HashMap();
		context.put("foo", "abc");
		context.put("bar", "def");
		context.put("baz", "ghi");
		evalResult(mergeTemplate, resultFile, context);
	}

	public void testMacro () throws Exception {
		String mergeTemplate = "templates/macro_test.zs";
		String resultFile = "/templates/macro_result.txt";
		evalResult(mergeTemplate, resultFile, null);

		mergeTemplate = "templates/macro_nesting_test.zs";
		resultFile = "/templates/macro_nesting_result.txt";
		evalResult(mergeTemplate, resultFile, null);
	}

	private void evalResult (String mergeTemplate, String resultFile, Object context)
	throws ParseException, ExecutionException, IOException {
		String expectedResult = IOUtils.toString(getClass().getResourceAsStream(resultFile));
		String actualResult = merge(mergeTemplate, context);
		assertEquals(expectedResult, actualResult);
	}

	private String merge (String template, Object context)
	throws ParseException, ExecutionException, IOException {
		return ZipEngine.getInstance().getTemplate(template).merge(context);
	}
}
