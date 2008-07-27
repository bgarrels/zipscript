package hudson.zipscript.parser.template.element.directive.initialize;

import java.io.IOException;
import java.io.Writer;

public class NullWriter extends Writer {

	public void close() throws IOException {
	}

	public void flush() throws IOException {
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
	}
}