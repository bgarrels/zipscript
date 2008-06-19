package hudson.zipscript.parser.template.element.directive.whiledir;

import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAware;

import java.io.StringWriter;
import java.util.List;

public class WhileDirective extends NestableElement implements MacroInstanceAware {

	public static final String TOKEN_INDEX = "i";

	public static final int MAX_LOOPS = 5000;
	public Element whileElement;

	public WhileDirective (String contents, int contentIndex, ParsingSession parsingSession)
	throws ParseException {
		parseContents(contents, contentIndex, parsingSession);
	}

	private void parseContents (
			String s, int contentIndex, ParsingSession parsingSession)
	throws ParseException {
		whileElement = parseElement(s, contentIndex, parsingSession);
	}

	public void merge(ZSContext context, StringWriter sw) throws ExecutionException {
		int i = 0;
		context = new NestedContextWrapper(context);
		context.put(TOKEN_INDEX, new Integer(0));
		while (whileElement.booleanValue(context)) {
			if (i > MAX_LOOPS)
				throw new ExecutionException("Max loops limit reached", this);
			appendElements(getChildren(), context, sw);
			context.put(TOKEN_INDEX, new Integer(++i));
		}
	}

	public void getMacroInstances(
			ZSContext context, List macroInstanceList, MacroDirective macro) throws ExecutionException {
		int i = 0;
		context = new NestedContextWrapper(context);
		context.put(TOKEN_INDEX, new Integer(0));
		while (whileElement.booleanValue(context)) {
			if (i > MAX_LOOPS)
				throw new ExecutionException("Max loops limit reached", this);
			appendMacroInstances(getChildren(), context, macroInstanceList, macro);
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
