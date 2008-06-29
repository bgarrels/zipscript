package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.resource.macrolib.MacroManager;

public interface Evaluator {

	public boolean booleanValue (Object context) throws ExecutionException;

	public Object objectValue (Object context) throws ExecutionException;

	public void setMacroManager (MacroManager macroManager);

	public MacroManager getMacroManager ();
}