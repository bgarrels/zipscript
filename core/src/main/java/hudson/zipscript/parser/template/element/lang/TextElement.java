package hudson.zipscript.parser.template.element.lang;

import hudson.zipscript.parser.ExpressionParser;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.AbstractElement;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.VarDefaultElementPatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.VariablePatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.format.VarFormattingElementPatternMatcher;
import hudson.zipscript.parser.template.element.lang.variable.special.VarSpecialElementPatternMatcher;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;


public class TextElement extends AbstractElement implements Element {

	public static final PatternMatcher[] MATCHERS = new PatternMatcher[] {
		new VariablePatternMatcher(),
		new VarDefaultElementPatternMatcher(),
		new VarSpecialElementPatternMatcher(),
		new VarFormattingElementPatternMatcher()
	};

	private boolean evaluateText;
	private String text;
	private List children;

	public TextElement (String text) {
		this (text, false);
	}

	public TextElement (String text, boolean evaluateText) {
		this.text = text;
		this.evaluateText = evaluateText;
	}

	public Object objectValue(ZSContext context) {
		if (evaluateText) {
			if (getChildren().size() > 1) {
				StringWriter sw = new StringWriter();
				for (Iterator i=getChildren().iterator(); i.hasNext(); ) {
					((Element) i.next()).merge(context, sw);
				}
				return sw.toString();
			}
			else {
				return ((Element) getChildren().get(0)).objectValue(context);
			}
		}
		else {
			return text;
		}
	}

	public boolean booleanValue(ZSContext context) {
		return false;
	}

	public void merge(ZSContext context, StringWriter sw) {
		if (evaluateText) {
			for (Iterator i=getChildren().iterator(); i.hasNext(); ) {
				((Element) i.next()).merge(context, sw);
			}
		}
		else if (null != text) {
			sw.write(text);
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return "'" + text + "'";
	}

	public ElementIndex normalize(
			int index, List elementList, ParsingSession session) throws ParseException {
		if (evaluateText) {
			ParsingResult pr = ExpressionParser.getInstance().parse(text, MATCHERS, TextDefaultElementFactory.INSTANCE,
					session, (int) (getElementPosition() + 1));
			setChildren(pr.getElements());
		}
		return null;
	}

	public String getText() {
		return text;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}
}