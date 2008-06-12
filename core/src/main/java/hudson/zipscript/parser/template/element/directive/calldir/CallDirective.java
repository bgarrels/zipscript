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
import hudson.zipscript.parser.template.element.directive.macrodir.MacroInstanceEntity;
import hudson.zipscript.parser.template.element.special.SpecialStringElement;
import hudson.zipscript.parser.template.element.special.WithElement;

import java.io.StringWriter;
import java.util.List;

public class CallDirective extends AbstractDirective {

	private String macroNamespace;
	private String macroName;
	private Element withElement;

	private static PatternMatcher[] MATCHERS;
	static {
		PatternMatcher[] matchers = ZipEngine.VARIABLE_MATCHERS;
		MATCHERS = new PatternMatcher[matchers.length+1];
		System.arraycopy(matchers, 0, MATCHERS, 1, matchers.length);
		MATCHERS[0] = new CallPatternMatcher();
	}

	public CallDirective (String contents, ParsingSession session) throws ParseException {
		parseContents(contents, session);
	}

	private void parseContents (String contents, ParsingSession session) throws ParseException {
		java.util.List elements = parseElements(contents, session);
		try {
			if (elements.get(0) instanceof SpecialStringElement) {
				this.macroName = ((SpecialStringElement) elements.remove(0)).getTokenValue();
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
			// this is good
			MacroInstanceEntity callParent = (MacroInstanceEntity) obj;
			// find the macro to be called
			MacroDirective macro = context.getParsingSession().getMacroDirective(macroName);
			if (null != macro) {
				ZSContext subContext = new NestedContextWrapper(context);
				for (int i=0; i<macro.getAttributes().size(); i++) {
					MacroAttribute attribute = (MacroAttribute) macro.getAttributes().get(i);
					Object val = callParent.get(attribute.getName());
					if (null != val) subContext.put(attribute.getName(), val);
					macro.merge(subContext, sw);
				}
			}
			else {
				throw new ExecutionException("Invalid call: unknown macro '" + macroName + "'");
			}
		}
		else {
			throw new ExecutionException("Invalid call: a macro instance must be passed");
		}
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}
}