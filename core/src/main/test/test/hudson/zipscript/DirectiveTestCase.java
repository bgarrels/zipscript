package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.util.IOUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import test.hudson.zipscript.model.Person;

public class DirectiveTestCase extends TestCase {

//	public static TestSuite suite () {
//		TestSuite suite = new TestSuite();
//		suite.addTest(new DirectiveTestCase("testForeach"));
//		suite.addTest(new DirectiveTestCase("testWhile"));
//		suite.addTest(new DirectiveTestCase("testIf"));
//		suite.addTest(new DirectiveTestCase("testSet"));
//		suite.addTest(new DirectiveTestCase("testComment"));
//		suite.addTest(new DirectiveTestCase("testMacro"));
//		return suite;
//	}

	public DirectiveTestCase () {}

	public DirectiveTestCase (String name) {
		super(name);
	}
	
	public void testSimple () throws Exception {
		String mergeTemplate = "templates/simple_test.zs";
		System.out.println(
				ZipEngine.createInstance().getTemplate(mergeTemplate).merge(null));
		
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
		Map context = new HashMap();
		List l = new ArrayList(3);
		l.add(new Person("John", "Smith", "03/14/82"));
		l.add(new Person("Jimmy", "Carter", "09/03/63"));
		l.add(new Person("Jerry", null, "11/21/79"));
		context.put("people", l);
		String mergeTemplate = "templates/macro_test.zs";
		String resultFile = "/templates/macro_result.txt";
		evalResult(mergeTemplate, resultFile, context);

		mergeTemplate = "templates/macro_nesting_test.zs";
		resultFile = "/templates/macro_nesting_result.txt";
		evalResult(mergeTemplate, resultFile, null);
	}

	public void testSet () throws Exception {
		String mergeTemplate = "templates/set_test.zs";
		String resultFile = "/templates/set_result.txt";
		evalResult(mergeTemplate, resultFile, null);
	}

	public void testComment () throws Exception {
		String mergeTemplate = "templates/comment_test.zs";
		String resultFile = "/templates/comment_result.txt";
		evalResult(mergeTemplate, resultFile, null);
	}

	private void evalResult (String mergeTemplate, String resultFile, Object context)
	throws ParseException, ExecutionException, IOException {
		String expectedResult = IOUtil.toString(getClass().getResourceAsStream(resultFile));
		String actualResult = merge(mergeTemplate, context);
		assertEquals(expectedResult, actualResult);
	}

	private String merge (String template, Object context)
	throws ParseException, ExecutionException, IOException {
		return ZipEngine.createInstance().getTemplate(template)
			.merge(context);
	}
}
