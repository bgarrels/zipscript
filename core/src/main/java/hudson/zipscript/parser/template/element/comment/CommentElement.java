package hudson.zipscript.parser.template.element.comment;

import hudson.zipscript.parser.context.ZSContext;
import hudson.zipscript.parser.exception.ExecutionException;
import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;

import java.io.StringWriter;
import java.util.List;

public class CommentElement implements Element {

	private static CommentElement instance = new CommentElement();
	public static CommentElement getInstance () {
		return instance;
	}

	public boolean booleanValue(ZSContext context) throws ExecutionException {
		return false;
	}

	public int getElementLength() {
		return 0;
	}

	public long getElementPosition() {
		return 0;
	}

	public ElementIndex normalize(int index, List elementList,
			ParsingSession session) throws ParseException {
		return null;
	}

	public void merge(ZSContext context, StringWriter sw)
			throws ExecutionException {
	}

	public Object objectValue(ZSContext context) throws ExecutionException {
		return null;
	}

	public void setElementLength(int length) {
	}

	public void setElementPosition(long position) {
	}

	public List getChildren() {
		return null;
	}
}