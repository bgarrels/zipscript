package hudson.zipscript.parser.exception;

import hudson.zipscript.parser.template.data.LinePosition;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.element.Element;


public class ParseException extends Exception {

	private long absolutePosition;
	private int length;
	private ParsingResult parseData;
	private Element element;

	public ParseException (long position, String message) {
		this(null, position, Integer.MIN_VALUE, message);
	}

	public ParseException (long position, int length, String message) {
		this(null, position, length, message);
	}

	public ParseException (Element element, String message) {
		this(element, Long.MIN_VALUE, Integer.MIN_VALUE, message);
	}

	public ParseException (Element element, long position,
			int length, String message) {
		super(message);
		if (position >= 0)
			this.absolutePosition = position;
		else if (null != element)
			this.absolutePosition = element.getElementPosition();
		if (length > 0)
			this.length = length;
		else if (null != this.element)
			this.length = element.getElementLength();
		this.element = element;
	}

	public void setParsingResult (ParsingResult parsingData) {
		this.parseData = parsingData;
	}

	public String getMessage() {
		if (null == parseData)
			return super.getMessage();
		else {
			LinePosition lp = parseData.getLinePosition(absolutePosition);
			return "(line " + lp.line + ", position " + lp.position + ") " + super.getMessage();
		}
	}

	public int getLine () {
		if (null == parseData) return 0;
		return parseData.getLinePosition(absolutePosition).line;
	}

	public int getPosition () {
		if (null == parseData) return 0;
		return parseData.getLinePosition(absolutePosition).position;
	}
}