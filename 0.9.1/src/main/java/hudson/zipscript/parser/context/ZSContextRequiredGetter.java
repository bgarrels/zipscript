package hudson.zipscript.parser.context;

public interface ZSContextRequiredGetter {

	public Object get(String key, ExtendedContext context);
}
