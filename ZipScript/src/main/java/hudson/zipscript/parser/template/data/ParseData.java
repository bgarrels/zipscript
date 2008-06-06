package hudson.zipscript.parser.template.data;

import java.util.List;

public class ParseData {

	private List elements;
	private long[] lineBreaks;

	public ParseData (List elements, long[] lineBreaks) {
		this.elements = elements;
		this.lineBreaks = lineBreaks;
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
}
