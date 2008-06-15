package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class HTML extends AbstractReplacementMethod {

	public static final HTML INSTANCE = new HTML();

	protected char[] getCharsToReplace() {
		return new char[] {'<', '>', '&', '\"'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"&lt;", "&gt;", "&amp;", "&quot;"};
	}
}