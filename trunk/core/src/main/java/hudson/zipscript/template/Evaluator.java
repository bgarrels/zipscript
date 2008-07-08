package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;

import java.util.Locale;

public interface Evaluator {

	/**
	 * Return a boolean value that relates to the expression contained with this evaluator
	 * @param context the context
	 * @throws ExecutionException
	 */
	public boolean booleanValue (Object context) throws ExecutionException;

	/**
	 * Return a boolean value that relates to the expression contained with this evaluator
	 * @param context the context
	 * @param locale the locale
	 * @throws ExecutionException
	 */
	public boolean booleanValue (Object context, Locale locale) throws ExecutionException;

	/**
	 * Return the object value that relates to the expression contained with this evaluator
	 * @param context the context
	 * @throws ExecutionException
	 */
	public Object objectValue (Object context) throws ExecutionException;

	/**
	 * Return the object value that relates to the expression contained with this evaluator
	 * @param context the context
	 * @param locale the locale
	 * @throws ExecutionException
	 */
	public Object objectValue (Object context, Locale locale) throws ExecutionException;
}