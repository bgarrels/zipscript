package hudson.zipscript.parser.util.translator;

import java.util.List;
import java.util.Locale;

import hudson.zipscript.parser.Configurable;

public interface Translator extends Configurable {

	public List translate (List elementsOrText, Locale to) throws Exception;

	public String getBaseLocaleKey ();
}
