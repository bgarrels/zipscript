package hudson.zipscript.parser.util;

import hudson.zipscript.parser.Configurable;
import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.template.element.ToStringWithContextElement;

public interface UniqueIdGenerator extends ToStringWithContextElement, Configurable {

	public String toString(ExtendedContext context);
}
