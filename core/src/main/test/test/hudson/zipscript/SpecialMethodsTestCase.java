package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class SpecialMethodsTestCase extends TestCase {

	public void testStringMethods () throws Exception {
		Map context = new HashMap();
		context.put("str", "test");
		assertEquals("Test", merge("str?upperFirst", context));
		assertEquals("TEST", merge("str?upperCase", context));
		assertEquals("   test", merge("str?leftPad(3)", context));
		assertEquals("test   ", merge("str?rightPad(3)", context));
		assertEquals("true", merge("str?contains('es')", context));
		context.put("str", "TEST");
		assertEquals("tEST", merge("str?lowerFirst", context));
		assertEquals("test", merge("str?lowerCase", context));
		context.put("str", "<&{}\'\">");
		assertEquals("&lt;&amp;{}'&quot;&gt;", merge("str?html", context));
		assertEquals("&lt;&amp;{}'&quot;&gt;", merge("str?xml", context));
		assertEquals("<&\\{\\}'\">", merge("str?rtf", context));
		assertEquals("<&{}\\'\\\"\\>", merge("str?js", context));
		context.put("str", "http://www.google.com?a=b&d=e");
		assertEquals("http%3A%2F%2Fwww.google.com%3Fa%3Db%26d%3De", merge("str?url", context));
		context.put("str", "com.foo.bar");
		String[] arr = (String[]) ZipEngine.getInstance().getEvaluator(
				"str?split('.')").objectValue(context);
		assertEquals(3, arr.length);
		assertEquals("com", arr[0]);
		assertEquals("foo", arr[1]);
		assertEquals("bar", arr[2]);
	}

	public void testNumberMethods () throws Exception {
		Map context = new HashMap();
		context.put("myNumber", new Double("939.4323"));
		assertEquals("940", merge("myNumber?ceiling", context));
		assertEquals("939", merge("myNumber?floor", context));
		assertEquals("939", merge("myNumber?round", context));
		context.put("myNumber", new Double("939.5"));
		assertEquals("940", merge("myNumber?round", context));
	}

	public void testSequenceMethods () throws Exception {
		Map context = new HashMap();
		List l = new ArrayList();
		l.add("abc");
		l.add("def");
		l.add("ghi");
		context.put("myList", l);
		assertEquals("abc", merge("myList?first", context));
		assertEquals("ghi", merge("myList?last", context));
		assertEquals("true", merge("myList?contains('def')", context));
		assertEquals("false", merge("myList?contains('foo')", context));
		Object obj = ZipEngine.getInstance().getEvaluator("myList?length").objectValue(context);
		assertEquals(new Integer(3), obj);
	}

	public void testMapMethods () throws Exception {
		Map context = new HashMap();
		Map map = new HashMap();
		map.put("foo", "foo_value");
		map.put("bar", "bar_value");
		context.put("myMap", map);
		Collection c = (Collection) ZipEngine.getInstance().getEvaluator(
				"myMap?keys").objectValue(context);
		assertEquals(2, c.size());
		assertEquals("foo", c.toArray()[0]);
		assertEquals("bar", c.toArray()[1]);

		c = (Collection) ZipEngine.getInstance().getEvaluator(
				"myMap?values").objectValue(context);
		assertEquals(2, c.size());
		assertEquals("foo_value", c.toArray()[0]);
		assertEquals("bar_value", c.toArray()[1]);
	}

	public void testObjectMethods () throws Exception {
		assertIsMethod("isBoolean", Boolean.TRUE, "not it");
		assertIsMethod("isDate", new Date(), "not it");
		assertIsMethod("isNumber", new Integer(1), "not it");
		assertIsMethod("isString", "this is it", Boolean.TRUE);
		assertIsMethod("isSequence", new Object[1], "not it");
		assertIsMethod("isMap", new HashMap(), "not it");
	}

	private void assertIsMethod(String sm, Object matchVal, Object noMatchVal) throws ParseException {
		Map context = new HashMap();
		context.put("shouldMatch", matchVal);
		context.put("shouldNotMatch", noMatchVal);
		String query = "shouldMatch?" + sm;
		assertEquals(true, ZipEngine.getInstance().getEvaluator(query).booleanValue(context));
		query = "shouldNotMatch?" + sm;
		assertEquals(false, ZipEngine.getInstance().getEvaluator(query).booleanValue(context));
	}

	private String merge (String contents, Object context)
	throws ParseException, ExecutionException, IOException {
		return ZipEngine.getInstance().getEvaluator(
				contents).objectValue(context).toString();
	}
}