package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.resource.macrolib.MacroManager;

public interface Evaluator {

	/**
	 * Return a boolean value that relates to the expression contained with this evaluator
	 * @param context the context
	 * @throws ExecutionException
	 */
	public boolean booleanValue (Object context) throws ExecutionException;

	/**
	 * Return the object value that relates to the expression contained with this evaluator
	 * @param context the context
	 * @throws ExecutionException
	 */
	public Object objectValue (Object context) throws ExecutionException;

	/**
	 * Set the macro manager used to retrieve all imported macro libraries
	 * @param macroManager the macro manager
	 */
	public void setMacroManager (MacroManager macroManager);

	/**
	 * Return the macro manager
	 * @return the macro manager
	 */
	public MacroManager getMacroManager ();
}