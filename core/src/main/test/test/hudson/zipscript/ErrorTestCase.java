package test.hudson.zipscript;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.LinePosition;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.resource.StringResourceLoader;
import hudson.zipscript.template.Template;
import hudson.zipscript.template.TemplateImpl;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

public class ErrorTestCase extends TestCase {

	public ErrorTestCase () {}

	public ErrorTestCase (String name) {
		super(name);
	}
	
//	public void _testTopLevelDirectives () throws Exception {
//		TemplateImpl t = null;
//		ParsingResult pr = null;
//		long[] arr = null;
//		
//		evalResult("top_level_directive.zs", 3, 0);
//	}

	public void testInvalidVariables () {
		evalResult("variable1.zs", 2, 7, false);
		evalResult("variable2.zs", 2, 7, false);
		evalResult("variable3.zs", 2, 7, false);
		evalResult("variable4.zs", 2, 7, true);
	}

	public void testWhileDirective () {
		evalResult("while1.zs", 7, 13, false);
	}

	public void testForeachDirective () {
		evalResult("foreach1.zs", 6, 15, false);
		evalResult("foreach2.zs", 6, 11, false);
	}

	public void testIfDirective () {
		evalResult("if1.zs", 7, 0, false);
		evalResult("if2.zs", 9, 0, false);
	}

	private void evalResult (String mergeTemplate, int line, int position, boolean showError) {
		try {
			Template t = ZipEngine.getInstance().getTemplate("templates/error/" + mergeTemplate);
			t.merge(null);
		}
		catch (ParseException e) {
			if (showError) System.out.println(e.getMessage());
			assertEquals(line, e.getLine());
			assertEquals(position, e.getPosition());
			return;
		}
		catch (ExecutionException e) {
			if (showError) System.out.println(e.getMessage());
			assertEquals(line, e.getLine());
			assertEquals(position, e.getPosition());
			return;
		}
		assertTrue("The expected exception was not thrown", false);
	}

//	private void printChildInfo (Element e, ParsingResult pr, String prefix) {
//		LinePosition lp = pr.getLinePosition(e.getElementPosition());
//		System.out.println(prefix + e + " (Line: " + lp.line + ", Pos: " + lp.position + " - " + e.getElementPosition() + ")");
//		if (null != e.getChildren()) {
//			for (int i=0; i<e.getChildren().size(); i++) {
//				printChildInfo((Element) e.getChildren().get(i), pr, prefix + "   ");
//			}
//		}
//	}
}