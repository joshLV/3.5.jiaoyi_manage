package cn.juhaowan.interfaces.dao;

import java.util.HashMap;
import java.util.List;

import cn.juhaowan.interfaces.game.vo.MemberGameBind;

public interface MemberGameBindDao {
	
	/**
	 * 绑定游戏账号
	 * @param gameid
	 * @param uid
	 * @param gameUid
	 * @return
	 */
	int bind(int gameid,int uid,String gameUid);
	
	
	/**
	 * 解除绑定接口
	 * @param uid
	 * @param bindId
	 * @return
	 */
	int unbind(int uid, int bindId);
	
	
	
	/**
	 * 设置昵称
	 * @param gameid
	 * @param uid
	 * @param gameUid
	 * @param nickName
	 * @return
	 */
	int unbind(int id);
	
	
	/**
	 * 设置昵称
	 * @param gameid
	 * @param uid
	 * @param gameUid
	 * @param nickName
	 * @return
	 */
	int setNickName(int gameid, int uid, String gameUid, String nickName);
	
	
	/**
	 * 设置昵称
	 * @param bindId
	 * @param nickName
	 * @return
	 */
	int setNickName(int bindId, String nickName);
	
	
	/**
	 * 获取昵称
	 * @param gameid
	 * @param uid
	 * @param gameUid
	 * @return
	 */
	String getNickName(int gameid, int uid, String gameUid);
	
	/**
	 * 获取某个用户某个游戏下的用户ID和账号的映射
	 * @param gameid
	 * @param uid
	 * @return <gameUid, nickname>
	 */
	HashMap<String, String> getNickNameMap(int gameid, int uid);
	
	
	/**
	 * 根据游戏id，游戏用户id，查找交易平台id
	 * @param gameid
	 * @param gameUid
	 * @return
	 */
	int findUid(int gameid, String gameUid);
	
	
	/**
	 * 查找游戏UID
	 * @param uid
	 * @param gameid
	 * @return
	 */
	List<String> findGameUid(int uid, int gameid);
	
	
	/**
	 * 根据UID查找用户的绑定信息
	 * @param uid
	 * @return
	 */
	List<MemberGameBind> findByUid(int uid);
	
	

	/**
	 * 根据UID查找用户的绑定信息
	 * @param gameUid
	 * @return
	 */
	List<MemberGameBind> findByGameUid(String gameUid);
	
	/**
	 * 查找对象
	 */
	MemberGameBind findById(int id);
}
