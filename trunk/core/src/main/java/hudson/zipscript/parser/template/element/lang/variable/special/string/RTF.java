package hudson.zipscript.parser.template.element.lang.variable.special.string;


public class RTF extends AbstractReplacementMethod {

	public static final RTF INSTANCE = new RTF();

	protected char[] getCharsToReplace() {
		return new char[] {'\\', '{', '}'};
	}

	protected String[] getReplacementStrings() {
		return new String[] {
			"\\\\", "\\{", "\\}"};
	}
}