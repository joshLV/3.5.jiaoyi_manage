package cn.juhaowan.customer.vo;

import java.util.Date;
import cn.jugame.dal.annotation.*;

@Table(name="customer_service_duty")
public class CustomerServiceDuty implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id",type=DbType.Int)
	private int id;
	
	@Column(name = "duty_id",type=DbType.Int)
	private int dutyId;
	
	@Column(name = "duty_subid",type=DbType.Int)
	private int dutySubid;
	
	@Column(name = "customer_service_id",type=DbType.Int)
	private int customerServiceId;
	
	@Column(name = "busness_type",type=DbType.Int)
	private int busnessType;
	
	
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDutyId() {
		return dutyId;
	}

	public void setDutyId(int dutyId) {
		this.dutyId = dutyId;
	}

	public int getDutySubid() {
		return dutySubid;
	}

	public void setDutySubid(int dutySubid) {
		this.dutySubid = dutySubid;
	}

	public int getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(int customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public int getBusnessType() {
		return busnessType;
	}

	public void setBusnessType(int busnessType) {
		this.busnessType = busnessType;
	}
	

}