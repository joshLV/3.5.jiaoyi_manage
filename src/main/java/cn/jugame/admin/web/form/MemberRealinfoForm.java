//package cn.jugame.admin.web.form;
//
//import java.io.Serializable;
//import java.util.List;
//
//import cn.juhaowan.member.vo.MemberRealinfo;
///**
// * 
// * @author Administrator
// *
// */
//public class MemberRealinfoForm extends MemberRealinfo implements Serializable{
//
//	private static final long serialVersionUID = 5552503598505246567L;
//	
//
//	
//	/**
//	 * 付款时间
//	 */
//	private String infoPayMoneyTime;
//	
//	/**
//	 * 身份证图片正面url(none)
//	 */
//	private String infoIdImgUrl;
//	
//	/**
//	 * 身份证图片反面url(none)
//	 */
//	private String infoIdImgUrlB;
//	/**
//	 * 用户登录账号
//	 */
//   private String infoLoginid;
//   
//   /**
//    * 银行名称
//    * @return
//    */
//   private String bankName;
//   
//   /**
//    * 省份
//    */
//   private String provid;
//   /**
//    * 市区
//    */
//   private String cityid;
//   
//   /**
//    * 修改用户实名信息开关
//    */
//   private boolean modifyFlag = false;
//   
//	/**
//	 * 银行卡查看开关
//	 */
//   private boolean authorityShowBK;
//   
//   /**
//    * 银行是否快速认证的状态
//    */
//   private boolean authBankinfo = true;
//  
//   private String createTimeShow;
//   
//   private String modifyTimeShow;
//   
//   private String stayTime;
//   
//   private List<String> branchList = null;
//
//public String getInfoPayMoneyTime() {
//	return infoPayMoneyTime;
//}
//
//public void setInfoPayMoneyTime(String infoPayMoneyTime) {
//	this.infoPayMoneyTime = infoPayMoneyTime;
//}
//
//public String getInfoIdImgUrl() {
//	return infoIdImgUrl;
//}
//
//public void setInfoIdImgUrl(String infoIdImgUrl) {
//	this.infoIdImgUrl = infoIdImgUrl;
//}
//
//public String getInfoIdImgUrlB() {
//	return infoIdImgUrlB;
//}
//
//public void setInfoIdImgUrlB(String infoIdImgUrlB) {
//	this.infoIdImgUrlB = infoIdImgUrlB;
//}
//
//public String getInfoLoginid() {
//	return infoLoginid;
//}
//
//public void setInfoLoginid(String infoLoginid) {
//	this.infoLoginid = infoLoginid;
//}
//
//public String getBankName() {
//	return bankName;
//}
//
//public void setBankName(String bankName) {
//	this.bankName = bankName;
//}
//
//public String getProvid() {
//	return provid;
//}
//
//public void setProvid(String provid) {
//	this.provid = provid;
//}
//
//public String getCityid() {
//	return cityid;
//}
//
//public void setCityid(String cityid) {
//	this.cityid = cityid;
//}
//
//public boolean isModifyFlag() {
//	return modifyFlag;
//}
//
//public void setModifyFlag(boolean modifyFlag) {
//	this.modifyFlag = modifyFlag;
//}
//
//public boolean isAuthorityShowBK() {
//	return authorityShowBK;
//}
//
//public void setAuthorityShowBK(boolean authorityShowBK) {
//	this.authorityShowBK = authorityShowBK;
//}
//
//public boolean isAuthBankinfo() {
//	return authBankinfo;
//}
//
//public void setAuthBankinfo(boolean authBankinfo) {
//	this.authBankinfo = authBankinfo;
//}
//
//public String getCreateTimeShow() {
//	return createTimeShow;
//}
//
//public void setCreateTimeShow(String createTimeShow) {
//	this.createTimeShow = createTimeShow;
//}
//
//public String getModifyTimeShow() {
//	return modifyTimeShow;
//}
//
//public void setModifyTimeShow(String modifyTimeShow) {
//	this.modifyTimeShow = modifyTimeShow;
//}
//
//public String getStayTime() {
//	return stayTime;
//}
//
//public void setStayTime(String stayTime) {
//	this.stayTime = stayTime;
//}
//
//public List<String> getBranchList() {
//	return branchList;
//}
//
//public void setBranchList(List<String> branchList) {
//	this.branchList = branchList;
//}
//}
