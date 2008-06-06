package hudson.zipscript.parser.template.element.comparator.math;

import java.math.BigDecimal;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.template.element.comparator.AbstractComparatorElement;

public abstract class AbstractMathExpression extends AbstractComparatorElement {

	protected boolean compare(Object lhs, Object rhs) {
		return false;
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		Object lhs = getLeftHandSide().objectValue(context);
		Object rhs = getRightHandSide().objectValue(context);
		if (null == lhs) return rhs;
		if (null == rhs) return lhs;
		if (!(lhs instanceof Number) || !(rhs instanceof Number)) {
			throw new ExecutionException("Invalid math operator value");
		}
		return performOperation ((Number) lhs, (Number) rhs);
	}

	protected abstract Object performOperation (Number lhs, Number rhs);

	public int getPriority() {
		return 2;
	}

	protected Class getCommonDenominatorClass (Number lhs, Number rhs) {
		if (lhs instanceof Double
				|| rhs instanceof Double) return Double.class;
		else if (lhs instanceof Float
				|| rhs instanceof Float) return Float.class;
		else if (lhs instanceof BigDecimal
				|| rhs instanceof BigDecimal) return BigDecimal.class;
		else if (lhs instanceof Long
				|| rhs instanceof Long) return Long.class;
		else if (lhs instanceof Short
				|| rhs instanceof Short) return Short.class;
		else return Integer.class;
	}
}
