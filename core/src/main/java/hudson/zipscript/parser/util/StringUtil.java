package hudson.zipscript.parser.util;

public class StringUtil {

	public static String firstLetterUpperCase (String s) {
		if (null == s|| s.length() == 1) return s;
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toUpperCase(s.charAt(0)));
		for (int i=1; i<s.length(); i++)
			sb.append(s.charAt(i));
		return sb.toString();
	}

	public static String firstLetterLowerCase (String s) {
		if (null == s|| s.length() == 1) return s;
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toLowerCase(s.charAt(0)));
		for (int i=1; i<s.length(); i++)
			sb.append(s.charAt(i));
		return sb.toString();
	}
}
