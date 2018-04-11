/**
 * 
 */
package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.Column;
import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Table;

import java.util.Date;

@Table(name = "ty_game_account")
public class TyGameAccount implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ty_id", type = DbType.Varchar)
	private String tyId;
	
	@Column(name = "good_name", type = DbType.Varchar)
	private String goodName;
	
	@Column(name = "product_id", type = DbType.Varchar)
	private String productId;
	
	@Column(name = "product_status", type = DbType.Varchar)
	private String productStatus;
	
	@Column(name = "game_id", type = DbType.Int)
	private int gameId;
	
	@Column(name = "area_id", type = DbType.Int)
	private int aread;
	
	@Column(name = "game_account", type = DbType.Varchar)
	private String gameAccount;

	@Column(name = "game_passwd", type = DbType.Varchar)
	private String gamePassword;
	
	@Column(name = "game_safe_lock", type = DbType.Varchar)
	private String gameSafeLock;
	
	@Column(name = "vala_info", type = DbType.Varchar)
	private String valaInfo;

    @Column(name = "real_vala_info", type = DbType.Varchar)
    private String realValaInfo;

    @Column(name = "user_price", type = DbType.Int)
    private int userPrice;

    @Column(name = "real_price", type = DbType.Int)
    private int realPrice;

    @Column(name = "ext_info", type = DbType.Varchar)
    private String extInfo;

    @Column(name = "order_id", type = DbType.Varchar)
    private String orderId;

    @Column(name = "ty_type", type = DbType.Int)
    private int tyType;

    @Column(name = "status", type = DbType.Int)
    private int status;

    @Column(name = "kefu_remark", type = DbType.Varchar)
    private String kefuRemark;

    @Column(name = "wufu_remark", type = DbType.Varchar)
    private String wufuRemark;

    @Column(name = "physic_service_id", type = DbType.Int)
    private int physicServiceId;

    @Column(name = "customer_service_id", type = DbType.Int)
    private int customerServiceId;

    @Column(name = "customer_service_time", type = DbType.DateTime)
    private Date customerServiceTime;

    @Column(name = "physic_service_time", type = DbType.DateTime)
    private Date physicServiceTime;

    @Column(name = "game_channel_id", type = DbType.Int)
    private int gameChannelId;
    
	@Column(name = "new_passwd", type = DbType.Varchar)
	private String newPassword;
    
    public String getTyId() {
        return tyId;
    }

    public void setTyId(String tyId) {
        this.tyId = tyId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getAread() {
        return aread;
    }

    public void setAread(int aread) {
        this.aread = aread;
    }

    public String getGameAccount() {
        return gameAccount;
    }

    public void setGameAccount(String gameAccount) {
        this.gameAccount = gameAccount;
    }

    public String getGamePassword() {
        return gamePassword;
    }

    public void setGamePassword(String gamePassword) {
        this.gamePassword = gamePassword;
    }

    public String getGameSafeLock() {
        return gameSafeLock;
    }

    public void setGameSafeLock(String gameSafeLock) {
        this.gameSafeLock = gameSafeLock;
    }

    public String getValaInfo() {
        return valaInfo;
    }

    public void setValaInfo(String valaInfo) {
        this.valaInfo = valaInfo;
    }

    public String getRealValaInfo() {
        return realValaInfo;
    }

    public void setRealValaInfo(String realValaInfo) {
        this.realValaInfo = realValaInfo;
    }

    public int getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(int userPrice) {
        this.userPrice = userPrice;
    }

    public int getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(int realPrice) {
        this.realPrice = realPrice;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getTyType() {
        return tyType;
    }

    public void setTyType(int tyType) {
        this.tyType = tyType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKefuRemark() {
        return kefuRemark;
    }

    public void setKefuRemark(String kefuRemark) {
        this.kefuRemark = kefuRemark;
    }

    public String getWufuRemark() {
        return wufuRemark;
    }

    public void setWufuRemark(String wufuRemark) {
        this.wufuRemark = wufuRemark;
    }

    public int getPhysicServiceId() {
        return physicServiceId;
    }

    public void setPhysicServiceId(int physicServiceId) {
        this.physicServiceId = physicServiceId;
    }

    public int getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(int customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    public Date getCustomerServiceTime() {
        return customerServiceTime;
    }

    public void setCustomerServiceTime(Date customerServiceTime) {
        this.customerServiceTime = customerServiceTime;
    }

    public Date getPhysicServiceTime() {
        return physicServiceTime;
    }

    public void setPhysicServiceTime(Date physicServiceTime) {
        this.physicServiceTime = physicServiceTime;
    }

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public int getGameChannelId() {
		return gameChannelId;
	}

	public void setGameChannelId(int gameChannelId) {
		this.gameChannelId = gameChannelId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
    
    
}
