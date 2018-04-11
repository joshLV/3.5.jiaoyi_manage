/**
 * 
 */
package cn.jugame.web.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jugame.util.Cache;

/**
 * 人工退款功能工具类
 * 
 */
public class RefundUtil {
	/**
	 * 退款校验码缓存地址+管理员手机号
	 */
	private static final String REFUND_VERIFY_CODE = "refundVerifyCode/";

	/**
	 * 存储校验码到缓存
	 * 
	 * @param adminMobile
	 *            管理员手机号码
	 * @param verifyCode
	 *            校验码
	 */
	public static void storyVerfiyCode(String adminMobile, String verifyCode) {
		Cache.add(REFUND_VERIFY_CODE + adminMobile, 15 * 60, verifyCode);
	}

	/**
	 * 验证校验码是否正确
	 * 
	 * @param adminMobile
	 *            管理员手机号
	 * @param vcode
	 *            校验码
	 * @return 是否存在
	 */
	public static boolean checkRefundVCode(String adminMobile, String vcode) {
		if (StringUtils.isBlank(adminMobile) || StringUtils.isBlank(vcode)) {
			return false;
		}
		String code = Cache.get(REFUND_VERIFY_CODE + adminMobile);
		if (code == null) {
			return false;
		}
		if (code.equals(vcode)) {
			return true;
		}
		return false;
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 *            命令行参数
	 */
	public static void main(String[] args) {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("spring-config.xml");
		cxt.toString();
		storyVerfiyCode("13533509161", "660683");
		System.err.println(checkRefundVCode("13533509161", "660683"));
	}

	/**
	 * 生成校验码
	 * 
	 * @return 返回随机校验码
	 */
	public static String randomVerifyCode() {
		Random rand = new Random();
		int verifyCode = rand.nextInt(1000000);
		while (verifyCode < 100000) {
			verifyCode = rand.nextInt(1000000);
			if (verifyCode > 100000) {
				break;
			}
		}
		return verifyCode + "";
	}

	/**
	 * 操作完成删除本次验证码
	 * 
	 * @param adminMobile
	 *            管理员手机号码
	 */
	public static void removeVerifyCode(String adminMobile) {
		Cache.delete(REFUND_VERIFY_CODE + adminMobile);
	}
}
