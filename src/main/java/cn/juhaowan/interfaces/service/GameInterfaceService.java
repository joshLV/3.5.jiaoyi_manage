package cn.juhaowan.interfaces.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.juhaowan.interfaces.game.vo.GameArea;
import cn.juhaowan.interfaces.game.vo.GameResponse;
import cn.juhaowan.interfaces.game.vo.GameRole;
import cn.juhaowan.interfaces.game.vo.GameServer;
import cn.juhaowan.interfaces.game.vo.GameUserInfo;

/**
 * 
 * @author lithium
 *
 */
public interface GameInterfaceService {
    /**
     * 游戏接入的api版本号 1(对就api文件0.9)
     */
    public static final int API_VERSION_1  = 1;
    
    /**
     * 游戏接入的api版本号 2(对就api文件0.9.1)
     */
    public static final int API_VERSION_2  = 2;
    
    /**
     * 发送邮件验证码类型：普通验证码
     */
    public static final int MAIL_VCODE_TYPE_COMMON = 0;
    
    /**
     * 发送邮件验证码类型:绑定游戏
     */
    public static final int MAIL_VCODE_TYPE_BIND = 1;
    
    /**
     * 发送邮件验证码类型:发布商品
     */
    public static final int MAIL_VCODE_TYPE_PUBLISH = 2;
    
    
	/**
	 * 对加密数据进行解密 
	 * @param gameid     游戏id（会根据游戏ID，找到对应的key)
	 * @param dataEnCode 加密的字符串
	 * @param sign md5签名
	 * @return
	 */
	String decodeData(int gameid,String dataEnCode ,String sign);
	
	
	/**
	 * 登录验证，返回用户信息
	 * @param uid
	 * @param sessionid
	 * @return
	 */
	GameUserInfo getUserinfo(int gameid,String uid ,String sessionid);
	
	/**
	 * 读取游戏分区列表 
	 * @param gameid 
	 * @return
	 */
	List<GameArea> getAreaList(int gameid);
	
	/**
	 * 查询区名称
	 * @param gameid
	 * @param serverid
	 * @return
	 */
	String queryAreaName(int gameid,int areaid);
	
	/**
	 * 读取服务器列表 
	 * @param gameid
	 * @param areaid
	 * @return
	 */
	List<GameServer> getServerList(int gameid,int areaid);
	
	/**
	 * 查询服务器名称
	 * @param gameid
	 * @param serverid
	 * @return
	 */
	String queryServerName(int gameid,int serverid);
	
	/**
	 * 读取用户角色列表 
	 * @param gameid
	 * @param gameUid 游戏内用户id
	 * @param areaid
	 * @param serverid
	 * @return
	 */
	List<GameRole> getRoleList(int gameid,String gameUid,int areaid,int serverid);
	
	
	/**
	 * 根据角色id，查询角色名称 
	 * @param gameid
	 * @param uid
	 * @param areaid
	 * @param serverid
	 * @param roleid
	 * @return
	 */
	GameRole queryRoleName(int gameid,int areaid,int serverid,String roleid);
	
	/**
	 * 通过角色名查询角色信息
	 * @param gameid
	 * @param areaid
	 * @param serverid
	 * @param roleName
	 * @return
	 */
	GameRole queryRoleInfo(int gameid,int areaid,int serverid,String roleName);
	/**
	 * 商品上架
	 * @param gameid
	 * @param uid
	 * @param areaid
	 * @param serverid
	 * @param roleid	角色ID
	 * @param productid	商品类型（0表示金币）
	 * @param amount	上架数量
	 * @param pid	商品上架号
	 * @return
	 */
	GameResponse productOnShelf(int gameid,String uid,int areaid,int serverid,String roleid,int productid,long amount,String pid);
	
	/**
	 * 商品下架
	 * @param gameid
	 * @param pid	商品上架号
	 * @param areaid 区id
	 * @param serverid 服务器id
	 * @param reason 下架原因
	 * @param amount 下架数量
	 * @return
	 */
	GameResponse productOffShelf(int gameid,String pid,int areaid,int serverid,String reason,long amount);
	
	/**
	 * 发货
	 * @param gameid   游戏 id
	 * @param pid		锁定的商品ID
	 * @param orderid	订单id
	 * @param serverid  服务器id
	 * @param roleid    收货人角色id
	 * @param roleName  收货人角色名
	 * @param amount    购买数量
	 * @return
	 */
	GameResponse productDeliver(int gameid,String pid,String orderid,int areaid,int serverid,String roleid,String roleName,long amount);
	
	
	
	/**
	 * 发送游戏内邮件
	 * @param gameid
	 * @param areaid
	 * @param serverid
	 * @param title
	 * @param content
	 * @return
	 */
	//boolean sendMail(int gameid,int areaid,int serverid ,String roleid,String title,String content);
	
	/**
     * 发送邮件验证码（部分游戏无法直接调用发邮件接口）
     * @param gameid 游戏id
     * @param areaid 
     * @param serverid
     * @param roleid 
     * @param vcode 验证码
     * @param type （验证码类型，0普通验证码，1绑定账号验证码，2商品上架验证码
     * @return
     */
	boolean sendMailVcode(int gameid,int areaid,int serverid ,String roleid,String vcode,int type);
	
	/**
	 * 查询用户uid
	 * @param gameid
	 * @param areaid
	 * @param serverid
	 * @param roleid
	 * @return
	 */
	String queryGameUid(int gameid,int areaid,int serverid ,String roleid);
	
	
	/**
	 * 查询用户所有角色信息（所有区服）
	 * @param gameid
	 * @param gameUid
	 * @return json数组 :{“roleid”:”Z123”,”roleName”:”角色一”,”areaid”:1234,”areaName”:”分区一”}，
	 */ 
	public JSONArray queryAllRoleList(int gameid, String gameUid);
	
	
	/**
	 * 查询用户所有角色信息（所有区服）
	 * @param gameid
	 * @param gameUid
	 * @return
	 */
	public List<GameRole> queryAllRoleList1(int gameid, String gameUid);
	/**
	 * 查询商品出锁定状态
	 * @param gameid 游戏id
	 * @param areaid 区id
	 * @param serverid 服务器id
	 * @param pid  商品编号 
	 * @return json格式，{ areaid:9,serverid:0,roleid:””,pid:”PRO-xxxxx-xxxx”,amount:xxx}
	 */
	public JSONObject queryProductInfo(int gameid,int areaid,int serverid,String pid);
	
	/**
	 * 查询订单发货信息
	 * @param orderid
	 * @param areaid 区id
     * @param serverid 服务器id
	 * @return 	{orderid:”ORD-xxxx-xxx”, roleid:””,pid:”PRO-xxxxx-xxxx”,amount:””,createTime:”yyyy-MM-dd HH:mm:ss”}
	 * @throws Exception
	 */
	public JSONObject queryOrderInfo(int gameid, int areaid,int serverid,String orderid);
	
	
}
 