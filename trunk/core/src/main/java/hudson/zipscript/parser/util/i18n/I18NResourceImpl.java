package hudson.zipscript.parser.util.i18n;

import java.util.List;

public class I18NResourceImpl implements I18NResource {

	public String get(String key, List parameters) {
		return key;
	}

	public String get(String key, Object parameter) {
		return key;
	}

	public String get(String key, Object[] parameters) {
		return key;
	}

	public String get(String key) {
		return key;
	}
}
