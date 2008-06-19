package hudson.zipscript.template;

import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.resource.macrolib.MacroManager;

import java.io.StringWriter;

public interface Template {

	public String merge (Object context) throws ExecutionException;

	public void merge (Object context, StringWriter writer) throws ExecutionException;

	public void setMacroManager (MacroManager macroManager);

	public MacroManager getMacroManager ();
}
