/**
 * 
 */
package cn.jugame.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import cn.jugame.vo.SysUserinfo;
import cn.juhaowan.log.service.BackUserLogService;

/**
 * 后台用户日志记录工具
 * 
 * @author APXer
 * 
 */
public class BackUserLogUtil {
	public static Map<String, String> BACKLOG_TYPE_MAP = new TreeMap<String, String>();
	static {
		BACKLOG_TYPE_MAP.put("100000", "管理员登录");
		BACKLOG_TYPE_MAP.put("100001", "添加管理员");
		BACKLOG_TYPE_MAP.put("100002", "删除管理员");
		BACKLOG_TYPE_MAP.put("100003", "修改管理员信息");
		BACKLOG_TYPE_MAP.put("100004", "修改管理员类型");
		BACKLOG_TYPE_MAP.put("100005", "修改管理员状态");
		BACKLOG_TYPE_MAP.put("100006", "修改管理员密码");

		BACKLOG_TYPE_MAP.put("200001", "添加角色");
		BACKLOG_TYPE_MAP.put("200002", "删除角色");
		BACKLOG_TYPE_MAP.put("200003", "修改角色信息");
		BACKLOG_TYPE_MAP.put("200004", "修改角色状态");
		BACKLOG_TYPE_MAP.put("200005", "设置角色权限");

		BACKLOG_TYPE_MAP.put("300001", "添加模块");
		BACKLOG_TYPE_MAP.put("300002", "删除模块");
		BACKLOG_TYPE_MAP.put("300003", "修改模块");
		BACKLOG_TYPE_MAP.put("300004", "添加模块权限");
		BACKLOG_TYPE_MAP.put("300005", "删除模块权限");
		BACKLOG_TYPE_MAP.put("300006", "修改模块权限");
		BACKLOG_TYPE_MAP.put("300007", "修改模块状态");
		
		BACKLOG_TYPE_MAP.put("400001", "添加系统参数");
		BACKLOG_TYPE_MAP.put("400002", "删除系统参数");
		BACKLOG_TYPE_MAP.put("400003", "修改系统参数");

		BACKLOG_TYPE_MAP.put("500001", "添加黑名单");
		BACKLOG_TYPE_MAP.put("500002", "删除黑名单");
		BACKLOG_TYPE_MAP.put("500003", "修改黑名单");
		BACKLOG_TYPE_MAP.put("500004", "实名认证");
		BACKLOG_TYPE_MAP.put("500005", "提现申请");
		BACKLOG_TYPE_MAP.put("500006", "提现转账");
		BACKLOG_TYPE_MAP.put("500007", "订单发货");
		BACKLOG_TYPE_MAP.put("500008", "订单退款");
		BACKLOG_TYPE_MAP.put("500009", "支付密码重置");
		BACKLOG_TYPE_MAP.put("500010", "支付密码解锁");
		BACKLOG_TYPE_MAP.put("500011", "登录密码解锁");
		BACKLOG_TYPE_MAP.put("500012", "押金");
		BACKLOG_TYPE_MAP.put("500013", "会员类型修改");
		BACKLOG_TYPE_MAP.put("500014", "会员列表锁定");
		BACKLOG_TYPE_MAP.put("500015", "会员列表解锁");
		BACKLOG_TYPE_MAP.put("500016", "会员服务类型添加");
		BACKLOG_TYPE_MAP.put("500017", "会员服务类型删除");
		BACKLOG_TYPE_MAP.put("500018", "会员服务类型修改");
		BACKLOG_TYPE_MAP.put("500019", "会员服务类型修改状态");
		BACKLOG_TYPE_MAP.put("500020", "会员服务类型修改权重");
		BACKLOG_TYPE_MAP.put("500021", "开通会员服务");
		BACKLOG_TYPE_MAP.put("500022", "关闭会员服务");
		BACKLOG_TYPE_MAP.put("500023", "添加卖家缴费信息");
		BACKLOG_TYPE_MAP.put("500024", "会员店铺审核");
		BACKLOG_TYPE_MAP.put("500025", "查看用户银行卡");
		BACKLOG_TYPE_MAP.put("500026", "解除验证码锁定");
		BACKLOG_TYPE_MAP.put("500027", "修改手机号码");
		BACKLOG_TYPE_MAP.put("500028", "修改实名信息");

		BACKLOG_TYPE_MAP.put("600001", "添加游戏");
		BACKLOG_TYPE_MAP.put("600002", "删除游戏");
		BACKLOG_TYPE_MAP.put("600003", "修改游戏");

		BACKLOG_TYPE_MAP.put("600004", "添加游戏区");
		BACKLOG_TYPE_MAP.put("600005", "删除游戏区");
		BACKLOG_TYPE_MAP.put("600006", "修改游戏区");

		BACKLOG_TYPE_MAP.put("600007", "添加游戏服");
		BACKLOG_TYPE_MAP.put("600008", "删除游戏服");
		BACKLOG_TYPE_MAP.put("600009", "修改游戏服");

		BACKLOG_TYPE_MAP.put("600010", "停机维护");

		BACKLOG_TYPE_MAP.put("600011", "添加游戏渠道");
		BACKLOG_TYPE_MAP.put("600012", "删除游戏渠道");
		BACKLOG_TYPE_MAP.put("600013", "修改游戏渠道");
		
		BACKLOG_TYPE_MAP.put("600014", "添加游戏分类");
		BACKLOG_TYPE_MAP.put("600015", "删除游戏分类");
		BACKLOG_TYPE_MAP.put("600016", "修改游戏分类");
		
		BACKLOG_TYPE_MAP.put("600017", "添加某个游戏的渠道");
		BACKLOG_TYPE_MAP.put("600018", "删除某个游戏的渠道");
		BACKLOG_TYPE_MAP.put("600019", "修改某个游戏的渠道");
		
		BACKLOG_TYPE_MAP.put("600020", "添加游戏版本");
		BACKLOG_TYPE_MAP.put("600021", "删除游戏版本");
		BACKLOG_TYPE_MAP.put("600022", "修改游戏版本");
		BACKLOG_TYPE_MAP.put("600023", "删除代充白名单");
		BACKLOG_TYPE_MAP.put("600024", "添加代充白名单");
		
		BACKLOG_TYPE_MAP.put("700003", "商品修改");
		BACKLOG_TYPE_MAP.put("700004", "商品下架");
		BACKLOG_TYPE_MAP.put("700005", "解冻商品");
		BACKLOG_TYPE_MAP.put("700006", "商品审核");
		BACKLOG_TYPE_MAP.put("700007", "热销商品删除");
		BACKLOG_TYPE_MAP.put("700008", "查看卖家账号信息");
		BACKLOG_TYPE_MAP.put("700009", "订单模式删除");
		BACKLOG_TYPE_MAP.put("700010", "商品类型一级分类添加");
		BACKLOG_TYPE_MAP.put("700011", "商品类型一级分类修改");
		BACKLOG_TYPE_MAP.put("700012", "商品类型一级分类删除");

		BACKLOG_TYPE_MAP.put("800001", "站内信添加");
		BACKLOG_TYPE_MAP.put("800002", "站内信删除");

		BACKLOG_TYPE_MAP.put("900001", "添加公告");
		BACKLOG_TYPE_MAP.put("900002", "删除公告");
		BACKLOG_TYPE_MAP.put("900003", "修改公告");
		BACKLOG_TYPE_MAP.put("900004", "添加公告类型");
		BACKLOG_TYPE_MAP.put("900005", "删除公告类型");
		BACKLOG_TYPE_MAP.put("900006", "修改公告类型");

		BACKLOG_TYPE_MAP.put("110001", "添加帮助");
		BACKLOG_TYPE_MAP.put("110002", "删除帮助");
		BACKLOG_TYPE_MAP.put("110003", "修改帮助");
		BACKLOG_TYPE_MAP.put("110004", "添加帮助类型");
		BACKLOG_TYPE_MAP.put("110005", "删除帮助类型");
		BACKLOG_TYPE_MAP.put("110006", "修改帮助类型");

		BACKLOG_TYPE_MAP.put("120001", "添加最新出售");
		BACKLOG_TYPE_MAP.put("120002", "删除最新出售");
		BACKLOG_TYPE_MAP.put("120003", "修改最新出售");

		BACKLOG_TYPE_MAP.put("130001", "添加意见反馈");
		BACKLOG_TYPE_MAP.put("130002", "删除意见反馈");

		BACKLOG_TYPE_MAP.put("140001", "添加栏目");
		BACKLOG_TYPE_MAP.put("140002", "删除栏目");
		BACKLOG_TYPE_MAP.put("140003", "修改栏目");

		BACKLOG_TYPE_MAP.put("150001", "手动充值");
		BACKLOG_TYPE_MAP.put("150002", "手动退款");
		BACKLOG_TYPE_MAP.put("150003", "手动转账");
		
		BACKLOG_TYPE_MAP.put("160001", "幻灯片添加");
		BACKLOG_TYPE_MAP.put("160002", "幻灯片更新");
		BACKLOG_TYPE_MAP.put("160003", "幻灯片删除");
		BACKLOG_TYPE_MAP.put("160004", "幻灯片图片删除");
		
		BACKLOG_TYPE_MAP.put("170001", "站内信添加");
		BACKLOG_TYPE_MAP.put("170002", "站内信删除");
		BACKLOG_TYPE_MAP.put("170003", "站内信修改");
		
		BACKLOG_TYPE_MAP.put("180001", "在线工作状态添加");
		BACKLOG_TYPE_MAP.put("180002", "在线工作状态删除");
		BACKLOG_TYPE_MAP.put("180003", "在线工作状态修改");
		
		BACKLOG_TYPE_MAP.put("190001", "游戏解绑");
		
		BACKLOG_TYPE_MAP.put("200001", "短信群发");

		BACKLOG_TYPE_MAP.put("210001", "转单卖家");

		BACKLOG_TYPE_MAP.put("220001", "支付宝提现失败-退款");
	}
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String USER_IP = "userIp";

	/**
	 * 添加后台日志
	 * 
	 * @param backUserLogService
	 * 
	 * 
	 * @param isOperateSuccess
	 *            操作是否成功
	 * @param operateRecordId
	 *            操作记录ID
	 * @param request
	 *            请求
	 */
	public static void log(BackUserLogService backUserLogService, boolean isOperateSuccess, String operateRecordId,
			int backUserLogType, HttpServletRequest request) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		if (userInfo != null) {
			int userId = userInfo.getUserId();
			String userName = userInfo.getLoginid();
			String userIp = RequestUtils.getUserIp(request);
			String moduleName = BackUserLogUtil.getName(backUserLogType);

			if (isOperateSuccess) {
				// 进行添加
				if (operateRecordId.isEmpty()) {
					backUserLogService.addLog(userId,//
							userIp,//
							backUserLogType,//
							userName + moduleName + "成功");
				} else {
					backUserLogService.addLog(userId,//
							userIp,//
							backUserLogType,//
							"管理员: " + userName + moduleName + "ID: " + operateRecordId + "成功");

				}
			} else {
				// 进行添加
				backUserLogService.addLog(userId,//
						userIp,//
						backUserLogType,//
						"管理员: " + userName + moduleName + "失败");
			}
		}
	}

	/**
	 * 获得用户信息
	 * 
	 * @param request
	 * @return 用户信息
	 */
	public static Map<String, String> findUserInfo(HttpServletRequest request) {
		SysUserinfo userInfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);

		if (userInfo != null) {
			int userId = userInfo.getUserId();
			String userName = userInfo.getLoginid();
			String userIp = RequestUtils.getUserIp(request);
			Map<String, String> userInfoMap = new HashMap<String, String>();
			userInfoMap.put(USER_ID, userId + "");
			userInfoMap.put(USER_IP, userIp);
			userInfoMap.put(USER_NAME, userName);
			return userInfoMap;
		}
		return null;
	}

	/**
	 * @param backUserLogTYPE
	 * @return
	 */
	public static String getName(int backUserLogTYPE) {
		return BACKLOG_TYPE_MAP.get(backUserLogTYPE + "");
	}

	/**
	 * @return 所有日志类型
	 */
	public static JSONObject allLogTypeMapJson() {
		return JSONObject.fromObject(BACKLOG_TYPE_MAP);
	}

	/**
	 * @param args命令行参数
	 */
	public static void main(String[] args) {
		System.err.println(BackUserLogUtil.getName(BackUserLogService.ADMIN_ADD));
		System.err.println(allLogTypeMapJson());
	}
}
