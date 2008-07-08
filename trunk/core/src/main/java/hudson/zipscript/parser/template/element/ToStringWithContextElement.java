package hudson.zipscript.parser.template.element;

import java.io.Writer;

import hudson.zipscript.parser.context.ExtendedContext;

public interface ToStringWithContextElement {

	public String toString (ExtendedContext context);

	public void append (ExtendedContext context, Writer writer);
}
