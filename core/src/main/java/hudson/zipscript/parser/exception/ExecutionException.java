package hudson.zipscript.parser.exception;

import hudson.zipscript.parser.template.data.LinePosition;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.element.Element;

public class ExecutionException extends RuntimeException {

	private Element element;
	ParsingResult parsingResult;

	public ExecutionException(
			String message, Element element) {
		super(message);
		this.element = element;
	}

	public ExecutionException(
			String message, Element element, Exception thrownException) {
		super(message, thrownException);
		this.element = element;
	}

	public void setParsingResult (ParsingResult parsingResult) {
		this.parsingResult = parsingResult;
	}

	public String getMessage() {
		if (null == parsingResult)
			return super.getMessage();
		else {
			LinePosition lp = parsingResult.getLinePosition(element.getElementPosition());
			return "(line " + lp.line + ", position " + lp.position + ") " + super.getMessage();
		}
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
}
