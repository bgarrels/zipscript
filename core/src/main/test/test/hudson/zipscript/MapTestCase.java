package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class MapTestCase extends TestCase {

	public void testMapSyntax () throws Exception {
		Map context = new HashMap();
		Map map = new HashMap();
		map.put("foo", "this is a test");
		context.put("bar.b.q", "this is another test");
		context.put("myMap", map);
		Object obj = eval("${myMap['foo']}", context);
		assertEquals("this is a test", obj);
	}

	private Object eval (String s)
	throws ParseException, ExecutionException {
		return eval(s, null);
	}

	private Object eval (String s, Object context)
	throws ParseException, ExecutionException {
		return ZipEngine.getInstance().getTemplateForEvaluation(s).objectValue(context);
	}
}
