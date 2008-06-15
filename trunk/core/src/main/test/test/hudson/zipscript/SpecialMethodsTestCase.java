package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class SpecialMethodsTestCase extends TestCase {

	public void testStringMethods () throws Exception {
		Map context = new HashMap();
		context.put("str", "test");
		assertEquals("Test", merge("str?upperFirst", context));
		assertEquals("TEST", merge("str?upperCase", context));
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

	private String merge (String contents, Object context)
	throws ParseException, ExecutionException, IOException {
		return ZipEngine.getInstance().getTemplateForEvaluation(
				contents).objectValue(context).toString();
	}
}