package hudson.zipscript.parser.util;

import hudson.zipscript.parser.template.data.ParsingSession;

public class SessionUtil {

	public static boolean getProperty (String name, boolean defaultValue, ParsingSession session) {
		Object obj = session.getParameters().getProperty(name);
		if (null == obj) return defaultValue;
		else if (obj instanceof Boolean) return ((Boolean) obj).booleanValue();
		else if (obj instanceof String) {
			if (obj.toString().equals(Boolean.TRUE.toString()))
				return true;
			else if (obj.toString().equals(Boolean.FALSE.toString()))
				return false;
		}
		return defaultValue;
	}
}
