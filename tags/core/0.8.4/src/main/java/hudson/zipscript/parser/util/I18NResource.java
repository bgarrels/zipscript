package hudson.zipscript.parser.util;

import java.util.List;

public interface I18NResource {

	/**
	 * Return the resource associated with the given message key
	 * @param key the resource key
	 */
	public String get(String key);

	/**
	 * Return the resource associated with the given message key
	 * @param key the resource key
	 * @param parameter a single message parameter
	 */
	public String get(String key, Object parameter);

	/**
	 * Return the resource associated with the given message key
	 * @param key the resource key 
	 * @param parameters all parameters
	 */
	public String get(String key, Object[] parameters);

	/**
	 * Return the resource associated with the given message key
	 * @param key the resource key 
	 * @param parameters all parameters
	 */
	public String get(String key, List parameters);
}
