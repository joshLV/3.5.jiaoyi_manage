package cn.jugame.admin.notify;

import java.util.List;
import java.util.Map;

public interface INotify {
	/**
	 * 读取待处理数量
	 * 
	 * @param kefuId
	 * @return
	 */
	public List<Map<String, Integer>> getNotifyCount(int kefuId, int userType);

}
