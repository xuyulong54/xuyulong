package wusc.edu.pay.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author healy
 * 
 */
public class OrderedProperties extends Properties {

	private transient Map mapDelegate = null;
	private transient List listDelegate = null;

	@Override
	@SuppressWarnings("unchecked")
	public synchronized Object put(Object key, Object value) {
		if (mapDelegate != null) {
			return mapDelegate.put(key, value);
		} else if (listDelegate != null) {
			listDelegate.add(new SimpleEntry(key, value));
			return value;
		}
		return super.put(key, value);
	}

	public void loadMap(InputStream in, Map map) throws IOException {
		if (map instanceof Properties) {
			((Properties) map).load(in);
		} else if (map != null) {
			synchronized (this) {
				Map prev = mapDelegate;
				try {
					mapDelegate = map;
					load(in);
				} finally {
					mapDelegate = prev;
				}
			}
		}
	}

	public void loadList(InputStream in, List list) throws IOException {
		if (list instanceof Properties) {
			((Properties) list).load(in);
		} else if (list != null) {
			synchronized (this) {
				List prev = listDelegate;
				try {
					listDelegate = list;
					load(in);
				} finally {
					listDelegate = prev;
				}
			}
		}
	}
}
