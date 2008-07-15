package hudson.zipscript.parser.exception;

public class UnknownPropertyException extends ExecutionException {
	private static final long serialVersionUID = -1363961609103512907L;

	private String propertyName;
	private Object caller;
	
	public UnknownPropertyException (String propertyName, Object caller) {
		super(null, null);
		this.propertyName = propertyName;
		this.caller = caller;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getCaller() {
		return caller;
	}

	public void setCaller(Object caller) {
		this.caller = caller;
	}
}