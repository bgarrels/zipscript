package hudson.zipscript.parser.util;

import hudson.zipscript.parser.context.ExtendedContext;
import hudson.zipscript.parser.exception.InitializationException;

import java.io.Writer;
import java.util.Map;

public class UniqueIdGeneratorImpl implements UniqueIdGenerator {

	private long uniqueId = Long.MIN_VALUE;

	public String toString(ExtendedContext context) {
		if (Long.MIN_VALUE == uniqueId) {
			uniqueId = System.currentTimeMillis();
		}
		else {
			uniqueId = uniqueId ++;
		}
		return Long.toString(uniqueId);
	}

	public void append(ExtendedContext context, Writer writer) {
		StringUtil.append(toString(context), writer);
	}

	public String toString() {
		return toString(null);
	}

	public void configure(Map properties) throws InitializationException {	
	}
}