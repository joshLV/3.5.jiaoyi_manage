package cn.juhaowan.interfaces.service;

import java.util.HashMap;
import java.util.List;

import cn.juhaowan.interfaces.game.vo.MemberGameBind;
import cn.juhaowan.interfaces.game.vo.MemberGameBindApply;

/**
 * 绑定游戏接口
 * @author cai9911
 *
 */
public interface GameBindService {
    /**
     * 绑定游戏 账号（一个交易平台账号，针对同一个游戏 ，只能绑定一个账号
     * @param jiaoyiUid  交易平台uid
     * @param gameUid    用户在游戏的uid
     * @return 0成功，1已存在
     */
    int bindGameUser(int gameid,int jiaoyiUid,String gameUid);
    
    
    /**
     * 解除绑定接口
     * @param uid   用户id
     * @param bindId 绑定表主键ID
     * @return
     */
    int unbindGameUser(int uid, int bindId);

    /**
     * 查询用户绑定游戏uid
     * @param uid
     * @param gameid
     * @return uid和gameid下绑定的所有gameuid
     */
    int unbindGameUser(int id);
    List<String> queryBindGameUid(int uid,int gameid);
    
    
    
    /**
     * 查询游戏 账号绑定平台的账号信息
     * 
     * @param gameid
     * @param gameUid
     * @return
     */
    int queryMemberID(int gameid,String gameUid);
    
    
    /**
     * 查询用户绑定的游戏列表
     * josn: { "gameid":123,"gameName":"世界","gameUid","1234","createTime":"yyyy-MM-dd HH:mm:ss"}
     * @param uid
     * @return
     */
    HashMap<String, List<MemberGameBind>> queryBindGameList(int uid);
    
    
    /**
     * 查询用户绑定的游戏列表
     * @param uid
     * @return
     */
    List<MemberGameBind> queryBindGameListByUid(int uid);
    
    
    /**
     * 查询用户绑定的游戏列表
     * @param  gameUid
     * @return
     */
    List<MemberGameBind> queryBindGameListByGameUid(String gameUid);
    
    
    

    /**
     * 设置绑定游戏用户的昵称
     * @param gameid
     * @param uid
     * @param gameUid
     * @param nickName 
     * @return
     */
    int setBindUserNickName(int gameid, int uid, String gameUid, String nickName);
    
    
    /**
     * 设置绑定游戏用户的昵称
     * @param bindId
     * @param nickName 
     * @return
     */
    int setBindUserNickName(int bindId, String nickName);
    
    /**
     * 获取绑定用户的昵称
     * @param gameid
     * @param uid
     * @param gameUid
     * @return
     */
    String getBindUserNickName(int gameid, int uid, String gameUid);
    
    
    /**
     * 获取平台用户的某个游戏的所有绑定昵称
     * @param gameid
     * @param uid
     * @return <gameUid, nickname>
     */
    HashMap<String, String> getBindUserNickNameMap(int gameid, int uid);
    
    /**
     * 
     * @param uid
     * @param gameRoleId
     * @param gameId
     * @param areaId
     * @param vcode
     * @return
     */
    public int applyBind(int uid, String gameRoleId,  String gameRoleName, int gameId, int areaId, String vcode);
    
    
    /**
     * 删除绑定申请
     * @param uid 
     * @param gameId
     * @param gameRoleId
     * @return 0为成功，其他为失败
     */
    public int delBindApply(int uid, String gameRoleId, int gameId, int areaId);
    
    
    /**
     * 删除绑定申请
     * @param id 申请的主键ID
     * @return 0为成功，其他为失败
     */
    public int delBindApply(int id);
    
    /**
     * 查找绑定申请列表
     * @param uid
     * @return
     */
    public List<MemberGameBindApply> findApplyBindListByUid(int uid);
    
    /**
     * 查找绑定申请
     * @param id
     * @return
     */
    public MemberGameBindApply findApplyBindById(int id);
    
    
    /**
     * 查找绑定申请
     * @param uid
     * @param gameRoleId
     * @param gameId
     * @param areaId
     * @return
     */
    public MemberGameBindApply findApplyBindByInfo(int uid, String gameRoleId, int gameId, int areaId);
    
    /**
     * 查找绑定信息
     * @param id
     * @return
     */
    public MemberGameBind findBindById(int id);
    
}
