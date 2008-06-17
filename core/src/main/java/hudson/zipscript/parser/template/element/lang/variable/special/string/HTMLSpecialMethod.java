package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class HTMLSpecialMethod extends AbstractReplacementMethod {

	public static final HTMLSpecialMethod INSTANCE = new HTMLSpecialMethod();

	protected char[] getCharsToReplace() {
		return new char[] {'<', '>', '&', '\"'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"&lt;", "&gt;", "&amp;", "&quot;"};
	}
}