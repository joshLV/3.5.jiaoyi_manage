package cn.juhaowan.log.service;

import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.log.vo.BackUserLog;

/**
 * 后台日志
 * **/
/**
 * @author Administrator
 * 
 */
public interface BackUserLogService {

	/**
	 * 插入(客服系统使用1)
	 */
	@Deprecated
	public int OPERATE_TYPE_INSERT = 1;
	/**
	 * 修改(客服系统使用2)
	 */
	@Deprecated
	public int OPERATE_TYPE_UPDATE = 2;
	/**
	 * 删除(客服系统使用3)
	 */
	@Deprecated
	public int OPERATE_TYPE_DELETE = 3;

	/**
	 * 管理员登录
	 */
	public static int ADMIN_LOGIN = 100000;
	/**
	 * 添加管理员
	 */
	public static int ADMIN_ADD = 100001;
	/**
	 * 删除管理员
	 */
	public static int ADMIN_DELETE = 100002;
	/**
	 * 修改管理员信息
	 */
	public static int ADMIN_UPDATE_INFO = 100003;
	/**
	 * 修改管理员类型
	 */
	public static int ADMIN_UPDATE_TYPE = 100004;
	/**
	 * 修改管理员状态
	 */
	public static int ADMIN_UPDATE_STATUS = 100005;

	/**
	 * 修改管理员密码
	 */
	public static int ADMIN_UPDATE_PASSWORD = 100006;

	/**
	 * 添加角色
	 */
	public static int ROLE_ADD = 200001;
	/**
	 * 删除角色
	 * 
	 */
	public static int ROLE_DELETE = 200002;
	/**
	 * 修改角色信息
	 */
	public static int ROLE_UPDATE = 200003;
	/**
	 * 修改角色状态
	 */
	public static int ROLE_UPDATE_STATUS = 200004;
	/**
	 * 设置角色全权限
	 */
	public static int ROLE_SET_PERMISSION = 200005;

	/**
	 * 添加模块
	 */
	public static int MODULE_ADD = 300001;
	/**
	 * 删除模块
	 */
	public static int MODULE_DELETE = 300002;
	/**
	 * 修改模块
	 */
	public static int MODULE_UPDATE = 300003;
	/**
	 * 添加模块权限
	 */
	public static int MODULE_ADD_PERMISSION = 300004;
	/**
	 * 删除模块权限
	 */
	public static int MODULE_DELETE_PERMISSION = 300005;
	/**
	 * 修改模块权限
	 */
	public static int MODULE_UPDATE_PERMISSION = 300006;
	/**
	 * 修改模块状态
	 */
	public static int MODULE_UPDATE_STATUS = 300007;
	/**
	 * 添加系统参数
	 */
	public static int SYS_ARGS_ADD = 400001;
	/**
	 * 删除系统参数
	 */
	public static int SYS_ARGS_DELETE = 400002;
	/**
	 * 修改系统参数
	 */
	public static int SYS_ARGS_UPDATE = 400003;

	/**
	 * 会员管理_黑名单添加
	 */
	public static int MEMBER_BLACKLIST_ADD = 500001;
	/**
	 * 会员管理_黑名单删除
	 */
	public static int MEMBER_BLACKLIST_DELETE = 500002;
	/**
	 * 会员管理_黑名单修改
	 */
	public static int MEMBER_BLACKLIST_UPDATE = 500003;

	/**
	 * 会员管理_实名认证
	 */
	public static int REALNAME_AUTHENTICATION = 500004;

	/**
	 * 会员管理_提现申请
	 */
	public static int WITHDRAWAL_APPLICATION = 500005;
	/**
	 * 会员管理_提现转账
	 */
	public static int CASH_TRANSFERS = 500006;
	/**
	 * 会员管理_订单发货
	 */
	public static int ORDER_DISPATCH = 500007;
	/**
	 * 会员管理_订单退款
	 */
	public static int ORDER_REFUND = 500008;
	/**
	 * 会员管理_支付密码_重置
	 */
	public static int PAY_PASSWORD_RESET = 500009;
	/**
	 * 会员管理_支付密码_解锁
	 */
	public static int PAY_PASSWORD_UNLOCK = 500010;
	/**
	 * 会员管理_登录密码_解锁
	 */
	public static int LOGIN_PASSWORD_UNLOCK = 500011;
	/**
	 * 会员管理_押金
	 */
	public static int DEPOSIT_ADD = 500012;
	/**
	 * 会员类型修改
	 */
	public static int MEMBER_TYPE_UPDATE = 500013;
	/**
	 * 会员列表锁定
	 */
	public static int MEMBER_LIST_LOCK = 500014;

	/**
	 * 会员列表解锁
	 */
	public static int MEMBER_LIST_UNLOCK = 500015;

	/**
	 * 会员服务类型添加
	 */
	public static int MEMBER_SERVICETYPE_ADD = 500016;
	/**
	 * 会员服务类型删除
	 */
	public static int MEMBER_SERVICETYPE_DELETE = 500017;
	/**
	 * 会员服务类型修改
	 */
	public static int MEMBER_SERVICETYPE_UPDATE = 500018;
	/**
	 * 会员服务类型修改状态
	 */
	public static int MEMBER_SERVICETYPE_UPDATESTATUS = 500019;
	/**
	 * 会员服务类型修改权重
	 */
	public static int MEMBER_SERVICETYPE_UPDATEWEIGHT = 500020;
	/**
	 * 开通会员服务
	 */
	public static int MEMBER_SERVICE_OPEN = 500021;
	/**
	 * 关闭会员服务
	 */
	public static int MEMBER_SERVICE_CLOSE = 500022;
	/**
	 * 添加卖家缴费信息
	 */
	public static int MEMBER_STORE_PAYCOST_ADD = 500023;
	/**
	 * 添加用户店铺审核
	 */
	public static int MEMBER_STORE_VERIFY = 500024;

	/**
	 * 查看用户银行卡信息
	 */
	public static int MEMBER_SHOW_BANKCARDNUM = 500025;

	/**
	 * 解除验证码锁定
	 */
	public static int MEMBER_DYNAMIC_PASS_UNLOCK = 500026;

	/**
	 * 修改用户电话号码
	 */
	public static int MEMBER_MODIFY_PHONE = 500027;
	
	/**
	 * 修改用户实名信息
	 */
	public static int MEMBER_MODIFY_REALINFO = 500028;

	/**
	 * 添加游戏
	 */
	public static int GAME_ADD = 600001;
	/**
	 * 删除游戏
	 */
	public static int GAME_DELETE = 600002;
	/**
	 * 修改游戏
	 */
	public static int GAME_UPDATE = 600003;

	/**
	 * 添加游戏区
	 */
	public static int GAME_PARTITION_ADD = 600004;
	/**
	 * 修改游戏区
	 */
	public static int GAME_PARTITION_UPDATE = 600006;
	/**
	 * 删除游戏区
	 */
	public static int GAME_PARTITION_DELETE = 600005;
	/**
	 * 删除游戏服
	 */
	public static int GAME_SERVER_DELETE = 600008;
	/**
	 * 添加游戏服
	 */
	public static int GAME_SERVER_ADD = 600007;
	/**
	 * 修改游戏服
	 */
	public static int GAME_SERVER_UPDATE = 600009;
	/**
	 * 停机维护
	 */
	public static int GAME_MAINTENANCE = 600010;
	/**
	 * 添加游戏渠道
	 */
	public int GAME_CHANNLE_ADD = 600011;
	/**
	 * 删除游戏渠道
	 */
	public int GAME_CHANNLE_DELETE = 600012;
	/**
	 * 修改游戏渠道
	 */
	public int GAME_CHANNLE_UPDATE = 600013;
	/**
	 * 添加游戏分类
	 */
	public int GAME_PARTITION_CATEGORY_ADD = 600014;
	/**
	 * 删除游戏分类
	 */
	public int GAME_PARTITION_CATEGORY_DELETE = 600015;
	/**
	 * 修改游戏分类
	 */
	public int GAME_PARTITION_CATEGORY_UPDATE = 600016;
	
	/**
	 * 添加某个游戏下的渠道
	 */
	public int GAME_GAME_CHANNEL_ADD = 600017;
	/**
	 * 删除某个游戏下的渠道
	 */
	public int GAME_GAME_CHANNEL_DELETE = 60018;
	/**
	 * 修改某个游戏下的渠道
	 */
	public int GAME_GAME_CHANNEL_UPDATE = 60019;
	/**
	 * 添加游戏版本
	 */
	public int Game_Version_ADD = 600020;
	/**
	 * 更新游戏版本
	 */
	public int Game_Version_UPDATE = 600021;
	/**
	 * 删除游戏版本
	 */
	public int Game_Version_DEL = 600022;
	
	/**
	 * 删除代充白名单
	 */
	public int DC_WHITE_LIST_DEL = 600023;
	
	/**
	 * 添加代充白名单
	 */
	public int DC_WHITE_LIST_ADD = 600024;
	
	

	/**
	 * 商品修改
	 */
	public static int PRODUCT_UPDATE = 700003;
	/**
	 * 商品下架(后台)
	 */
	public static int PRODUCT_SHELVES = 700004;
	/**
	 * 解冻指定数量的商品
	 */
	public static int PRODUCT_UNLOCK_AMOUNT = 700005;

	/**
	 * c2c商品上架审核
	 */
	public static int PRODUCT_C2C_VERIFY = 700006;
	/**
	 * 热销商品删除
	 */
	public static int PRODUCT_HOT_SALE_DELETE = 700007;
	
	/**
	 * c2c商品查看买家用户账号信息
	 */
	public static int PRODUCT_C2C_GAMEACCOUNT_INFO = 700008;
	
	/**
	 * 订单模式删除
	 */
	public static int PRODUCT_ONSALE_MODEL_DELETE= 700009;
	
	/**
	 * 商品类型一级类目添加
	 */
	public static int PRODUCT_TYPE_GROUP_ADD = 700010;
	/**
	 * 商品类型一级类目修改
	 */
	public static int PRODUCT_TYPE_GROUP_UPDATE = 700011;
	/**
	 * 商品类型一级类目删除
	 */
	public static int PRODUCT_TYPE_GROUP_DELETE = 700012;
	
	
	
	/**
	 * 站内信添加
	 */
	public static int STAND_INSIDE_LETTER_ADD = 800001;
	/**
	 * 站内信删除
	 */
	public static int STAND_INSIDE_LETTER_DELETE = 800002;
	/**
	 * 公告添加
	 */
	public static int ANNOUNCEMENT_ADD = 900001;
	/**
	 * 公告删除
	 */
	public static int ANNOUNCEMENT_DELETE = 900002;
	/**
	 * 公告修改
	 */
	public static int ANNOUNCEMENT_UPDATE = 900003;
	/**
	 * 公告分类添加
	 */
	public static int ANNOUNCEMENT_CATEGORY_ADD = 900004;
	/**
	 * 公告分类删除
	 */
	public static int ANNOUNCEMENT_CATEGORY_DELETE = 900005;
	/**
	 * 公告分类修改
	 */
	public static int ANNOUNCEMENT_CATEGORY_UPDATE = 900006;
	/**
	 * 添加帮助信息
	 */
	public static int HELPINFO_ADD = 110001;
	/**
	 * 删除帮助信息
	 */
	public static int HELPINFO_DELETE = 110002;
	/**
	 * 修改帮助信息
	 */
	public static int HELPINFO_UPDATE = 110003;
	/**
	 * 添加帮助类型
	 */
	public static int HELP_CATEGORY_ADD = 110004;
	/**
	 * 删除帮助类型
	 */
	public static int HELP_CATEGORY_DELETE = 110005;
	/**
	 * 修改帮助类型
	 */
	public static int HELP_CATEGORY_UPDATE = 110006;

	/**
	 * 添加最新出售
	 */
	public static int NEWSALES_ADD = 120001;
	/**
	 * 删除最新出售
	 */
	public static int NEWSALES_DELETE = 120002;
	/**
	 * 修改最新出售
	 */
	public static int NEWSALES_UPDATE = 120003;

	/**
	 * 添加意见反馈
	 */
	public static int FEEDBACK_ADD = 130001;
	/**
	 * 删除意见反馈
	 */
	public static int FEEDBACK_DELETE = 130002;

	/**
	 * 添加栏目
	 */
	public static int COLUMN_ADD = 140001;
	/**
	 * 删除栏目
	 */
	public static int COLUMN_DELETE = 140002;
	/**
	 * 修改栏目
	 */
	public static int COLUMN_UPDATE = 140003;

	/**
	 * 手动充值
	 */
	public int ARTIFICIAL_REFUND = 150001;

	/**
	 * 手动退款
	 */
	public int MANUAL_TOPUP = 150002;
	/**
	 * 手动转账
	 */
	public int ARTIFICIAL_TRANSFER = 150003;
	/**
	 * 幻灯片添加
	 */
	public int BANNER_ADD = 160001;

	/**
	 * 幻灯片更新
	 */
	public int BANNER_UPDATE = 160002;

	/**
	 * 幻灯片删除
	 */
	public int BANNER_DELETE = 160003;

	/**
	 * 幻灯片图片删除
	 */
	public int BANNERIMG_DELETE = 160004;
	/**
	 * 站内信添加
	 */
	public int MESSAGE_MISSION_CATEGORY_ADD = 170001;
	/**
	 * 站内信删除
	 */
	public int MESSAGE_MISSION_CATEGORY_DELETE = 170002;
	/**
	 * 站内信修改
	 */
	public int MESSAGE_MISSION_CATEGORY_EDIT = 170003;
	/**
	 * 添加工作状态类型
	 */
	public int WORK_STATUS_ADD = 180001;
	/**
	 * 删除工作状态类型
	 */
	public int WORK_STATUS_DELETE = 180002;
	/**
	 * 修改工作状态类型
	 */
	public int WORK_STATUS_EDIT = 180003;
	/**
	 * 游戏解绑
	 */
	public int GAME_UNBIND = 190001;
	
	/**
	 * 短信群发
	 */
	public int SMS_IN_GROUPS = 200001;
	/**
	 * 转单卖家
	 */
	public int TO_SINGLE_SELLER = 210001;
	/**
	 * 退款--支付宝提现失败退款
	 */
	public int ALIPAY_WITHDRAWAL_FAIL_REFUND = 220001;
	/**
	 * 写入日志
	 * 
	 * @param uid
	 *            操作人
	 * @param ip
	 *            操作ip
	 * @param type
	 *            日志类型
	 */
	int addLog(int uid, String ip, int type, String logRemark);

	/**
	 * 查询日志列表
	 * 
	 * @param condition
	 *            查询条件
	 * @param pageSize
	 *            每页显示数量
	 * @param pageNo
	 *            显示页
	 * @param sort
	 *            排序字段
	 * @param order
	 *            排序规则
	 **/
	PageInfo<BackUserLog> queryLogList(Map<String, Object> condition, int pageSize, int pageNo, String sort,
			String order);

}
