package hudson.zipscript.parser.template.element.directive.whiledir;

import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.NestableElement;
import hudson.zipscript.parser.template.element.directive.LoopingDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceAware;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WhileDirective extends NestableElement implements MacroInstanceAware, LoopingDirective {

	public static final String TOKEN_INDEX = "i";
//	public static final int MAX_LOOPS = 200000;

	private Element whileElement;
	private boolean isInMacroDefinition;

	public WhileDirective (String contents, int contentIndex, ParsingSession parsingSession)
	throws ParseException {
		parseContents(contents, contentIndex, parsingSession);
	}

	private void parseContents (
			String s, int contentIndex, ParsingSession session)
	throws ParseException {
		// see if we are in a macro definition
		for (Iterator i=session.getNestingStack().iterator(); i.hasNext(); ) {
			if (i.next() instanceof MacroDirective) {
				isInMacroDefinition = true;
				break;
			}
		}

		whileElement = parseElement(s, contentIndex, session);
	}

	public void merge(ZSContext context, Writer sw) throws ExecutionException {
		int i = 0;
		context = new NestedContextWrapper(context, this);
		context.put(TOKEN_INDEX, new Integer(0));
		while (whileElement.booleanValue(context)) {
//			if (i > MAX_LOOPS)
//				throw new ExecutionException("Max loops limit reached", this);
			appendElements(getChildren(), context, sw);
			context.put(TOKEN_INDEX, new Integer(++i));
		}
	}

	public void getMacroInstances(
			ZSContext context, List macroInstanceList, MacroDirective macro, Map additionalContextEntries) throws ExecutionException {
		int i = 0;
		context = new NestedContextWrapper(context, this);
		Integer int0 = new Integer(0);
		additionalContextEntries.put(TOKEN_INDEX, int0);
		context.put(TOKEN_INDEX, int0);
		while (whileElement.booleanValue(context)) {
//			if (i > MAX_LOOPS)
//				throw new ExecutionException("Max loops limit reached", this);
			appendMacroInstances(getChildren(), context, macroInstanceList, macro, additionalContextEntries);
			Integer index = new Integer(++i);
			additionalContextEntries.put(TOKEN_INDEX, index);
			context.put(TOKEN_INDEX, index);
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

	public boolean isInMacroDefinition() {
		return isInMacroDefinition;
	}
}
