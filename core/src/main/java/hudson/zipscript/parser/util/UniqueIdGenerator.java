package hudson.zipscript.parser.util;

import hudson.zipscript.parser.Configurable;
import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;

public interface UniqueIdGenerator extends ToStringWithContextElement, Configurable {

	public String toString(ZSContext context);
}
