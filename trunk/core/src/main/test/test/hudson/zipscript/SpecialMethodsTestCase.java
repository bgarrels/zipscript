package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		String[] arr = (String[]) ZipEngine.getInstance().getTemplateForEvaluation(
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
	}

	public void testMapMethods () throws Exception {
		Map context = new HashMap();
		Map map = new HashMap();
		map.put("foo", "foo_value");
		map.put("bar", "bar_value");
		context.put("myMap", map);
		Collection c = (Collection) ZipEngine.getInstance().getTemplateForEvaluation(
				"myMap?keys").objectValue(context);
		assertEquals(2, c.size());
		assertEquals("foo", c.toArray()[0]);
		assertEquals("bar", c.toArray()[1]);

		c = (Collection) ZipEngine.getInstance().getTemplateForEvaluation(
				"myMap?values").objectValue(context);
		assertEquals(2, c.size());
		assertEquals("foo_value", c.toArray()[0]);
		assertEquals("bar_value", c.toArray()[1]);
	}

	private String merge (String contents, Object context)
	throws ParseException, ExecutionException, IOException {
		return ZipEngine.getInstance().getTemplateForEvaluation(
				contents).objectValue(context).toString();
	}
}