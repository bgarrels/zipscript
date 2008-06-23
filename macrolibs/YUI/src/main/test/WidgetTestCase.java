import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.util.IOUtil;
import hudson.zipscript.template.Template;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Person;

import junit.framework.TestCase;


public class WidgetTestCase extends TestCase {

	public void testDataTableWidget () throws Exception {
		Map context = new HashMap();
		List l = new ArrayList();
		l.add(new Person("Bill", "Cosby", "12/03/95", 8, new BigDecimal(1429978.76)));
		l.add(new Person("Bill", "Clinton", "12/03/89", 4, new BigDecimal(2875635.21)));
		l.add(new Person("Bill", "Bixby", "3/29/75", 3, new BigDecimal(192879.78)));
		l.add(new Person("Clark", "Kent", "5/11/82", 1, new BigDecimal(43987.19)));
		context.put("people", l);
		String mergeTemplate = "dataTable.zs";
		String resultFile = "/dataTable_result.html";
		evalResult(mergeTemplate, resultFile, context);
	}

	public void testTabWidget () throws Exception {
		Map context = new HashMap();
		String mergeTemplate = "tab.zs";
		String resultFile = "/tab_result.html";
		evalResult(mergeTemplate, resultFile, context);
	}

	public void testTreeWidget () throws Exception {
		Map context = new HashMap();
		String mergeTemplate = "tree.zs";
		String resultFile = "/tree_result.html";
		evalResult(mergeTemplate, resultFile, context);
	}

	private static ZipEngine engine = null;
	static {
		try {
			engine = new ZipEngine();
			engine.addMacroLibrary("data", "data.zsm");
			engine.addMacroLibrary("tab", "tab.zsm");
			engine.addMacroLibrary("tree", "tree.zsm");
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private Template layoutTemplate;

	private void evalResult (String mergeTemplate, String resultFile, Map context)
	throws ParseException, ExecutionException, IOException {
		context.put("addImportHeaders", Boolean.TRUE);
		context.put("cssIncludes", new HashMap(3));
		context.put("scriptIncludes", new HashMap(3));
		String body = merge(mergeTemplate, context);
		if (null == layoutTemplate)
			layoutTemplate = engine.getTemplate("layout.zs");
		context.put("screen_placeholder", body);
		String actualResult = layoutTemplate.merge(context);		
		String expectedResult = IOUtil.toString(getClass().getResourceAsStream(resultFile));
		assertEquals(expectedResult, actualResult);
	}

	private String merge (String template, Object context)
	throws ParseException, ExecutionException, IOException {
		return engine.getTemplate(template).merge(context);
	}
}
