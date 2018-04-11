package cn.juhaowan.member.service.impl;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import cn.jugame.util.Cache;
import cn.jugame.util.DateUtils;
import cn.jugame.util.PageInfo;
import cn.jugame.util.SysConfig;
import cn.jugame.yeepay.api.YeePayApi;
import cn.jugame.yeepay.api.message.IRespMsg;
import cn.juhaowan.member.dao.MemberDao;
import cn.juhaowan.member.service.MemberService;
import cn.juhaowan.member.vo.Member;
import cn.juhaowan.member.vo.MemberRealinfo;
import cn.juhaowan.util.MessageEnciphered;

/**
 * 会员服务实现
 * **/
@Service("MemberService")
public class DefaultMemberService implements MemberService  {
	
	public static String MD5_CODE = "admin";
	
	@Autowired
	private JdbcOperations jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private MemberDao memberDao;
	
	Logger logger = LoggerFactory.getLogger(DefaultMemberService.class);
	
	@Override
	public int updateLastTime(int uid){
		try{
			String sql = "update member set last_time = now() where uid = ?";
			int x = jdbcTemplate.update(sql, uid);
			return x;
		}catch (Exception e) {
			logger.error("更新用户最后登录时间失败",e);
		}
		return 0;
	}
	@Override
	public int register(String loginName, String passwd,String ip) {
		String userPass = MessageEnciphered.loginPassEnciphered(passwd);
		
		String sql = " INSERT member(login_name,login_passwd,mobile_validate,ip,create_time,status) VALUES (?,?,?,?,?,?)";
		Date createTime = new Date();
		int i = jdbcTemplate.update(sql,loginName,userPass,1,ip,createTime,1);//1正常
		i = i ^ 1;
		return i;
	}


	@Override
	public int register1(int userQuestionId, String userAnswer,String userMobile,String loginName, String passwd,String ip,String nickname, String sn, String userFrom, String userTerrace) {
		String userPass = MessageEnciphered.loginPassEnciphered(passwd);
		Date createTime = new Date();
		String sql = " INSERT member(question_id,answer,mobile,login_name,login_passwd,mobile_validate,real_validate,ip,create_time,status,user_type,nick_name,sn,user_from,user_terrace) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql,userQuestionId,userAnswer,userMobile,loginName,userPass,1,0,ip,createTime,1,1,nickname,sn,userFrom,userTerrace);
		i = i ^ 1;
		return i;
	}
	
	public int registerForQQ(String loginName, String passwd, String mobile, String ip,String nickname, String sn, String userFrom,String userTerrace){
		String userPass = null;
		if (StringUtils.isNotBlank(passwd)){
			userPass = MessageEnciphered.loginPassEnciphered(passwd);
		}
		Date createTime = new Date();
		String sql = " INSERT member(question_id,answer,mobile,login_name,login_passwd,mobile_validate,real_validate,ip,create_time,status,user_type,nick_name,sn,user_from,user_terrace) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = jdbcTemplate.update(sql,-1,"",mobile,loginName,userPass,0,0,ip,createTime,1,1,nickname,sn,userFrom,userTerrace);
		i = i ^ 1;
		return i;
	}
	
	@Override
	public int login(String loginid, String passwd) {
		String key = "login_time_" + loginid + DateUtils.getDateString();
		long x = Cache.incr(key, 3600 * 24);
		//登录锁定 次数 
		int loginCount = SysConfig.getSysParamValue("LOGIN_COUNT", 5);
		if (x > loginCount) {
			return -4;
		}
		
		String userPass = MessageEnciphered.loginPassEnciphered(passwd);
		Member member = memberDao.findMemberByLoginIdAndPwd(loginid, userPass);
		if (null == member) {
			member = memberDao.findMemberByMobileAndPwd(loginid, userPass);
		}
		if (null == member) {
			return -1;
		}
		Cache.delete(key);
		if (member.getStatus() == 1) {
			memberDao.updateMemberLastTime(member.getUid());
			//添加到缓存：因为登录下一个动作是根据 uid查询用户,避免再到数据库查询一次
			Cache.add("member/" + member.getUid(), 5, member);
			return member.getUid();
		}

		if(member.getStatus() == 2){
			//临时锁定用户
			Date today = new Date();
			Date lockDate = member.getUnlockTime();
			if(null == lockDate){
				return -3;
			}
			if(!lockDate.before(today)){
				return -2;
			}else {
				memberDao.updateMemberStatus(member.getUid(), 1);//解锁
				return member.getUid();
				}
		}
		
		if(member.getStatus() == 3){
			return -3;
		}
		return -1;
			
	}
	@Override
	public int loginUid(int uid) {
		return memberDao.loginUid(uid);
	}
	
	@Override
	public Member findMemberByName(String loginName) { 
		return memberDao.findMemberByName(loginName);
	}
	
	@Override
	public Member findMemberByMobile(String mobile) { 
		return memberDao.findMemberByMobile(mobile);
	}

	@Override
	public MemberRealinfo findRealInfoByName(String loginName) {
		
		return memberDao.findRealinfoByName(loginName);
	}

	@Override
	public Member findMemberByUid(int uid) {
		Member member = null;
		member = memberDao.findMemberByUid(uid);
		return member;
	}

	@Override
	public MemberRealinfo findRealInfoByUid(int uid) {
		
		return memberDao.findRealinfoByUid(uid);
	}

	@Override
	public int updatePasswd(int uid, String newPasswd) {
		
		String pwd  = MessageEnciphered.loginPassEnciphered(newPasswd);//两次md5
		int i = memberDao.updateMemberPasswd(uid, pwd);
		i = i ^ 1;
		return i;
	}


	@Override
	public int updateLoginName(int uid, String newLoginName) {
		return memberDao.updateLoginName(uid, newLoginName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<Member> queryMemberList(Map<String, Object> condition,int pageSize, int pageNo, String sort, String order) {
	
		if(null != condition.get("mobile")){
			condition.put("mobile", condition.get("mobile"));
		}
		if(null != condition.get("uid")){
			condition.put("uid", condition.get("uid"));
		}
		if(null != condition.get("login_name")){
			condition.put("login_name", condition.get("login_name"));
		}
		if(null != condition.get("userTerrace")){
			condition.put("userTerrace", condition.get("userTerrace"));
		}
		return memberDao.findMemberWithPage(condition, pageSize, pageNo, sort, order);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<MemberRealinfo> queryMemberRealInfoList(Map<String, Object> condition,
			int pageSize, int pageNo, String sort, String order) {
		// TODO Auto-generated method stub
		return memberDao.findMemberRealWithPage(condition, pageSize, pageNo, sort, order);
	}


	@Override
	public int updatePayPasswd(int uid, String newPayPasswd) {
		
		return memberDao.updatePayPasswd(uid, newPayPasswd);
	}

	@Override
	public int lockMember(int uid, int lockType,int day,String remark) {
		int i = 0;
		switch(lockType){
			case 3:i = memberDao.updateMemberLocking(uid, 3,remark);break;
			case 2:
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DAY_OF_MONTH, day);
				i = memberDao.updateMemberLockingByTemp(uid, 2, c.getTime(),remark);
				break;
		}
		i = i ^ 1;
		return i;
	}

	@Override
	public int bindMobile(int uid, String mobile) {
		int i = memberDao.updateMobileBindingCondition(uid, mobile);
		i =  i ^ 1;
		return i;
	}

	@Override
	public int unBindMobile(int uid) {
		int i = memberDao.updateMobileBindingCondition(uid, "");
		i =  i ^ 1; 
		return i;
	}

	@Override
	public int inputRealNameInfo(int uid, MemberRealinfo realInfo) {
		int i = 1;
		MemberRealinfo realInfo1 = memberDao.findRealinfoByUid(uid);
		if(null != realInfo1){
			int changeFiag = realInfo1.getChangeFlag();
			//更新
			realInfo.setModifyTime(new Date());
			if(changeFiag == 0 || changeFiag == 1){
				realInfo.setChangeFlag(1); //表示修改了银行信息
			}else{
				realInfo.setChangeFlag(3); //表示修改了用户信息和银行信息
			}
			
			i = memberDao.updateMemberRealinfo(uid, realInfo);
			i = i ^ 1;
		}else{
			//写入
			Random rdm = new Random();
			int payMoneyNum = rdm.nextInt(19);
			realInfo.setUid(uid);
			realInfo.setPayMoneyNum(payMoneyNum);
			realInfo.setCreateTime(new Date());
			realInfo.setModifyTime(new Date());
			i = memberDao.saveMemberRealinfo(realInfo);
			i = i ^ 1;
		}
		if(i == 0 && realInfo.getStatus() == 5){
			memberDao.updateMemberRealValidate(uid, 1); //修改member表中的实名认证
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int chekMoneyNum(int uid, int moneyNum) {
		MemberRealinfo member = memberDao.findMemberByUidAndMoneyNuM(uid, moneyNum);
		if(null != member && member.getPayMoneyNum() == moneyNum){
			Member m = memberDao.findMemberByUid(uid);
			m.setRealValidate(1);
			memberDao.update(m);
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateMemberInfo(Member member) {
		Member m = memberDao.findMemberByUid(member.getUid());
		if(null == m){
			return 1;
		}
		m.setQq(member.getQq());
		m.setEmail(member.getEmail());
		m.setBirthday(member.getBirthday());
		m.setNickName(member.getNickName());
		memberDao.update(m);
		return 0;
	}

	@Override
	public int checkAnswer(int uid ,int questionid, String answer) {
		Member m = memberDao.findMemberByUid(uid);
		
		
		if(null != m){
			if(m.getQuestionId() == questionid && m.getAnswer().equals(answer)){
				return 0;
			}
			if(m.getQuestionId() == questionid && !m.getAnswer().equals(answer)){
				return 1;
			}
			if(m.getAnswer() == null || m.getAnswer().equals("")){
				return 2;
			}
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateAnswer(int uid,int questionid, String answer) {
		Member m = memberDao.findMemberByUid(uid);
		
		if(null != m){
			m.setAnswer(answer);
			m.setQuestionId(questionid);
			try{
				memberDao.update(m);
				return 0;
			}catch (Exception e) {
				return 1;
			}
		}
		return 0;
	}


	@Override
	public int updateMemberRealInfoStatus(int uid, int status) {
		MemberRealinfo m = memberDao.findRealinfoByUid(uid);
		int formerStatus = m.getStatus();//之前的状态
		int i = 0;
		int j = 0;
		if(null != m){
			try{
				i = memberDao.updateMemberRealInofStatus(uid, status);
				if(i == 1 && status == 5){
					j = memberDao.updateMemberRealValidate(uid, 1);
					if(j == 0){
						memberDao.updateMemberRealInofStatus(uid, formerStatus);
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}


	@Override
	public int updateMemberStatus(int uid, int status) {
		Member m = memberDao.findMemberByUid(uid);
		if(null != m){
			try{
				memberDao.updateMemberStatus(uid, status);
				return 0;
			}catch (Exception e) {
				return 1;
			}
		}
		return 0;
	}


	@Override
	public int updateRealNameInfo(int uid, Date approveTime) {
		int i = memberDao.updateRealNameInfo(uid, approveTime);
		i =  i ^ 1;
		return i;
	}


	@Override
	public Boolean findMobile(String mobile) {
		return memberDao.findMobile(mobile);
	}
	
	@Override
	public int updateMemberRealInfoStatus(int uid, int status,String remark) {
		MemberRealinfo m = memberDao.findRealinfoByUid(uid);
		if(null != m){
			try{
				memberDao.updateMemberRealInofStatus(uid, status,remark);
				String sql = "UPDATE `member` SET `real_validate` = ? where uid = ?";
				if(status == 5){
					jdbcTemplate.update(sql,1,uid);
				}
				if(status == 7){
					jdbcTemplate.update(sql,0,uid);
				}
				
				return 0;
			}catch (Exception e) {
				return 1;
			}
		}
		return 0;
	}
	
	@Override
	public int updateMemberStatus(int uid, int status,String remark) {
		Member m = memberDao.findMemberByUid(uid);
		if(null != m){
			try{
				memberDao.updateMemberStatus(uid, status,remark);
				return 0;
			}catch (Exception e) {
				return 1;
			}
		}
		return 0;
	}


	@Override
	public boolean payPassChecking(int uid, String payPass) {
		Member member = findMemberByUid(uid);
		String paymentPass1 = MessageEnciphered.loginPassEnciphered(payPass);
		if (paymentPass1.equals(member.getPayPasswd())) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Integer> queryAllMemberID() {
		String sql = "select uid from member where status=1";
		List<Integer> memberIdList = jdbcTemplate.queryForList(sql,Integer.class);
		return memberIdList;
	}


	@Override
	public Member findMemberByUid_back(int uid) {
		Member member = null;	
		member = memberDao.findMemberByUid(uid);
		return member;
	}


	@Override
	public Member findMemberByNickname(String nickName) {
		return memberDao.findMemberByNickname(nickName);
	}


	@Override
	public Boolean findIDcard(String idCard) {
		return memberDao.findIDcard(idCard);
	}


	@Override
	public int updateUserType(int uid, int identity) {
		return memberDao.updateUserType(uid, identity);
	}


	@Override
	public String findNicknameByUid(int uid) {
		
		String key = "findNicknameByUid" + uid;
		String nickName = Cache.get(key);
		if(null != nickName){
			return nickName;
		}
		
		nickName = memberDao.findNicknameByUid(uid);
		if(null != nickName){
			Cache.set(key, 300, nickName);
		}
		return nickName;
	}


	@Override
	public boolean findAuthenticationByUid(int uid) {
		String key = "findAuthenticationByUid" + uid;
		String str = Cache.get(key);
		if(null != str){
			if(str.equals("true")){
				return true;
			}else {
				return false;
			}
		}
		boolean b = memberDao.findAuthenticationByUid(uid);
		if(b) {
			Cache.set(key, 3600 * 24, "true");
		} else {
			Cache.set(key, 300, "fasle");
		}
		return b;
	}

	@Override
	public boolean findPaymentCodeByUid(int uid) {
		boolean b = memberDao.findPaymentCodeByUid(uid);
		return b;
	}

	@Override
	public boolean isSetLoginPass(int uid) {
		return memberDao.isSetLoginPass(uid);
	}
	
	
	@Override
	public Boolean findIDcard2(int uid, String idCard) {
		return memberDao.findIDcard2(uid, idCard);
	}


	@Override
	public boolean resetPayPass(int uid) {
		return memberDao.resetPayPass(uid);
	} 
	
	/**
    * 是否锁定登录状态
    * @param uid
    * @return
    */
	@Override
	public boolean isLoginLock(int uid){
		Member member = memberDao.findMemberByUid(uid);
		if(member == null){
			return false;
		}
		try{
			String loginid = member.getLoginName();
			
			String key = "_login_error_counter_" + loginid;
			int count = Cache.get(key);
			if (count == 0){
				String mobile = member.getMobile();
				key = "_login_error_counter_" + mobile ;
				count = Cache.get(key);
				if(count == 0){
					return false;
				}
			}
			//long x = Long.parseLong(str);
			//登录锁定 次数 
			 int loginCount = SysConfig.getSysParamValue("LOGIN_COUNT", 5);
			if (count > loginCount){
				return true;
			}
		}catch (Exception e) {
			logger.error("",e);
		}
		return false;		
	}
   
	/**
    * 是否锁定 支付状态（支付密码）
    * @param uid
    * @return
    */
	@Override
	public boolean isPayLock(int uid){
		String key = "user/paypasswd/error/" + uid + "/" + DateUtils.getDateString();
		String str = Cache.get(key);
		if (str == null){
			return false;
		}
		try{
			int defaultErrTime = SysConfig.getSysParamValue("PAYPWD_ERROY_MAX_TIEM", 3);
			long x = Long.parseLong(str);
			if (x > defaultErrTime){
				return true;
			}
		}catch (Exception e) {
			logger.error("",e);
		}
		return false;
	} 
	
//	@Override
//	public IRespMsg yeepayRealInfo(String uid, String timeTamp, String realName, String idCardType, String idCardNo, String desc) {
//		IRespMsg result = null;
//		try {
//			result = YeePayApi.setAuthStatus(String.valueOf(uid),timeTamp, realName, idCardType, idCardNo,desc);
//			return result;
//		} catch (IOException e) {
//		    logger.error(uid + "同步易宝实名认证出错,出错原因 " + e);
//		}
//		return result;
//	}


	@Override
	public IRespMsg yeepayQueryAccount(String userIdentityNumber, String desc, int merchantAccountFlag) {
		
		IRespMsg result = null;
		try {
			result = YeePayApi.queryAccount(userIdentityNumber, desc, merchantAccountFlag);
			return result;
		} catch (IOException e) {
		    logger.error(userIdentityNumber + "查询易宝账号详情出错,出错原因" + e);
		}
		return result;
	}


    @Override
    public String findMobileByUid(int uid) {
        return memberDao.findMobileByUid(uid);
    } 


    @Override
    public int isVIP(int uid) {
        int i = 1;
        String sql ="SELECT user_type FROM member WHERE uid = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, uid);
        while(rs.next()){
            i = rs.getInt(1);
        }
        return i;
	}


	@Override
	public boolean checkLoginPwd(int uid, String loginpwd) {
		String encryptloginpwd = MessageEnciphered.loginPassEnciphered(loginpwd);
		return memberDao.checkLoginPwd(uid, encryptloginpwd);
	}


	@Override
	public IRespMsg yeepaySetUpBankCard(String uid, String requestId, String bankCardNumber, String branchName,
			String mobile, String province, String city, String desc,String branchinfo) {
		IRespMsg result = null;
		try {
			result = YeePayApi.setUpBankCard(uid, requestId, bankCardNumber, branchName, mobile, province, city, desc,branchinfo);
		} catch (IOException e) {
			logger.error(uid + "设置用户银行卡号出错,出错原因" + e);
		}
		return result;
	}
	@Override
	public List<Map<String,Object>> findMemberRealinfo() {
		String sql = "SELECT uid, real_name, (SELECT bank_code_name FROM bankinfo WHERE bank_code = bank_id) 'bankcode', id_num, bank_card_num, bank_addr, provinces_code FROM member_realinfo";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}


	@Override
	public boolean updateChangeflagByUid(int uid, int changeflag) {
		return memberDao.updateChangeflagByUid(uid, changeflag);
	}


	@Override
	public int qeuryBankInfoAuth(String bankId) {
        int result = -1;
		String sql = "SELECT auth FROM bankinfo where bank_code = ? ";
        SqlRowSet re = jdbcTemplate.queryForRowSet(sql,bankId);
       
        while(re.next()){
        	result = re.getInt(1);
        }
		return result;
	}


	@Override
	public boolean verifyIdentity(int uid, Date begindate, Date lastdate) {
		String sql = "SELECT create_time FROM member WHERE uid = ?";
		SqlRowSet re = jdbcTemplate.queryForRowSet(sql,uid);
        while(re.next()){
        	Date createtime = re.getDate(1);
        	if(createtime.after(begindate) && createtime.before(lastdate)){
        		return true;
        	}
        }
		return false;
	}



	
	
	
}
