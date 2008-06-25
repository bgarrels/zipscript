package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.io.Writer;

public interface Template {

	public String merge (Object context) throws ExecutionException;

	public void merge (Object context, Writer writer) throws ExecutionException;

	public void setMacroManager (MacroManager macroManager);

	public MacroManager getMacroManager ();
}
