package cn.juhaowan.member.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.jugame.dal.callback.JuRowCallbackHandler;
import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.jugame.util.PageInfo;
import cn.juhaowan.member.dao.MemberDao;
import cn.juhaowan.member.vo.Member;
import cn.juhaowan.member.vo.MemberRealinfo;
import cn.juhaowan.util.MessageEnciphered;

/**
 * 会员dao接口实现
 * **/
@SuppressWarnings("rawtypes")
@Repository("MemberDao")
public class MemberDaoImpl extends BaseDaoImplJdbc implements MemberDao {

	private static final Logger log = LoggerFactory.getLogger(MemberDaoImpl.class);
	Logger logger = org.slf4j.LoggerFactory.getLogger(MemberDaoImpl.class);
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Override
	public Member findMemberByUid(int uid) {
		Member member = null;
		if (uid < 0) {
			return member;
		}
		String sql = " select * from member m where m.uid = ? ";
		JuRowCallbackHandler<Member> mrch = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sql, mrch, uid);
		List<Member> members = mrch.getList();

		if (members.size() > 0){
			member = members.get(0);
		}
		return member;
	}

	@Override
	public Member findMemberByName(String loginName) {
		Member member = null;
		if (loginName == null || loginName.equals("")) {
			return member;
		}
		String sql = " select * from member m where m.login_name = ? ";
		JuRowCallbackHandler<Member> mrch = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sql, mrch, loginName);
		List<Member> members = mrch.getList();
		if (members.size() > 0){
			member = members.get(0);
		}
		return member;
	}


	@Override
	public Member findMemberByMobile(String mobile) {
		Member member = null;
		if (StringUtils.isBlank(mobile)) {
			return member;
		}
		String sql = " select * from member m where m.mobile = ? ";
		JuRowCallbackHandler<Member> mrch = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sql, mrch, mobile);
		List<Member> members = mrch.getList();
		if (members.size() > 0){
			member = members.get(0);
		}
		return member;
	}
	
	
	@Override
	public MemberRealinfo findRealinfoByUid(int uid) {
		MemberRealinfo memberRealinfo = null;
		if (uid < 0) {
			log.info("uid=" + uid);
			return memberRealinfo;
		}
		String sql = " select * from member_realinfo m where m.uid = ? ";
		JuRowCallbackHandler<MemberRealinfo> mrirch = new JuRowCallbackHandler<MemberRealinfo>(MemberRealinfo.class);
		jdbcOperations.query(sql, mrirch, uid);
		List<MemberRealinfo> memberRealinfos = mrirch.getList();

		if (memberRealinfos.size() > 0){
			memberRealinfo = memberRealinfos.get(0);
		}
		return memberRealinfo;
	}

	@Override
	public MemberRealinfo findRealinfoByName(String loginName) {
		MemberRealinfo memberRealinfo = null;
		if (loginName == null || loginName.equals("")) {
			log.info("loginName="  + loginName);
			return memberRealinfo;
		}
		String sql = " select * from member_realinfo m where m.real_name = ? ";
		JuRowCallbackHandler<MemberRealinfo> mrirch = new JuRowCallbackHandler<MemberRealinfo>(MemberRealinfo.class);
		jdbcOperations.query(sql, mrirch, loginName);
		List<MemberRealinfo> memberRealinfos = mrirch.getList();
		if (memberRealinfos.size() > 0){
			memberRealinfo = memberRealinfos.get(0);
		}
		return memberRealinfo;
	}

	@Override
	public int updatePayPasswd(int uid, String payPwd) {
		int i = 1;
		if (uid < 0 || payPwd == null || payPwd.equals("")){
			return 1;
		}
		String sql = " update member set pay_passwd = ? where uid = ? ";
		try {
			i = jdbcOperations.update(sql, payPwd, uid);
			return i;
		} catch (Exception e) {
			log.error("Can not update payPwd of uid: " + uid + ",because MemberDaoImpl.updatePayPasswd()  has happend error!");
		}
		return i;
	}

	@Override
	public Member findMemberByLoginIdAndPwd(String loginId, String passwd) {
		Member member = null;
		if (loginId == null || loginId.equals("") || passwd == null
				|| passwd.equals("")) {
			return member;
		}
		String sql = " select * from member m where m.login_name =? and m.login_passwd = ? ";
		JuRowCallbackHandler<Member> mrch = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sql, mrch, loginId, passwd);
		List<Member> members = mrch.getList();

		if (members.size() > 0){
			member = members.get(0);
		}
		return member;
	}
	@Override
	public Member findMemberByMobileAndPwd(String loginId, String passwd) {
		Member member = null;
		if (loginId == null || loginId.equals("") || passwd == null
				|| passwd.equals("")) {
			return member;
		}
		String sql = " select * from member m where m.mobile =? and m.login_passwd = ? ";
		JuRowCallbackHandler<Member> mrch = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sql, mrch, loginId, passwd);
		List<Member> members = mrch.getList();

		if (members.size() > 0){
			member = members.get(0);
		}
		return member;
	}
	 
	
	
	@Override
	public PageInfo<Member> findMemberWithPage(Map conditions, int pageSize, int pageNo,String sort, String order) {
	
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		if (null == sort) {
			sort = "uid";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append(" from member where 1=1 ");

		if (null != conditions) {
			if (StringUtils.isNotBlank((String)conditions.get("uid"))) {
				sql.append(" and uid = ?");
				conList.add(conditions.get("uid"));
			}
			if (StringUtils.isNotBlank((String)(conditions.get("mobile")))) {
				sql.append(" and mobile like ?"); 
				conList.add("%" + conditions.get("mobile") + "%");
			}
			if (StringUtils.isNotBlank((String)conditions.get("login_name"))) {
				sql.append(" and login_name like ?");
				conList.add("%" + conditions.get("login_name") + "%");
			}
			if (StringUtils.isNotBlank((String)conditions.get("beginTime"))) {
				sql.append(" and create_time >= ? ");
				conList.add(conditions.get("beginTime"));
			}
	        if (StringUtils.isNotBlank((String)conditions.get("endTime"))) {
	        	sql.append(" and create_time <= ? ");
				conList.add(conditions.get("endTime"));
			}
	        
			if (StringUtils.isNotBlank((String)conditions.get("loginbtime"))) {
				sql.append(" and last_time >= ? ");
				conList.add(conditions.get("loginbtime"));
			}
	        if (StringUtils.isNotBlank((String)conditions.get("loginetime"))) {
	        	sql.append(" and last_time <= ? ");
				conList.add(conditions.get("loginetime"));
			}
	        
	        if (StringUtils.isNotBlank((String)conditions.get("sn"))) {
	        	sql.append(" and sn = ? ");
				conList.add(conditions.get("sn"));
			}
	        if (StringUtils.isNotBlank((String)conditions.get("user_type"))) {
	        	sql.append(" and user_type = ? ");
				conList.add(conditions.get("user_type"));
			}
	        if(StringUtils.isNotBlank((String)conditions.get("userTerrace"))){
	        	sql.append(" and user_terrace like ?");
	        	conList.add("%" + conditions.get("userTerrace") + "%");
	        }

		
		}
		sql.append(" order by " + sort + " " + order);
		String sqlcount = "select count(uid)  " + sql.toString();
		@SuppressWarnings("deprecation")
		int count = jdbcOperations.queryForInt(sqlcount, conList.toArray());
		PageInfo<Member> pageInfo = new PageInfo<Member>(count,pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		//pageInfo.setPageSize(pageSize);
		
		if (count == 0){
			return pageInfo;
		}

		if (pageNo <= 0){
			pageNo = 1;
		}
		
		if (pageNo > pageInfo.getPageCount()){
			pageNo = pageInfo.getPageCount();
		}
		
		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		if (firstResult < 0){
			firstResult = 0;
		}

		String sqlPage = "select * " + sql.append(" limit " + firstResult + "," + pageInfo.getPageSize()).toString();

		JuRowCallbackHandler<Member> rowCallbackHandler = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sqlPage, rowCallbackHandler, conList.toArray());
		pageInfo.setItems(rowCallbackHandler.getList());
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object modle) {
		try{
			super.update(modle);
		}catch (Exception e) {
			log.error("Can not update modle,because MemberDaoImpl.update(T) method has happend error!");
			throw new RuntimeException(e.getMessage() + " Can not update modle,because MemberDaoImpl.update(T) method has happend error!");
			
		}
	}
	@Override
	public int updateMemberLastTime(int uid){
		String sql = " update member set last_time = NOW() where uid = ? ";
		int i = 0;
		try{
			i = jdbcOperations.update(sql,uid);
		}catch (Exception e) {
			log.error("Can not update status,because MemberDaoImpl.updateMemberLocking(uid,status) method has happend error!");
		}
		return i;
	}
	@Override
	public int updateMemberLocking(int uid, int status,String remark) {
		int i = 0;
		if(uid < 0 || status < 0){
			return 0;
		}
		String sql = " update member set status = ? ,remark = ? where uid = ? ";
		try{
			i = jdbcOperations.update(sql, status,remark,uid);
		}catch (Exception e) {
			log.error("Can not update status,because MemberDaoImpl.updateMemberLocking(uid,status,remark) method has happend error!");
		}
		
		return i;
	}
	
	@Override
	public int updateMemberLockingByTemp(int uid, int status, Date day,String remark) {
		int i = 0;
		if(uid < 0 || status < 0){
			return 0;
		}
		String sql = " update member set status = ?, unlock_time = ? ,remark = ? where uid = ? ";
		try{
			i = jdbcOperations.update(sql, status, day,remark,uid);
		}catch (Exception e) {
			log.error("Can not update status,because MemberDaoImpl.updateMemberLocking(uid,status,remark) method has happend error!");
		}
		
		return i;
	}

	@Override
	public int updateMemberPasswd(int uid, String pwd) {
		if(uid < 0 || pwd == null || pwd.equals("")){
			return 0;
		}
		int i = 0;
		String sql = " update member set login_passwd = ? where uid = ? ";
		try{
			i = jdbcOperations.update(sql, pwd,uid);
			
		}catch (Exception e) {
			log.error("Can not update passwd of uid:" + uid + ",because MemberDaoImpl.updateMemberLocking(uid,status) method has happend error!");
		}
		
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int saveMemberRealinfo(MemberRealinfo memberRealinfo) {
		logger.info("用户[" + memberRealinfo.getRealName() + "]于" + new Date(System.currentTimeMillis()) + "第一次申请实名认证，身份证号为[" + memberRealinfo.getIdNum() + "]");
		if(null != memberRealinfo){
			try{
				super.insert(memberRealinfo);
				return 1;
			}catch (Exception e) {
				log.error("Can not save memberRealinfo,because MemberDaoImpl.saveMemberRealinfo(memberRealinfo) method has happend error!");
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateMemberRealinfo(int uid, MemberRealinfo memberRealinfo) {
		logger.info("用户[" + memberRealinfo.getRealName() + "]于" + new Date(System.currentTimeMillis()) + "重新申请实名认证，身份证号为[" + memberRealinfo.getIdNum() + "]");
		if(memberRealinfo != null && uid >= 0){
			memberRealinfo.setUid(uid);
			try{
				super.update(memberRealinfo);
				return 1;
			}catch (Exception e) {
				log.error("Can not update memberRealinfo of uid:" + uid + ",because MemberDaoImpl.updateMemberRealinfo(int,MemberRealinfo) method has happend error!");
				return 0;
			}
		}
		return 0;	
	}
	
	@Override
	public int updateMobileBindingCondition(int uid, String mobile) {
		if(uid < 0){
			return 0;
		}
		int i = 0;
		String  sql = " update member set mobile = ?,mobile_validate = ? where uid = ?   ";
		try{
			i = jdbcOperations.update(sql, mobile,1,uid);
		}catch (Exception e) {
			log.error("Can not update mobile of uid:" + uid + ",because MemberDaoImpl.updateMobileBindingConditiono(int,mobile) method has happend error!");
		}
		
		return i;
	}
	
	@Override
	public MemberRealinfo findMemberByUidAndMoneyNuM(int uid, int moneyNum) {
		MemberRealinfo memberRealinfo = null;
		if (uid < 0 || moneyNum < 0) {
			return memberRealinfo;
		}
		String sql = " select * from Member_RealInfo m where m.uid = ? and m.pay_money_num = ? ";
		JuRowCallbackHandler<MemberRealinfo> mrch = new JuRowCallbackHandler<MemberRealinfo>(MemberRealinfo.class);
		jdbcOperations.query(sql, mrch, uid,moneyNum);
		List<MemberRealinfo> members = mrch.getList();

		if (members.size() > 0){
			memberRealinfo = members.get(0);
		}
		return memberRealinfo;
	}
	
	@Override
	public int updateAnswerNQuestionOfMember(int uid,int questionId, String answer) {
		if(uid < 0 || questionId < 0 || answer == null || answer.equals("")){
			return 0;
		}
		String  sql = " update member set question_id = ?,answer = ?  where uid = ?   ";
		int i = jdbcOperations.update(sql, questionId,answer,uid);
		return i;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public int loginUid(int uid) {
		int i = 0;
		String sql = "select status from member where uid=?";
		i = jdbcOperations.queryForInt(sql, uid);
		return i;
	}

	@Override
	public PageInfo<MemberRealinfo> findMemberRealWithPage(Map conditions, int pageSize,int pageNo, String sort, String order) {
		StringBuffer sql = new StringBuffer();
		List<Object> conList = new ArrayList<Object>();
		if (null == sort) {
			sort = "uid";
		}
		if (null == order) {
			order = "asc";
		}
		sql.append(" from member_realinfo where 1=1  ");

		if (null != conditions) {
			if (StringUtils.isNotBlank((String)conditions.get("uid"))) {
				sql.append(" and uid = ?");
				conList.add(conditions.get("uid"));
			}
			if (StringUtils.isNotBlank((String)(conditions.get("id_num")))) {
				sql.append(" and id_num = ?"); 
				conList.add(conditions.get("id_num"));
			}
			if (StringUtils.isNotBlank((String)conditions.get("real_name"))) {
				sql.append(" and real_name like ?");
				conList.add("%" + conditions.get("real_name") + "%");
			}
			if(StringUtils.isNotBlank((String)conditions.get("status"))){
				sql.append(" and status = ? ");
				conList.add(conditions.get("status"));
			}
		
		}
		sql.append(" order by " + sort + " " + order);
		String sqlcount = "select count(uid)" + sql.toString();
		@SuppressWarnings("deprecation")
		int count = jdbcOperations.queryForInt(sqlcount, conList.toArray());
		PageInfo<MemberRealinfo> pageInfo = new PageInfo<MemberRealinfo>(count,pageSize);
		pageInfo.setRecordCount(count);
		pageInfo.setPageno(pageNo);
		//pageInfo.setPageSize(pageSize);
		
		if (count == 0){
			return pageInfo;
		}

		if (pageNo <= 0){
			pageNo = 1;
		}
		
		if (pageNo > pageInfo.getPageCount()){
			pageNo = pageInfo.getPageCount();
		}
		
		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		if (firstResult < 0){
			firstResult = 0;
		}

		String sqlPage = "select * " + sql.append(" limit " + firstResult + "," + pageInfo.getPageSize()).toString();

		JuRowCallbackHandler<MemberRealinfo> rowCallbackHandler = new JuRowCallbackHandler<MemberRealinfo>(MemberRealinfo.class);
		jdbcOperations.query(sqlPage, rowCallbackHandler, conList.toArray());
		pageInfo.setItems(rowCallbackHandler.getList());
		return pageInfo;
	}

	@Override
	public int updateMemberRealInofStatus(int uid, int status) {
		if(uid < 0){
			return 0;
		}
		int i = 0;
		String  sql = " update member_realinfo set status = ? where uid = ?   ";
		try{
			i = jdbcOperations.update(sql,status,uid);
		}catch (Exception e) {
			log.error("Can not update status of uid:" + uid + ",because MemberDaoImpl. updateMemberRealInofStatus(int uid, int status) method has happend error!");
		}
		
		return i;
	}

	@Override
	public int updateMemberStatus(int uid, int status) {
		if(uid < 0){
			return 0;
		}
		int i = 0;
		String  sql = " update member set status = ? where uid = ?   ";
		try{
			i = jdbcOperations.update(sql,status,uid);
		}catch (Exception e) {
			log.error("Can not update status of uid:" + uid + ",because MemberDaoImpl.updateMemberStatus(int uid, int status) method has happend error!");
		}
		
		return i;
	}

	@Override
	public int updateRealNameInfo(int uid, Date approveTime) {
		int i = 0;
		String  sql = " update member_realinfo set approve_time = ? where uid = ?   ";
		i = jdbcOperations.update(sql,approveTime,uid);
		return i;
	}

	@Override
	public Boolean findMobile(String mobile) {
		String sql = "select * from member where mobile=?";
		JuRowCallbackHandler<Member> mrch = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sql, mrch, mobile);

		if(mrch.getList().size() > 0){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int updateMemberRealInofStatus(int uid, int status,String remark) {
		if(uid < 0){
			return 0;
		}
		int i = 0;
		String  sql = " update member_realinfo set status = ?, approve_time = now(),remark = ? where uid = ?   ";
		try{
			i = jdbcOperations.update(sql,status,remark,uid);
		}catch (Exception e) {
			log.error("Can not update status of uid:" + uid + ",because MemberDaoImpl. updateMemberRealInofStatus(int uid, int status,String remark) method has happend error!");
		}
		
		return i;
	}
	
	@Override
	public int updateMemberStatus(int uid, int status,String remark) {
		if(uid < 0){
			return 0;
		}
		int i = 0;
		String  sql = " update member set status = ?,remark = ? where uid = ?   ";
		try{
			i = jdbcOperations.update(sql,status,remark,uid);
		}catch (Exception e) {
			log.error("Can not update status of uid:" + uid + ",because MemberDaoImpl.updateMemberStatus(int uid, int status,String remark) method has happend error!");
		}
		
		return i;
	}

	@Override
	public Member findMemberByNickname(String nickName) {
		Member member = null;
		if(StringUtils.isBlank(nickName)){
			return member;
		}
		String sql = " select * from member m where m.nick_name = ? ";
		JuRowCallbackHandler<Member> mrch = new JuRowCallbackHandler<Member>(Member.class);
		jdbcOperations.query(sql, mrch, nickName);
		List<Member> members = mrch.getList();
		if (members.size() > 0){
			member = members.get(0);
		}
		return member;
	}

	@Override
	public Boolean findIDcard(String idCard) {
		String sql = "select * from member_realinfo where id_num=?";
		JuRowCallbackHandler<MemberRealinfo> mrch = new JuRowCallbackHandler<MemberRealinfo>(MemberRealinfo.class);
		jdbcOperations.query(sql, mrch, idCard);

		if(mrch.getList().size() > 0){
			return false;
		}
		
		return true;
	}

	@Override
	public int updateUserType(int uid, int identity) {
		String sql = " update member set user_type = ? where uid = ? ";
		int i = 0;
		try{
			i = jdbcOperations.update(sql,identity,uid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public String findNicknameByUid(int uid) {
		String sql = "select nick_name from member where uid = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			return rs.getString(1); 
		}
		return null;
	}

	@Override
	public boolean findAuthenticationByUid(int uid) {
		String sql = "select real_validate from member where uid = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			if(rs.getInt(1) == 1){
				return true; 
			}
		}
		return false;
	}

	@Override
	public int updateMemberRealValidate(int uid, int status) {
		if(uid < 0){
			return 0;
		}
		int i = 0;
		String  sql = " update member set real_validate = ? where uid = ?   ";
		try{
			i = jdbcOperations.update(sql,status,uid);
		}catch (Exception e) {
			log.error("Can not update status of uid:" + uid + ",because MemberDaoImpl. updateMemberRealInofStatus(int uid, int status) method has happend error!");
		}
		
		return i;
	}

	@Override
	public boolean findPaymentCodeByUid(int uid) {
		String sql = "select pay_passwd from member where uid = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			if(null != rs.getString(1)){
				return true; 
			}
		}
		return false;
	}
	
	
	@Override
	public boolean isSetLoginPass(int uid) {
		String sql = "select login_passwd from member where uid = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			if(null != rs.getString(1) && rs.getString(1).length() > 0){
				return true; 
			}
		}
		return false;
	}
	
	@Override
	public Boolean findIDcard2(int uid, String idCard) {
		String sql = "select * from member_realinfo where id_num=? and status = 5";
		JuRowCallbackHandler<MemberRealinfo> mrch = new JuRowCallbackHandler<MemberRealinfo>(MemberRealinfo.class);
		jdbcOperations.query(sql, mrch, idCard);

		if(mrch.getList().size() > 0){
			return false;
		}
		
		return true;
	}

	@Override
	public boolean resetPayPass(int uid) {
		String mobile = null; 
		try {
			String sql = "select mobile from member where uid = ?";
			SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
			if (rs.next()){
				mobile = rs.getString(1);
			}
			if(null != mobile){
				String payPass = mobile.substring(mobile.length() - 6,mobile.length());
				String payPass1 = MessageEnciphered.loginPassEnciphered(payPass);
				updatePayPasswd(uid, payPass1);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	@Override
	public String findMobileByUid(int uid) {
		String sql = "select mobile from member where uid = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid);
		if (rs.next()){
			if(null != rs.getString(1)){
				return rs.getString(1); 
			}
		}
		return null;
	}

	@Override
	public int updateLoginName(int uid, String newLoginName) {
		int i = 0;
		if (StringUtils.isBlank(newLoginName)){
			return 0;
		}
		String sql = " update member set login_name = ? where uid = ? ";
		try {
			i = jdbcOperations.update(sql, newLoginName, uid);
			return i;
		} catch (Exception e) {
			log.error("Can not update loginanme of uid: " + uid + ",because MemberDaoImpl.updateLoginName()  has happend error!");
		}
		return i;
	}

	@Override
	public boolean checkLoginPwd(int uid, String loginpwd) {
		if(StringUtils.isBlank(loginpwd)){
			return false;
		}
		String sql = "select login_passwd from member where uid = ? and login_passwd = ?";
		SqlRowSet rs = jdbcOperations.queryForRowSet(sql, uid, loginpwd);
		if(rs.next()){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateChangeflagByUid(int uid, int changeflag) {
		int i = 0;
		String  sql = " update member_realinfo set change_flag = ? where uid = ?";
		try {
			i = jdbcOperations.update(sql, changeflag, uid);
		} catch (Exception e) {
			return false;
		}
		return i == 0 ? false : true;
	}

}
