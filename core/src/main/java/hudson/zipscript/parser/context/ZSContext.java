package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.directive.macrodir.MacroDirective;

import java.util.Iterator;
import java.util.Locale;


public interface ZSContext {

	public Object get (String key);

	public Object remove (String key);

	public void put (String key, Object value);

	public Iterator getKeys ();

	public ParsingSession getParsingSession();

	public void setParsingSession (ParsingSession session);

	public Locale getLocale ();
}
