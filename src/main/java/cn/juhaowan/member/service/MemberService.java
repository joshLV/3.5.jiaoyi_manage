package cn.juhaowan.member.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import cn.jugame.util.PageInfo;
import cn.jugame.yeepay.api.message.IRespMsg;
import cn.juhaowan.member.vo.Member;
import cn.juhaowan.member.vo.MemberRealinfo;

/** 
 * 会员服务
 *  */
public interface MemberService {
	/**
	 * 更新用户最后登录时间
	 * @param uid 用户id
	 * @return 1成功，0失败
	 */
	int updateLastTime(int uid);
	/** 
    * 用户注册 
    * 
    * 0 注册成功
    * 1 注册失败
    * 
    * @param loginName 登录名
    * @param passwd 登录密码
    * @param ip 注册ip
    * @pdOid 594b1c8a-b248-43a7-8f08-1e1a1df858ad */
	int register(String loginName, String passwd,String ip);
   /**
    * 用户注册1
    * 0 注册成功
    * 1 注册失败
    * @param userQuestionId 密保问题id
    * @param userAnswer 密保答案
    * @param userMobile 绑定手机号
    * @param loginName 登录名
    * @param passwd 登录密码
    * @param ip 注册ip
    * */
   int register1(int userQuestionId,String userAnswer,String userMobile,String loginName, String passwd,String ip,String nickname,String sn, String userFrom, String userTerrace);
   
   
   /**
    * 用户注册（QQ）
    * @param loginName
    * @param passwd
    * @param ip
    * @param nickname
    * @param sn
    * @return
    */
   int registerForQQ(String loginName, String passwd, String mobile, String ip,String nickname,String sn, String userFrom,String userTerrace);
   /** 
    * 用户登录：（记录登录日志.锁定账号）
    *    0登录成功0
    *    
    *    -1账号或者密码不正确
   	*	 -2账号被临时锁定
   	*	 -3账号被永久锁定
    * 
    * @param loginid 用户账号/手机号/邮件
    * @param passwd 密码（明文）
    * 0登录成功
    * @pdOid a49aad16-432c-4605-b60c-ee1fa8734372 */
   int login(String loginid, String passwd);
   
   /**
    * 根据uid查询用户状态
    * @pdOid a49aad16-432c-4605-b60c-ee1fa8734372 */
   int loginUid(int uid);
   
   /** 
    * 根据用户名查询用户信息
    * 
    * @param loginName 登录名
    * @pdOid 215a632e-46fa-4652-b84f-79f34972598c */
   Member findMemberByName(String loginName);
   /** 
    * 根据用户手机号查询用户信息
    * 
    * @param mobile 手机号
    * @pdOid 215a632e-46fa-4652-b84f-79f34972598c */
   Member findMemberByMobile(String mobile);
   
   /** @param loginName 用户ID
    * @pdOid 2855e64b-f2ce-4b90-8fbc-94294e12e68c */
   MemberRealinfo findRealInfoByName(String loginName);
   /** 
    * 根据用户名查询用户信息
    * 
    * @param uid 登录名
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74 */
   Member findMemberByUid(int uid);
   /** 
    * 读取用户真实信息
    * 
    * @param uid 用户ID
    * @pdOid 9329e13f-35c1-4dbd-ad5f-6540acd33d1d */
   MemberRealinfo findRealInfoByUid(int uid);
   /** 
    * 更新登录密码
    * 
    * @param uid 登录名
    * @param newPasswd 新的密码（明文,该方法会自动两次MD5）
    * @pdOid 260a8501-0f06-471e-bf98-79ea2e68a59c */
   int updatePasswd(int uid, String newPasswd);
   /** 
    * 更新用户名
    * 
    * @param uid uid
    * @param newUserName 新的用户名
    * @pdOid 260a8501-0f06-471e-bf98-79ea2e68a59c */
   int updateLoginName(int uid, String newLoginName);
 
   
   /** 
    * 
    * @param condition 查询条件
    * @param pageSize 每页的记录数
    * @param pageNo 查询页码
    * @param sort 排序字段
    * @param order 升序/降序(asc/desc)
    * @pdOid 3bdb04f3-0989-49ac-951c-83c97f88287d */
   PageInfo<Member> queryMemberList(Map<String,Object> condition, int pageSize, int pageNo, String sort, String order);
   
   /** 
    * 
    * @param condition 查询条件
    * @param pageSize 每页的记录数
    * @param pageNo 查询页码
    * @param sort 排序字段
    * @param order 升序/降序(asc/desc)
    * @pdOid 3bdb04f3-0989-49ac-951c-83c97f88287d */
   PageInfo<MemberRealinfo> queryMemberRealInfoList(Map<String,Object> condition, int pageSize, int pageNo, String sort, String order);
  
   /** 
    * 设置支付密码
    * 1 成功
    * 0 失败
    * 
    * @param uid 用户ID
    * @param newPayPasswd 支付密码（明文）
    * @pdOid 2a9c7f50-2394-4509-8947-fcb4dc9c8981 */
   int updatePayPasswd(int uid, String newPayPasswd);
   /** 
    * 锁定用户账号
    * 0 锁定成功
    * 1 锁定失败
    * 
    * @param uid 登录账号
    * @param day 锁定天数
    * @param lockType 锁定类型，1长期锁定，2临时锁定
    * @pdOid 9a4e979b-0c66-45a1-9bab-78c3738b1068 */
   int lockMember(int uid, int lockType,int day,String remark);
   /** 
    * 绑定手机号
    * 
    * @param uid 
    * @param mobile
    * @pdOid 6e2cdeab-8a93-435c-bb36-4fdcc325bf0c */
   int bindMobile(int uid, String mobile);
   /** 
    * 解绑手机号
    * 
    * @param uid
    * @pdOid ab7dfff1-d84e-4b51-87c7-ec14c18eebce */
   int unBindMobile(int uid);
   /** 
    * 录入实名信息(身份证，银行信息）
    * 0 成功
    * 1 失败
    * 
    * @param uid 用户账号
    * @param realInfo 实名信息
    * @pdOid e7cb5a09-2a35-48b0-8bc9-d77343a55906 */
   int inputRealNameInfo(int uid, MemberRealinfo realInfo);
   
   
   /** 
    * 修改实名信息(认证成功修改认证时间）
    * 0 成功
    * 1 失败
    * @param uid 用户账号
    * @param approveTime 认证成功时间
    */
   int updateRealNameInfo(int uid, Date approveTime);
   
   
   /** 
    * 验证银行转账金额（如果金额正确则实名认证通过）
    * 
    * 
    * @param uid 账号
    * @param moneyNum 转账金额（单位：分）
    * @pdOid e0d52c9d-49b5-4adc-922f-0c8b87a8a1b3 */
   int chekMoneyNum(int uid, int moneyNum);
   /** 
    * 更新用户基本信息（不会更新密码等重要资料）
    * 
    * @param member
    * @pdOid b3a46de2-934e-48a9-a6f3-f2d3e8eb77cc */
   int updateMemberInfo(Member member);
   /** 
    * 检查安全提示是否正确 
    * 
    * 0  正常
    * 1  答案错误
    * 2  末密码安全提示问题
    * 
    * @param questionid 问题ID
    * @param answer 问题答案
    * @pdOid 701cea8c-9ae8-4420-b64a-8c33e276e116 */
   int checkAnswer(int uid,int questionid, String answer);
   /** 
    * 更新安全提问
    * 0 成功
    * 1 失败
    * 
    * @param questionid 问题ID
    * @param answer 问题答案
    * @pdOid c2136835-2229-460f-9518-42e52e4a055d */
   int updateAnswer(int uid ,int questionid, String answer);
   
   
   /** 
    * 后台实名认证 修改状态
    * 0 成功
    * 1 失败
    * 
    * @param uid 用户ID
    * @param status 实名认证状态
    * @pdOid 2a9c7f50-2394-4509-8947-fcb4dc9c8981 */
   int updateMemberRealInfoStatus(int uid, int status);
   
   /** 
    * 锁定用户账号
    * 0 成功
    * 1 失败
    * 
    * @param uid 用户ID
    * @param status 用户账号状态
    * @pdOid 2a9c7f50-2394-4509-8947-fcb4dc9c8981 */
   int updateMemberStatus(int uid, int status);

   
   /** 
    * 检查用户输入的手机号是否唯一
    * true 唯一
    * false 已存在
    * 
    * @param mobile 手机号
    */
   Boolean findMobile(String mobile);
   
   /** 
    * 后台实名认证 修改状态(后台)
    * 0 成功
    * 1 失败
    * 
    * @param uid 用户ID
    * @param status 实名认证状态
    * @param remark 备注
    * @pdOid 2a9c7f50-2394-4509-8947-fcb4dc9c8981 */
   int updateMemberRealInfoStatus(int uid, int status,String remark);
   
   /** 
    * 锁定用户账号
    * 0 成功
    * 1 失败
    * 
    * @param uid 用户ID
    * @param status 用户账号状态
    * @param remark 备注
    * @pdOid 2a9c7f50-2394-4509-8947-fcb4dc9c8981 */
   int updateMemberStatus(int uid, int status,String remark);
   /**
    * 验证支付密码
    * 
    **/
   boolean payPassChecking(int uid, String payPass);
   
   List<Integer> queryAllMemberID();
   
   /** 
    * 根据用户名查询用户信息(后台 不需要缓存)
    * 
    * @param uid 登录名
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74 */
   Member findMemberByUid_back(int uid);
   
   /** 
    * 根据用户昵称查询用户信息
    * 
    * @param nickName 昵称
    * 
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74 */
   Member findMemberByNickname(String nickName);
   
   /** 
    * 检查分证是否被使用
    * @param idCard 生分证号
    * true 唯一
    * false 已存在
    * 
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74 */
   Boolean findIDcard(String idCard);
   
   /** 
    * 检查分证是否被使用(排除自己)
    * @param idCard 生分证号
    * true 唯一
    * false 已存在
    * 
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74 */
   Boolean findIDcard2(int uid, String idCard);
   
   /**
    * 修改用户身份（后台） 
    * @param uid 用户id
    * 0 失败 
    * 1 成功
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
   int updateUserType(int uid, int identity);
   
   
   /**
    * 根据uid查询昵称
    * @param uid 用户id
    * 
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
   String findNicknameByUid(int uid);
   
   /**
    * 根据uid查询用户是否实名认证
    * @param uid 用户id
    * true 已实名认证
    * false  未实名认证
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
   boolean findAuthenticationByUid(int uid);
   /**
    * 根据uid查询用户是否设置支付密码
    * @param uid 用户id
    * true 已设置
    * false  未设置
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
   boolean findPaymentCodeByUid(int uid);
   
   
   /**
    * 判断是否设置过登陆密码
    * @param uid
    * @return
    */
   boolean isSetLoginPass(int uid);
   
   /**
    * 重置用户支付密码
    * @param uid 用户id
    * true 设置成功
    * false  设置失败
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
   boolean resetPayPass(int uid);
   
   
   /**
    * 是否锁定登录状态
    * @param uid
    * @return
    */
   boolean isLoginLock(int uid);
   
   /**
    * 是否锁定 支付状态（支付密码）
    * @param uid
    * @return
    */
   boolean isPayLock(int uid);
   
   /**
    * 根据uid查询手机号
    * @param uid
    * @return
    */
   public String findMobileByUid(int uid);

   /**
    * 是否是vip 用户 c2c 职责权限 查询条件时用到
    * @param uid
    * @return 1 普通用户 2 VIP
    */
   int isVIP(int uid);
   
   /**
    *验证登录密码是否正确 
    * 
    * @param uid 用户uid
    * @param loginpwd 密码
    * 
    * @return true 正确  false 错误
    * **/
   public boolean checkLoginPwd(int uid, String loginpwd);


//   /**
//    * 同步易宝实名认证
//    * @param uid 用户uid
//    * @param timeTamp 时间戳
//    * @param realName 真实姓名
//    * @param idCardType 默认"ID" 
//    * @param idCardNo 身份证号码
//    * @param desc 描述
//    * @return 
//    */
//   IRespMsg yeepayRealInfo(String uid,String timeTamp,String realName, String idCardType, String idCardNo, String desc);
   
   /**
    * 查询用户易宝账号资金详情
    * @param uid 用户uid
    * @param desc 描述
    * @param merchantAccountFlag 商户营收账户标识 （填0或1或不填(查用户账户时不填，查商户账户时此标识必须与商户总账账户标识和商户清算账户标识互斥) 
    * @return IRespMsg 对象
    */
   IRespMsg yeepayQueryAccount(String uid, String desc,int merchantAccountFlag);

   /**
    * 易宝设置银行提现卡API 同步用户银行卡信息
    * @param uid 用户uid (必填)
    * @param requestId 时间戳 (必填)
    * @param bankCardNumber 银行卡号 (必填)
    * @param branchName 银行名称
    * @param mobile 电话号码
    * @param province 省份
    * @param city 城市
    * @param desc 描述
    * @param branchinfo 支行信息
    * @return IRespMsg 对象
    */
   IRespMsg yeepaySetUpBankCard(String uid, String requestId,String bankCardNumber, 
		   String branchName, String mobile, String province, String city,String desc,String branchinfo);
	
   
   /**
    * 查询memberRealinfo
    **/
   List<Map<String,Object>> findMemberRealinfo();
   
   /**
    * 修改用户实名信息是否有改动 
    * @param uid 用户id
    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
   boolean updateChangeflagByUid(int uid, int changeflag);
   
   /**
    * 查询银行是否自动实名的信息列表
    * @return
    */
   int qeuryBankInfoAuth(String bankId);
   
   /**
    * 判断用户是否在某个区间注册
    * @param begindate 开始时间
    * 		 lastdate 结束时间
    * 
    * @return boolean
    * 
    * **/
   boolean verifyIdentity(int uid, Date begindate, Date lastdate);
   
}
