package hudson.zipscript.parser.util;

import hudson.zipscript.parser.exception.ParseException;
import hudson.zipscript.parser.template.data.ElementIndex;
import hudson.zipscript.parser.template.data.ParseParameters;
import hudson.zipscript.parser.template.data.ParsingSession;
import hudson.zipscript.parser.template.element.Element;
import hudson.zipscript.parser.template.element.lang.WhitespaceElement;

import java.util.List;

public class ElementNormalizer {

	public static void normalize (
			List elements, ParsingSession session, boolean topLevel) throws ParseException {
		Element e = null;
		ElementIndex ei = null;
		for (int i=0; i<elements.size(); i++) {
			e = (Element) elements.remove(i);
			ei = e.normalize(i, elements, session);
			if (null == ei) {
				elements.add(i, e);
			}
			else {
				i = ei.getIndex();
				elements.add(i, ei.getElement());
			}
		}

		if (session.getParameters().cleanWhitespace) {
			// remove white spaces
			for (int i=0; i<elements.size(); i++) {
				if (elements.get(i) instanceof WhitespaceElement) {
					elements.remove(i);
					i --;
				}
			}
		}
		else if (session.getParameters().trim) {
			trim(elements);
		}
	}

	public static void trim (List elements) {
		while (elements.size() > 0 && elements.get(0) instanceof WhitespaceElement)
			elements.remove(0);
		while (elements.size() > 0 && elements.get(elements.size()-1) instanceof WhitespaceElement)
			elements.remove(elements.size()-1);
	}
}
