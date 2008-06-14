package hudson.zipscript.parser.exception;

import hudson.zipscript.parser.template.data.LinePosition;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;


public class ParseException extends Exception {

	public static final int TYPE_EOF = 0;
	public static final int TYPE_UNEXPECTED_CHARACTER = 1;
	public static final int TYPE_MISSING_ELEMENT = 1;

	private long position;
	private ParsingResult parseData;
	private Element element;
	private int type;

	public ParseException (int type, PatternMatcher pattern, int position) {
		this(type, pattern, null, position, null);
	}

	public ParseException (int type, Element element, String message) {
		this(type, null, element, Long.MIN_VALUE, message);
	}

	public ParseException (
			int type, PatternMatcher pattern, Element element, long position, String message) {
		super(message);
		if (position >= 0)
			this.position = position;
		else if (null != element)
			this.position = element.getElementPosition();
		this.type = type;
		this.element = element;
	}

	public void setParsingResult (ParsingResult parsingData) {
		this.parseData = parsingData;
	}

	public String getMessage() {
		if (null == parseData)
			return super.getMessage();
		else {
			LinePosition lp = parseData.getLinePosition(position);
			return "(line " + lp.line + ", position " + lp.position + ") " + super.getMessage();
		}
	}
}