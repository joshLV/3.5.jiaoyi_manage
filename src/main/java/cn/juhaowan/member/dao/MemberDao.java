package cn.juhaowan.member.dao;

import java.util.Date;
import java.util.Map;

import cn.jugame.util.PageInfo;
import cn.juhaowan.member.vo.Member;
import cn.juhaowan.member.vo.MemberRealinfo;
/**
 * 会员dao接口
 * @param <T>
 * **/
public interface MemberDao<T> {
	
	
	/**
	 * 根据会员id查询会员信息
	 * 
	 * @author Administrator
	 * @param uid 会员id
	 * @return  如果  uid 小于0 ，则返回null。查询成功返回对应的 member，否则返回null.
	 * 
	 * **/
	public Member findMemberByUid(int uid);
	
	/**
	 * 根据登录名查询会员信息
	 * 
	 * @author Administrator
	 * @param loginName 登录名
	 * @return 如果 loginName 等于null或等于空串 ，则返回null。查询成功返回对应的 member，否则返回null
	 * 
	 * **/
	public Member findMemberByName(String loginName);
	
	/** 
    * 根据用户手机号查询用户信息
    * 
    * @param mobile 手机号
    * @pdOid 215a632e-46fa-4652-b84f-79f34972598c */
   Member findMemberByMobile(String mobile);
	
	
	
	/**
	 * 根据会员id查询实名信息
	 * 
	 * @author Administrator
	 * @param uid 会员id
	 * @return 如果  uid 小于0 ，则返回null。查询成功返回对应的 memberRealinfo，否则返回null.
	 * 
	 * **/
	public MemberRealinfo findRealinfoByUid(int uid);
	
	/**
	 * 根据登录名查询实名信息
	 * 
	 * @author Administrator
	 * @param loginName 登录名
	 * @return 如果 loginName 等于null或等于空串 ，则返回null。查询成功返回对应的 memberRealinfo，否则返回null.
	 * 
	 * **/
	public MemberRealinfo findRealinfoByName(String loginName);
	
	
	/**
	 * 根据uid查询用户状态
	 * 
	 * */
	public int loginUid(int uid);
	
	
	/**
	 * 根据会员id更新支付密码
	 * @author Administrator
	 * @param uid 会员id
	 * @param payPwd  支付密码
	 * @return 1失败，0成功
	 * */
	public int updatePayPasswd(int uid,String payPwd);
	

	/**
	 * 根据账号和密码查询会员信息
	 * @author Administrator
	 * @param loginId 会员账号
	 * @param passwd  登录密码
	 * @return 如果 loginId 或  passwd 等于null或等于空串 ，则返回null。查询成功返回对应的 member，否则返回null.
	 * */
	public Member findMemberByLoginIdAndPwd(String loginId,String passwd);
	
	
	/**
	 * 根据手机号查询
	 * @param mobile
	 * @param passwd
	 * @return
	 */
	public Member findMemberByMobileAndPwd(String mobile,String passwd);
	
	
	/**
	 * @author Administrator
	 * @param modle 更新的信息
	 * */
	public void update(T modle);
	
	/**
	 * 根据会员id更新会员锁定类型
	 * @author Administrator
	 * @param uid  会员id
	 * @param status 锁定类型  1正常,2 临时锁定,3 锁定
	 * @return 1 失败 ,0 成功
	 * 
	 * */
	public int updateMemberLocking(int uid,int status,String remark);
	
	/**
	 * 根据会员id更新会员锁定类型
	 * @author Administrator
	 * @param uid  会员id
	 * @param status 锁定类型  1正常,2 临时锁定,3 锁定
	 * @return 1 失败 ,0 成功
	 * 
	 * */
	public int updateMemberLockingByTemp(int uid,int status,Date day,String remark);
	
	
	/**
	 * 根据会员id更新会员登录密码
	 * @author Administrator
	 * @param uid 会员id
	 * @param pwd 新密码
	 * @return  
	 * */
	public int updateMemberPasswd(int uid,String pwd);
	
	
	/**
	 * 分页查询会员信息
	 * @author Administrator
	 * @param condition 查询条件
	 * @param pageSize 页记录数
	 * @param pageNo 当前页
	 * @param sort 排序字段
	 * @param order 升序/降序 
	 * @return 分页会员信息
	 * 
	 * */
	public PageInfo<Member> findMemberWithPage(Map<String, Object> condition,int pageSize, int pageNo, String sort, String order);
	
	/**
	 * 保存会员信息
	 * @author Administrator
	 * @return 返回作用记录数
	 * */
	public int saveMemberRealinfo(MemberRealinfo memberRealinfo);
	
	/**
	 * 更新用户最后登录时间
	 * @param uid
	 * @return 返回更新记录数
	 */ 
	public int updateMemberLastTime(int uid);
	
	/**
	 * 根据会员id更新会员信息
	 * @param uid 会员id
	 *  @return 返回作用记录数
	 * */
	public int updateMemberRealinfo(int uid, MemberRealinfo memberRealinfo);
	/**
	 * 根据会员id保存会员手机号
	 * @author Administrator
	 * @param uid 会员id
	 * @param mobile 手机号
	 * @return 返回作用记录数 ，0 表示0条记录作用，1 表示1条记录作用。
	 * */
	public int updateMobileBindingCondition(int uid,String mobile);
	
	
	/**
	 * 根据会员id和打款金额查询会员
	 * @author Administrator
	 * @param uid 会员id
	 * @param moneyNum 打款金额(单位：分)
	 * @return 如果 uid 或  moneyNum 小于 0 ，则返回null。查询成功返回对应的memberRealinfo，否则返回null.
	 *
	 * 
	 * */
	public MemberRealinfo findMemberByUidAndMoneyNuM(int uid, int moneyNum);
	
	
	/**
	 * 更新会员的安全问题与答案
	 * @author Administrator
	 * @param uid 会员id
	 * @param questionId 安全问题id
	 * @param answer 答案
	 * @return 返回作用记录数
	 * */
	public int updateAnswerNQuestionOfMember(int uid ,int questionId,String answer);
	
	/**
	 * 分页查询会员实名信息
	 * @author Administrator
	 * @param condition 查询条件
	 * @param pageSize 页记录数
	 * @param pageNo 当前页
	 * @param sort 排序字段
	 * @param order 升序/降序 
	 * @return 分页会员信息
	 * 
	 * */
	public PageInfo<MemberRealinfo> findMemberRealWithPage(Map<String, Object> condition,int pageSize, int pageNo, String sort, String order);
	
	
	
	/**
	 * 根据会员id更新会员信息实名认证(修改用户真实信息表--MemberRealInof)
	 * @author Administrator
	 * @param uid 会员id
	 * @param status 新状态
	 * @return  
	 * */
	public int updateMemberRealInofStatus(int uid,int status);
	
	/**
	 * 根据会员id更新会员信息实名认证（修改用户信息表-->member）
	 * @author Administrator
	 * @param uid 会员id
	 * @param status 新状态
	 * @return  
	 * */
	public int updateMemberRealValidate(int uid,int status);
	
	/**
	 * 根据会员id 确认锁定用户账号
	 * @param uid 会员id
	 * @param status 新状态
	 * @return  
	 * */
	public int updateMemberStatus(int uid,int status);
	
	
	/** 
    * 修改实名信息(认证成功修改认证时间）
    * 0 成功
    * 1 失败
    * @param uid 用户账号
    * @param approveTime 认证成功时间
    */
	int updateRealNameInfo(int uid, Date approveTime);
	/** 
    * 检查用户输入的手机号是否唯一
    * true 唯一
    * false 已存在
    * 
    * @param mobile 手机号
    */
	Boolean findMobile(String mobile);
	
	/** 
    * 检查用户生分证是否唯一
    * true 唯一
    * false 已存在
    * 
    * @param idCard 生分证号
    */
	Boolean findIDcard(String idCard);
	
	
	/**
	 * 根据会员id更新会员信息实名认证(后台)
	 * @author Administrator
	 * @param uid 会员id
	 * @param status 新状态
	 * @param remark 审核备注
	 * @return  
	 * */
	public int updateMemberRealInofStatus(int uid,int status,String remark);
	
	/**
	 * 根据会员id 确认锁定用户账号(后台)
	 * @param uid 会员id
	 * @param status 新状态
	 * @param remark 审核备注
	 * @return  
	 * */
	public int updateMemberStatus(int uid,int status,String remark);
	
	/**
	 * 根据昵称查询会员信息
	 * @author Administrator
	 * @param nickName 昵称
	 * @return 如果 nickName 等于null或等于空串 ，则返回null。查询成功返回对应的 member，否则返回null
	 * 
	 **/
	  public Member findMemberByNickname(String nickName);
	  
	  /**
	   * 修改用户身份（后台） 
	   * @param uid 用户id
	   * 0 失败 
	   * 1 成功
	   **/
	  int updateUserType(int uid, int identity);
	  
	  /**
	    * 根据uid查询昵称
	    * @param uid 用户id
	    * */
	  String findNicknameByUid(int uid);
	  
	  /**
	    * 根据uid查询用户是否实名认证
	    * @param uid 用户id
	    * true 已实名认证
	    * false  未实名认证
	    **/
	   boolean findAuthenticationByUid(int uid);
	   
	   /**
	    * 根据uid查询用户是否设置支付密码
	    * @param uid 用户id
	    * true 已设置
	    * false  未设置
	    **/
	   boolean findPaymentCodeByUid(int uid);
	   
	   
	   /**
	    * 判断用户是否设置了登陆密码
	    * @param uid 用户id
	    * true 已设置
	    * false  未设置
	    **/
	   boolean isSetLoginPass(int uid);
	   
	   /** 
	    * 检查分证是否被使用(排除自己)
	    * @param idCard 身份证号
	    * true 唯一
	    * false 已存在
	    * 
	    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74 */
	   Boolean findIDcard2(int uid, String idCard);
	   /**
	    * 重置用户支付密码
	    * @param uid 用户id
	    * true 已设置
	    * false  未设置
	    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
	   boolean resetPayPass(int uid);
	   
	   /**
	    * 查询用户的手机号
	    * @param uid
	    * **/
	   String findMobileByUid(int uid);
	   
	   /** 
	    * 更新用户名
	    * 
	    * @param uid uid
	    * @param newUserName 新的用户名
	    * @pdOid 260a8501-0f06-471e-bf98-79ea2e68a59c */
	   int updateLoginName(int uid, String newLoginName);
	   
	   /**
	    *验证登录密码是否正确 
	    * 
	    * @param uid 用户uid
	    * @param loginpwd 密码
	    * 
	    * @return true 正确  false 错误
	    * **/
	   public boolean checkLoginPwd(int uid, String loginpwd);
	   
	   /**
	    * 修改用户实名信息是否有改动 
	    * @param uid 用户id
	    * @pdOid 268ef521-c671-43e4-bf55-e475b6f43a74*/
	   boolean updateChangeflagByUid(int uid, int changeflag);
}
