package cn.juhaowan.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 一般情况下，你就当它是一个TreeMap<String, Object>来使用吧。<br>
 * 但是稍微有点特殊的是，内部存储key时使用了一个链表，即可以拿到一个按时间顺序排好序的key列表，参见 keys 方法。
 * 同样的，你也可以通过 for(XProperty pro : xobject) 来按时间插入顺序来遍历这个TreeMap中的所有元素。<br>
 * <br>
 * XObject和XProperty需要位于同一个包内，他们是同一个整体，如果不是java恶心地限制了一个文件只能写一个public类，
 * 我早就把他们写到同一个文件里头去了，那样引用起来多方便！
 * 
 * @author zimT_T
 *
 */
public class XObject implements Map<String, Object>, Iterable<XProperty>{
	
	protected ArrayList<XProperty> _list = new ArrayList<XProperty>();
	protected TreeMap<String, XProperty> _map = new TreeMap<String, XProperty>();
	
	public XObject(XProperty... pros){
		for(XProperty pro : pros){
			this.put(pro.getKey(), pro.getValue());
		}
	}
	
	/**
     * Test two values for equality.  Differs from o1.equals(o2) only in
     * that it copes with {@code null} o1 properly.
     */
    static final boolean valEquals(Object o1, Object o2) {
        return (o1==null ? o2==null : o1.equals(o2));
    }

    static long convert2Long(Object o){
		if(o == null)
			return 0;
		if(o instanceof Long) return ((Long)o).longValue();
		if(o instanceof Integer){
			int tmp = ((Integer)o).intValue();
			return (long)tmp;
		}
		
		String s = o.toString();
		StringBuffer m = new StringBuffer();
		for(int i=0; i<s.length(); ++i){
			char c = s.charAt(i);
			if(c >= '0' && c <= '9'){
				m.append(c);
			}
			else break;
		}
		return Integer.parseInt(m.toString());
	}
	
	static int convert2Int(Object o){
		long d = convert2Long(o);
		return (int)d;
	}
	
	static double convert2Double(Object o){
		if(o == null)
			return 0.0;
		if(o instanceof Double){
			return ((Double)o).doubleValue();
		}
		if(o instanceof Float){
			float tmp = ((Float)o).floatValue();
			return (double)tmp;
		}
		
		String s = o.toString();
		StringBuffer m = new StringBuffer();
		boolean dot = false;
		for(int i=0; i<s.length(); ++i){
			char c = s.charAt(i);
			if(c >= '0' && c <= '9'){
				m.append(c);
			}else if(c == '.'){
				if(!dot){
					dot = true;
					m.append(c);
				}
				else break;
			}
			else break;
		}
		return Double.parseDouble(m.toString());
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		for(Entry<String, XProperty> e : _map.entrySet()){
			if(valEquals(e.getValue().val, value))
				return true;
		}
		return false;
	}

	@Override
	public Object get(Object key) {
		XProperty n = _map.get(key);
		return n==null ? null : n.val;
	}

	@Override
	public Object put(String key, Object value) {
		XProperty n = new XProperty(key, value, _list.size());
		_list.add(n);
		
		XProperty old = _map.put(key, n);
		return old==null ? null : old.val;
	}

	@Override
	public Object remove(Object key) {
		XProperty n = _map.remove(key);
		if(n==null) return null;
		_list.remove(n.index);
		return n.val;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		for(Entry<? extends String, ? extends Object> e : m.entrySet()){
			this.put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear() {
		_list.clear();
		_map.clear();
	}

	@Override
	public Set<String> keySet() {
		return _map.keySet();
	}

	@Override
	public Collection<Object> values() {
		ArrayList<Object> vals = new ArrayList<Object>();
		for(XProperty e : _list){
			vals.add(e.getValue()==null?null:e.getValue());
		}
		return vals;
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		Set<Map.Entry<String, Object>> set = new TreeSet<Map.Entry<String, Object>>();
		for(Entry<String, XProperty> e : _map.entrySet()){
			set.add(e.getValue());
		}
		return set;
	}

	@Override
	public Iterator<XProperty> iterator() {
		return _list.iterator();
	}
	

	/* 扩展的方法 */
	/*********************************************************************/
	public List<String> keys(){
		ArrayList<String> keys = new ArrayList<String>();
		for(XProperty n : _list){
			keys.add(n.key);
		}
		return keys;
	}
	public long getAsLong(String key){
		return convert2Long(this.get(key));
	}
	public int getAsInt(String key){
		return (int)convert2Long(this.get(key));
	}
	public double getAsDouble(String key){
		return convert2Double(this.get(key));
	}
	public String getAsString(String key){
		Object s = this.get(key);
		if(s == null) return null;
		return s.toString();
	}
	public XObject getAsXObject(String key){
		Object obj = this.get(key);
		try{
			return (XObject)obj;
		}catch(Exception e){
			return null;
		}
	}
	/*用于导出内部属性的方法*/
	public String dump(int deep){
		StringBuffer buf = new StringBuffer();
		buf.append("XObject:\n");
		for(XProperty pro : this){
			for(int i=0; i<deep; ++i){
				buf.append("  ");
			}
			if(pro.getValue() instanceof XObject){
				XObject inner = (XObject)pro.getValue();
				buf.append("  ").append(pro.getKey()).append(" => ").append(inner.dump(deep+1)).append("\n");
			}else{
				buf.append("  ").append(pro.getKey()).append(" => ").append(pro.getValue()).append("\n");
			}
		}
		return buf.toString();
	}
	public String dump(){
		return dump(0);
	}
	
	@Override
	public String toString() {
		return dump(0);
	}
	
	public static void main(String[] args) {
		XObject obj = new XObject(new XProperty[]{
				new XProperty("aa", "aa"),
				new XProperty("bb", 11),
				new XProperty("cc", 3.0),
				new XProperty("dd", new XObject(new XProperty[]{
						new XProperty("11", "11"),
						new XProperty("22", "22"),
				})),
		});
		System.out.println(obj);
	}
}
