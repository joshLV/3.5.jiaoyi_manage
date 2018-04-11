package cn.juhaowan.util;

import java.util.Map;

public class XProperty implements Map.Entry<String, Object>, Comparable<XProperty> {
	String key;
	Object val;
	int index = -1;	//如果不在XObject中，也即是游离状态的XProperty，将会是-1

	public XProperty(String key, Object val) {
		this.key = key;
		this.val = val;
	}
	/*提供给XObject使用的构造函数*/
	XProperty(String key, Object val, int index) {
		this.key = key;
		this.val = val;
		this.index = index;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Object getValue() {
		return val;
	}

	@Override
	public Object setValue(Object value) {
		Object old = getValue();
		this.val = value;
		return old;
	}
	
	@Override
	public String toString() {
		return getKey() + "=>" + getValue();
	}
	
	public boolean nomadic(){
		return index == -1;
	}
	
	public static Object[] values(XProperty... properties){
		if(properties == null) return new Object[0];
		Object[] vs = new Object[properties.length];
		for(int i=0; i<properties.length; ++i){
			vs[i] = properties[i].getValue();
		}
		return vs;
	}
	
	public static String[] keys(XProperty... properties){
		if(properties == null) return new String[0];
		String[] ks = new String[properties.length];
		for(int i=0; i<properties.length; ++i){
			ks[i] = properties[i].getKey();
		}
		return ks;
	}
	
	@Override
	public int compareTo(XProperty o) {
		return this.getKey().compareTo(o==null?null:o.getKey());
	}
}
