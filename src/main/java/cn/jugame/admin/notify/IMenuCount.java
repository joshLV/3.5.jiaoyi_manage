package cn.jugame.admin.notify;

public interface IMenuCount {

	public String className = "";

	/**
	 * 读取待处理数量
	 * 
	 * @param kefuId
	 * @return
	 */
	public int getCount(int kefuId, int userType);

}
