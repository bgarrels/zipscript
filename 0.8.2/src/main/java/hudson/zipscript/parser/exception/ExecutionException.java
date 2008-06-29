package hudson.zipscript.parser.exception;

import hudson.zipscript.parser.template.data.LinePosition;
import hudson.zipscript.parser.template.data.ParsingResult;
import hudson.zipscript.parser.template.element.Element;

public class ExecutionException extends RuntimeException {

	private Element element;
	ParsingResult parsingResult;
	private String resource;

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
		StringBuffer sb = new StringBuffer();
		if (null != resource)
			sb.append ("[" + resource + "] ");
		if (null != element) {
			LinePosition lp = parsingResult.getLinePosition(element.getElementPosition());
			sb.append("(line " + lp.line + ", position " + lp.position + ") ");
		}
		sb.append(super.getMessage());
		return sb.toString();
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public int getLine () {
		if (null == parsingResult) return 0;
		return parsingResult.getLinePosition(element.getElementPosition()).line;
	}

	public int getPosition () {
		if (null == parsingResult) return 0;
		return parsingResult.getLinePosition(element.getElementPosition()).position;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}