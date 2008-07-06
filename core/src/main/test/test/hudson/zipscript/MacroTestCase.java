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

public class MacroTestCase extends TestCase {

	public void testSimpleMacro () throws Exception {
		Map context = new HashMap();
		String mergeTemplate = "templates/macro_test.zs";
		String resultFile = "/templates/macro_result.txt";
		evalResult(mergeTemplate, resultFile, context);
	}

	public void testCommonTDOMacro () throws Exception {
		Map context = new HashMap();
		String mergeTemplate = "templates/macro_common_test.zs";
		String resultFile = "/templates/macro_common_result.txt";
		evalResult(mergeTemplate, resultFile, context);
	}

	public void testNestingMacro () throws Exception {
		Map context = new HashMap();
		String mergeTemplate = "templates/macro_nesting_test.zs";
		String resultFile = "/templates/macro_nesting_result.txt";
		evalResult(mergeTemplate, resultFile, null);
	}

	public void testHeaderFooterMacro () throws Exception {
		Map context = new HashMap();
		String mergeTemplate = "templates/macro_headerfooter_test.zs";
		String resultFile = "/templates/macro_headerfooter_result.txt";
		evalResult(mergeTemplate, resultFile, null);
	}

	public void testObjectOrientedMacro () throws Exception {
		Map context = new HashMap();
		List l = new ArrayList(3);
		l.add(new Person("John", "Smith", "03/14/82"));
		l.add(new Person("Jimmy", "Carter", "09/03/63"));
		l.add(new Person("Jerry", null, "11/21/79"));
		context.put("people", l);
		String mergeTemplate = "templates/macro_oo_test.zs";
		String resultFile = "/templates/macro_oo_result.txt";
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
