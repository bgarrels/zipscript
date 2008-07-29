package hudson.zipscript.parser.template.element.lang.xml;

import hudson.zipscript.parser.template.element.lang.variable.adapter.ObjectAdapter;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMObjectAdapter implements ObjectAdapter {
	
	public static DOMObjectAdapter INSTANCE = new DOMObjectAdapter();
	
	public boolean appliesTo(Object object) {
		return (object instanceof Document);
	}

	public Object call(
			String key, Object[] parameters, Object object)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public Object get(
			String key, Object object, RetrievalContext retrievalContext) throws ClassCastException {
		Node node = ((Document) object).getDocumentElement();
		if (retrievalContext.is(RetrievalContext.SEQUENCE)) {
			// find node children whose name match the key
			NodeList nl = node.getChildNodes();
			List matchingChildren = new ArrayList();
			for (int i=0; i<nl.getLength(); i++) {
				if (nl.item(i).getNodeName().equals(key))
					matchingChildren.add(nl.item(i));
			}
			return matchingChildren;
		}
		else {
			node = node.getAttributes().getNamedItem(key);
			if (null != node)
				return node.getNodeValue();
			else
				return null;
		}
	}

	public Iterator getProperties(Object object) throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public void set(String key, Object value, Object object)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}
}