package hudson.zipscript.parser.template.element.directive.whiledir;

import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAware;

import java.io.StringWriter;
import java.util.List;

public class WhileDirective extends NestableElement implements MacroInstanceAware {

	public static final String TOKEN_INDEX = "i";

	public static final int MAX_LOOPS = 5000;
	public Element whileElement;

	public WhileDirective (String contents) throws ParseException {
		parseContents(contents);
	}

	private void parseContents (String s) throws ParseException {
		whileElement = parseElement(s);
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		int i = 0;
		context = new NestedContextWrapper(context);
		context.put(TOKEN_INDEX, new Integer(0));
		while (whileElement.booleanValue(context)) {
			if (i > MAX_LOOPS)
				throw new ExecutionException("Max loops limit reached");
			appendElements(getChildren(), context, sw);
			context.put(TOKEN_INDEX, new Integer(++i));
		}
	}

	public void getMacroInstances(
			ZSContext context, List macroInstanceList) throws ExecutionException {
		int i = 0;
		context = new NestedContextWrapper(context);
		context.put(TOKEN_INDEX, new Integer(0));
		while (whileElement.booleanValue(context)) {
			if (i > MAX_LOOPS)
				throw new ExecutionException("Max loops limit reached");
			appendMacroInstances(getChildren(), context, macroInstanceList);
			context.put(TOKEN_INDEX, new Integer(++i));
		}
	}

	protected boolean isStartElement(Element e) {
		return (e instanceof WhileDirective);
	}

	protected boolean isEndElement(Element e) {
		return (e instanceof EndWhileDirective);
	}

	public String toString() {
		return "[#while " + whileElement.toString() + "]";
	}
}
