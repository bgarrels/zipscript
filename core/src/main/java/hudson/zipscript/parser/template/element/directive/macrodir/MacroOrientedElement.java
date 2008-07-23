package hudson.zipscript.parser.template.element.directive.macrodir;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.Element;

import java.io.Writer;
import java.util.List;

public interface MacroOrientedElement extends Element {

	public String getName();

	public boolean isBodyEmpty ();

	public List getMacroDefinitionAttributes (ExtendedContext context);

	public List getAttributes();

	public String getNestedContent (ExtendedContext context) throws ExecutionException;

	public MacroHeaderElement getHeader();

	public MacroFooterElement getFooter();

	public void writeNestedContent (ExtendedContext context, Writer writer) throws ExecutionException;

	public boolean isOrdinal();

	public boolean isInMacroDefinition();

	public MacroInstanceAttribute getAttribute (String name);
}
