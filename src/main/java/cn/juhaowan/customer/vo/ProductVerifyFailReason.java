//package cn.juhaowan.customer.vo;
//
//import java.util.Date;
//import cn.jugame.dal.annotation.*;
//
//@Table(name="product_verify_fail_reason")
//public class ProductVerifyFailReason implements java.io.Serializable {
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//	@Column(name = "id",type=DbType.Int)
//	private int id;
//	
//	@Column(name = "fail_reson",type=DbType.Varchar)
//	private java.lang.String failReson;
//	
//	@Column(name = "status",type=DbType.Int)
//	private int status;
//	
//	@Column(name = "remark",type=DbType.Varchar)
//	private java.lang.String remark;
//	
//	 
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public java.lang.String getFailReson() {
//		return failReson;
//	}
//
//	public void setFailReson(java.lang.String failReson) {
//		this.failReson = failReson;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public java.lang.String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(java.lang.String remark) {
//		this.remark = remark;
//	}
//
//}