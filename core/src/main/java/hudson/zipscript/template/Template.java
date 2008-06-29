package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.io.Writer;

public interface Template {

	/**
	 * Merge this template with the data provided with the context.  The context can be any typee of object.
	 * @param context the context
	 * @return a String containing the merged result
	 * @throws ExecutionException
	 */
	public String merge (Object context) throws ExecutionException;

	/**
	 * Merge this template with the data provided with the context.  Used the writer to append the string results.
	 * @param context the context
	 * @param writer the writer
	 * @throws ExecutionException
	 */
	public void merge (Object context, Writer writer) throws ExecutionException;

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