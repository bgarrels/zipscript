package hudson.zipscript.parser.template.element.lang.xml;

import java.util.Collection;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import hudson.zipscript.parser.template.element.lang.variable.adapter.HashAdapter;
import hudson.zipscript.parser.template.element.lang.variable.adapter.RetrievalContext;

public class NodeHashAdapter extends NodeObjectAdapter implements HashAdapter {

	public static NodeHashAdapter INSTANCE = new NodeHashAdapter();

	public Object get(Object key, Object map,
			RetrievalContext retrievalContext, String contextHint)
			throws ClassCastException {
		if (key instanceof Number) {
			// get a sequence entry
			Node node = getNode(map);
			String name = node.getNodeName();
			Node parent = node.getParentNode();
			int count = 0;
			int rtnIndex = ((Number) key).intValue();
			NodeList nl = parent.getChildNodes();
			for (int i=0; i<nl.getLength(); i++) {
				if (nl.item(i).getNodeName().equals(name)) {
					if (count == rtnIndex) {
						return nl.item(i);
					}
					else {
						count ++;
					}
				}
			}
			return null;
		}
		else {
			return super.get(key.toString(), map, retrievalContext, contextHint);
		}
	}

	public Set getKeys(Object map) throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public Collection getValues(Object map) throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public void put(Object key, Object value, Object map)
			throws ClassCastException {
		throw new UnsupportedOperationException();
	}

	public Object remove(Object key, Object map) throws ClassCastException {
		throw new UnsupportedOperationException();
	}
}