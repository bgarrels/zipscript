package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;

import java.io.Writer;
import java.util.List;


public interface Element {

	public void setElementPosition (long position);

	public long getElementPosition ();

	public void setElementLength (int length);

	public int getElementLength ();

	public Object objectValue (ZSContext context) throws ExecutionException;

	public boolean booleanValue (ZSContext context) throws ExecutionException;

	public void merge (ZSContext context, Writer sw) throws ExecutionException;

	public ElementIndex normalize (
			int index, List elementList, ParsingSession session) throws ParseException;

	public List getChildren();
}