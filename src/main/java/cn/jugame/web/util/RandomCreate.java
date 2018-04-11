package cn.jugame.web.util;

/**
 * 随机生成字母和数字组合的8位字符串（重置支付密码）
 * 
 * @author Administrator
 * 
 */
public class RandomCreate {
	/**
	 * @param args
	 */

	private final static String[] STR = {"0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
			"a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v",
			"b", "n", "m"};
    /**
     * 生成
     * @return 8位随机
     */
	public static String getStr() {
		String s = "";
		for (int i = 0; i < 8; i++) {
			int a = (int) (Math.random() * 36);
			s += STR[a];
		}
		return s;
	}

	/**
	 * 主方法 测试
	 * @param args 参数
	 */
	public static void main(String[] args) {

		String m = RandomCreate.getStr();
		System.out.println(m);

	}
}
