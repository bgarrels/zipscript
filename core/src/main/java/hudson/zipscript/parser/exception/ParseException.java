package hudson.zipscript.parser.exception;

import hudson.zipscript.parser.template.data.LinePosition;
import hudson.zipscript.parser.template.data.ParseData;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.PatternMatcher;


public class ParseException extends Exception {

	public static final int TYPE_EOF = 0;
	public static final int TYPE_UNEXPECTED_CHARACTER = 1;

	private int position;
	private ParseData parseData;

	public ParseException (int type, PatternMatcher pattern, int position) {
		this.position = position;
	}

	public ParseException (int type, Element element, String message) {
		super(message);
	}

	public ParseException (int type, PatternMatcher pattern, Element element, int position) {
		super(element.toString());
		this.position = position;
	}

	public void setParsingData (ParseData parsingData) {
		this.parseData = parseData;
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