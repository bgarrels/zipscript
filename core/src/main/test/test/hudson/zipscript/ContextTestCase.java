package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import junit.framework.TestCase;

public class ContextTestCase extends TestCase {

	public void testUniqueId () throws Exception {
		assertNotNull(eval("UniqueId"));
	}

	public void testNow () throws Exception {
		assertNotNull(eval("Now"));
	}

	public void testMath () throws Exception {
		assertNotNull(eval("Math.random"));
	}

	private Object eval (String s)
	throws ParseException, ExecutionException {
		return eval(s, null);
	}

	private Object eval (String s, Object context)
	throws ParseException, ExecutionException {
		return ZipEngine.getInstance().getEvaluator(s).objectValue(context);
	}
}
