package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class RTFSpecialMethod extends AbstractReplacementMethod {

	public static final RTFSpecialMethod INSTANCE = new RTFSpecialMethod();

	protected char[] getCharsToReplace() {
		return new char[] {'\\', '{', '}'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"\\\\", "\\{", "\\}"};
	}
}