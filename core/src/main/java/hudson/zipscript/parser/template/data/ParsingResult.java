package hudson.zipscript.parser.template.data;

import java.util.List;

public class ParsingResult {

	private List elements;
	private long[] lineBreaks;
	private ParsingSession parsingSession;

	public ParsingResult (List elements, long[] lineBreaks, ParsingSession parsingSession) {
		this.elements = elements;
		this.lineBreaks = lineBreaks;
		this.parsingSession = parsingSession;
	}

	public List getElements() {
		return elements;
	}

	public long[] getLineBreaks() {
		return lineBreaks;
	}

	public LinePosition getLinePosition (int position) {
		int lineCount = 0;
		long lineBreakPosition = 0;
		for (int i=0; lineBreaks.length > i; i++) {
			if (lineBreaks[i] < position)
				lineBreakPosition = lineBreaks[i];
			else
				break;
			lineCount ++;
		}
		return new LinePosition(lineCount, (int) (position - lineBreakPosition));
	}

	public ParsingSession getParsingSession() {
		return parsingSession;
	}
}