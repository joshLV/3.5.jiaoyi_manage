package cn.jugame.admin.web.form;

/**
 * 
 * @author Administrator
 *
 */
public class DutyForm  {


	// 1业务类型 
	private int[] businessType;

	/**上架==================BEGIN*/
	// 2 【上架】交易模式
	private int[] businessModel;
	
	// 3【上架】用户类型  
	private int[] userType;

	// 4【上架】商品类型  
	private int[] productTypeids;
	
	// 5【上架】游戏 
	private int[] gameids;
	
	/**上架==================END*/
	
	/**售中==================BEGIN*/
	
	// 2【售中】交易模式 =======
	private int[] businessModel2;
	
	// 3【售中】用户类型 
	private int[] userType2;

	// 4【售中】商品类型 
	private int[] productTypeids2;
	
	// 5【售中】游戏
	private int[] gameids2;
	
	/**售中==================END*/
	
	// 用户id
	private String uids;
	
	//用户头像
	private String headImg;
	
	public int[] getBusinessModel2() {
		return businessModel2;
	}

	public void setBusinessModel2(int[] businessModel2) {
		this.businessModel2 = businessModel2;
	}

	public int[] getUserType2() {
		return userType2;
	}

	public void setUserType2(int[] userType2) {
		this.userType2 = userType2;
	}

	public int[] getProductTypeids2() {
		return productTypeids2;
	}

	public void setProductTypeids2(int[] productTypeids2) {
		this.productTypeids2 = productTypeids2;
	}

	public int[] getGameids2() {
		return gameids2;
	}

	public void setGameids2(int[] gameids2) {
		this.gameids2 = gameids2;
	}

	// 选择多个用户名字
	private String selectName;
	
	//打开的用户角色 (客服 物服 审核员)
    private int roleType;
	
	public int[] getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int[] businessType) {
		this.businessType = businessType;
	}

	public int[] getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(int[] businessModel) {
		this.businessModel = businessModel;
	}

	public int[] getUserType() {
		return userType;
	}

	public void setUserType(int[] userType) {
		this.userType = userType;
	}

	public int[] getProductTypeids() {
		return productTypeids;
	}

	public void setProductTypeids(int[] productTypeids) {
		this.productTypeids = productTypeids;
	}

	public int[] getGameids() {
		return gameids;
	}

	public void setGameids(int[] gameids) {
		this.gameids = gameids;
	}

	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	

	
	
}
