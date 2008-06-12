package hudson.zipscript.parser.template.element.directive.calldir;

import hudson.zipscript.ZipEngine;
import hudson.zipscript.parser.context.NestedContextWrapper;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.directive.AbstractDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroAttribute;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceDirective;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceEntity;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceExecutor;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;
import hudson.zipscript.parser.template.element.special.WithElement;
import hudson.zipscript.parser.template.element.special.WithPatternMatcher;

import java.io.StringWriter;
import java.util.List;

public class CallDirective extends AbstractDirective {

	private String macroName;
	MacroDirective macroDirective;
	private Element withElement;

	private static PatternMatcher[] MATCHERS;
	static {
		PatternMatcher[] matchers = ZipEngine.VARIABLE_MATCHERS;
		MATCHERS = new PatternMatcher[matchers.length+1];
		System.arraycopy(matchers, 0, MATCHERS, 1, matchers.length);
		MATCHERS[0] = new WithPatternMatcher();
	}

	public CallDirective (String contents, ParsingSession session, int contentStartPosition) throws ParseException {
		parseContents(contents, session, contentStartPosition);
	}

	private void parseContents (String contents, ParsingSession session, int contentStartPosition) throws ParseException {
		java.util.List elements = parseElements(contents, session, contentStartPosition);
		try {
			if (elements.get(0) instanceof SpecialStringElement) {
				macroName = ((SpecialStringElement) elements.remove(0)).getTokenValue();
				this.macroDirective = session.getMacroDirective(macroName);
			}
			else {
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid sequence.  Expecting variable name");
			}
			if (!(elements.remove(0) instanceof WithElement))
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid sequence.  Expecting 'with'");
			if (elements.size() > 1)
				throw new ParseException(
						ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Invalid sequence.  Improperly formed set expression");
			else
				this.withElement = (Element) elements.get(0);
		}
		catch (IndexOutOfBoundsException e) {
			throw new ParseException(ParseException.TYPE_UNEXPECTED_CHARACTER, this, "Improperly formed set expression: must have at least 3 tokens");
		}
	}

	public void merge(ZSContext context, StringWriter sw)
			throws ExecutionException {
		Object obj = withElement.objectValue(context);
		if (obj instanceof MacroInstanceEntity) {
			MacroInstanceEntity callInput = (MacroInstanceEntity) obj;
			MacroInstanceDirective macroInstance = callInput.getMacroInstance();
			MacroInstanceExecutor executor = new MacroInstanceExecutor(macroInstance, context);
			getMacroDirective(context.getParsingSession()).executeMacro(
					context, macroInstance.isOrdinal(), macroInstance.getAttributes(), executor, sw);
		}
		else {
			throw new ExecutionException("Invalid call: a macro instance must be passed", this);
		}
	}

	protected MacroDirective getMacroDirective (ParsingSession session) {
		if (null == macroDirective) {
			// we might have to lazy load
			macroDirective = session.getMacroDirective(macroName);
		}
		if (null == macroDirective) {
			throw new ExecutionException("Unknown macro '" + macroName + "'", this);
		}
		return macroDirective;
	}

	protected PatternMatcher[] getContentParsingPatternMatchers() {
		return MATCHERS;
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}

	public String toString() {
		return "[#call " + macroName + " with " + withElement + "]";
	}
}