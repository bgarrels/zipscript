package hudson.zipscript.parser.template.element;

import java.io.Writer;

import hudson.zipscript.parser.context.ZSContext;

public interface ToStringWithContextElement {

	public String toString (ZSContext context);

	public void toString (ZSContext context, Writer writer);
}
