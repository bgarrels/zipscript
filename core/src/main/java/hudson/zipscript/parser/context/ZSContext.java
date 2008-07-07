package hudson.zipscript.parser.context;

import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The context interface used for all variables to retrieve supplied business data
 * 
 * @author Joe Hudson
 */

public interface ZSContext {

	/**
	 * Return the object matching the key
	 * @param key the key name
	 */
	public Object get (Object key);

	/**
	 * Remove a value from the context matching the key
	 * @param key the key name
	 */
	public Object remove (Object key);

	/**
	 * Put a value in the context
	 * @param key the key name
	 * @param value the value
	 * @param if we can travel up, should we?
	 */
	public void put (Object key, Object value, boolean travelUp);

	/**
	 * Put a value in the context using the global namespace
	 * @param key the key name
	 * @param value the value
	 */
	public void putGlobal (Object key, Object value);

	/**
	 * Return all keys for this scoped context
	 */
	public Iterator getKeys ();

	/**
	 * Return the parsing session which is used by evaluators
	 */
	public ParsingSession getParsingSession();

	/**
	 * Set the parsing session which is used by evaluators
	 * @param session
	 */
	public void setParsingSession (ParsingSession session);

	/**
	 * Return the locale
	 */
	public Locale getLocale ();

	/**
	 * Return the macro manager
	 */
	public MacroManager getMacroManager ();

	/**
	 * Set the macro manager
	 * @param macroManager the macro manager
	 */
	public void setMacroManager (MacroManager macroManager);

	/**
	 * Return the root context (AKA the global context)
	 */
	public ZSContext getRootContext ();

	/**
	 * Append all nested macro attributes within this context
	 * @param m the map to add the elements to using the macro name as the map key
	 */
	public void appendMacroNestedAttributes (Map m);

	/**
	 * Return all elements in the current execution scope
	 * @return
	 */
	public void addToElementScope(List nestingStack);
}