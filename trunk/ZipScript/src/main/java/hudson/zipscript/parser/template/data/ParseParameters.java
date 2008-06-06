package hudson.zipscript.parser.template.data;

public class ParseParameters {

	public boolean cleanWhitespace = true;
	public boolean trim;

	public ParseParameters (boolean cleanWhitespace, boolean trim) {
		this.cleanWhitespace = cleanWhitespace;
		this.trim = trim;
	}
}
