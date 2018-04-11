package cn.juhaowan.order.util;

import java.util.ResourceBundle;

/**
 * @author
 * 
 */
public class Configuration {

	private static Object LOCK = new Object();
	private static Configuration CONFIG = null;
	private static ResourceBundle RB = null;
	private static final String CONFIG_FILE = "activemq";

	/**
	 * 无参构造方法
	 */
	private Configuration() {
		RB = ResourceBundle.getBundle(CONFIG_FILE);
	}

	/**
	 * 静态实列化方法
	 * 
	 * @return Configuration对象
	 */
	public static Configuration getInstance() {
		synchronized (LOCK) {
			if (null == CONFIG) {
				CONFIG = new Configuration();
			}
		}
		return (CONFIG);
	}

	/**
	 * 根据key获取value
	 * 
	 * @param key
	 *            参数
	 * @return value
	 */
	public String getValue(String key) {
		return (RB.getString(key));
	}
}
