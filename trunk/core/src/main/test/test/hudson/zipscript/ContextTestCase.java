package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import junit.framework.TestCase;

public class ContextTestCase extends TestCase {

	public void testNoContextExpressions () throws Exception {
		Object obj = eval("UniqueId");
		assertNotNull(obj);
		obj = eval("Now");
		assertNotNull(obj);
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
