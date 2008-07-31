package hudson.zipscript.parser.template.element;

import hudson.zipscript.parser.context.ExtendedContext;

import java.io.Writer;

public interface ToStringWithContextElement {

	public String toString (ExtendedContext context);

	public void append (ExtendedContext context, Writer writer);
}
