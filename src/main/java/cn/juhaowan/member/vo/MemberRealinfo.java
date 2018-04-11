package cn.juhaowan.member.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.GenerationType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
/**
 *  @author cxb
 * **/
/**
 * @author cxb
 * @date 2014-6-23 下午2:56:44
 *
 */
@Table(name = "member_realinfo")
public class MemberRealinfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id(generationType = GenerationType.assign)
	@Column(name = "uid", type = DbType.Int)
	private int uid;

	@Column(name = "real_name", type = DbType.Varchar)
	private java.lang.String realName;

	@Column(name = "id_type", type = DbType.Int)
	private int idType;

	@Column(name = "id_num", type = DbType.Varchar)
	private java.lang.String idNum;

	@Column(name = "id_img_url", type = DbType.Varchar)
	private java.lang.String idImgUrl;

	@Column(name = "id_img_url2", type = DbType.Varchar)
	private java.lang.String idImgUrl2;

	@Column(name = "bank_id", type = DbType.Varchar)
	private java.lang.String bankId;

	@Column(name = "bank_card_num", type = DbType.Varchar)
	private java.lang.String bankCardNum;

	@Column(name = "bank_addr", type = DbType.Varchar)
	private java.lang.String bankAddr;

	@Column(name = "provinces_code", type = DbType.Varchar)
	private java.lang.String provincesCode;

	@Column(name = "pay_money_num", type = DbType.Int)
	private int payMoneyNum;

	@Column(name = "pay_money_time", type = DbType.DateTime)
	private java.util.Date payMoneyTime;

	@Column(name = "approve_time", type = DbType.DateTime)
	private java.util.Date approveTime;

	@Column(name = "status", type = DbType.Int)
	private int status;

	@Column(name = "remark", type = DbType.Varchar)
	private java.lang.String remark;

	@Column(name = "create_time", type = DbType.DateTime)
	private java.util.Date createTime;

	@Column(name = "modify_time", type = DbType.DateTime)
	private java.util.Date modifyTime;
	
	@Column(name = "pass_way", type = DbType.Int)
	private int passWay;
	
	@Column(name = "change_flag", type = DbType.Int)
	private int changeFlag;

	@Column(name = "ali_account", type = DbType.Varchar)
	private String aliAccount;

	@Column(name = "branch_info", type = DbType.Varchar)
	private String branchInfo;
	
	
	
	public String getAliAccount() {
		return aliAccount;
	}
	
	public void setAliAccount(String aliAccount) {
		this.aliAccount = aliAccount;
	}

	public int getChangeFlag() {
		return changeFlag;
	}

	public void setChangeFlag(int changeFlag) {
		this.changeFlag = changeFlag;
	}

	public int getPassWay() {
		return passWay;
	}

	public void setPassWay(int passWay) {
		this.passWay = passWay;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public java.lang.String getRealName() {
		return realName;
	}

	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public java.lang.String getIdNum() {
		return idNum;
	}

	public void setIdNum(java.lang.String idNum) {
		this.idNum = idNum;
	}

	public java.lang.String getIdImgUrl() {
		return idImgUrl;
	}

	public void setIdImgUrl(java.lang.String idImgUrl) {
		this.idImgUrl = idImgUrl;
	}

	public java.lang.String getIdImgUrl2() {
		return idImgUrl2;
	}

	public void setIdImgUrl2(java.lang.String idImgUrl2) {
		this.idImgUrl2 = idImgUrl2;
	}

	public java.lang.String getBankId() {
		return bankId;
	}

	public void setBankId(java.lang.String bankId) {
		this.bankId = bankId;
	}

	public java.lang.String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(java.lang.String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public java.lang.String getBankAddr() {
		return bankAddr;
	}

	public void setBankAddr(java.lang.String bankAddr) {
		this.bankAddr = bankAddr;
	}

	public java.lang.String getProvincesCode() {
		return provincesCode;
	}

	public void setProvincesCode(java.lang.String provincesCode) {
		this.provincesCode = provincesCode;
	}

	public int getPayMoneyNum() {
		return payMoneyNum;
	}

	public void setPayMoneyNum(int payMoneyNum) {
		this.payMoneyNum = payMoneyNum;
	}

	public java.util.Date getPayMoneyTime() {
		return payMoneyTime;
	}

	public void setPayMoneyTime(java.util.Date payMoneyTime) {
		this.payMoneyTime = payMoneyTime;
	}

	public java.util.Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(java.util.Date approveTime) {
		this.approveTime = approveTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(String branchInfo) {
		this.branchInfo = branchInfo;
	}

	
}
