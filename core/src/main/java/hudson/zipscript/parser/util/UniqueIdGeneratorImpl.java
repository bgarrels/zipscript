package hudson.zipscript.parser.util;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.InitializationException;

import java.util.Map;

public class UniqueIdGeneratorImpl implements UniqueIdGenerator {

	private long uniqueId = Long.MIN_VALUE;

	public String toString(ZSContext context) {
		if (Long.MIN_VALUE == uniqueId) {
			uniqueId = System.currentTimeMillis();
		}
		else {
			uniqueId = uniqueId ++;
		}
		return Long.toString(uniqueId);
	}

	public String toString() {
		return toString(null);
	}

	public void configure(Map properties) throws InitializationException {	
	}
}