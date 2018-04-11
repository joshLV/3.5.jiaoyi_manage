//package cn.juhaowan.interfaces.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import cn.juhaowan.interfaces.dao.MemberGameBindApplyDao;
//import cn.juhaowan.interfaces.dao.MemberGameBindDao;
//import cn.juhaowan.interfaces.game.vo.MemberGameBind;
//import cn.juhaowan.interfaces.game.vo.MemberGameBindApply;
//import cn.juhaowan.interfaces.service.GameBindService;
//import cn.juhaowan.product.service.GameService;
//import cn.juhaowan.product.vo.Game;
//import cn.juhaowan.product.vo.GamePartition;
//
///**
// * 游戏绑定服务类
// * @author cai9911
// *
// */
//@Service("DefaultGameBindService")
//public class DefaultGameBindService implements GameBindService{
//    @Autowired
//    private GameService gameService;
//    
//    @Autowired
//    private MemberGameBindDao memberGameBindDao;
//    
//    @Autowired
//    private MemberGameBindApplyDao memberGameBindApplyDao;
//    
//    Logger logger = LoggerFactory.getLogger(DefaultGameBindService.class);
//    
//    @Override
//    public int bindGameUser(int gameid,int jiaoyiUid, String gameUid) {
//        return memberGameBindDao.bind(gameid, jiaoyiUid, gameUid);
//    }
//    
//    @Override
//    public int unbindGameUser(int uid, int bindId) {
//        return memberGameBindDao.unbind(uid, bindId);
//    }
//    @Override
//    public int unbindGameUser(int id){
//        return memberGameBindDao.unbind(id);
//    }
//    @Override
//    public List<String> queryBindGameUid(int uid,int gameid){
//        return memberGameBindDao.findGameUid(uid,gameid);
//    }
//    
//
//    @Override
//    public int queryMemberID(int gameid,String gameUid){
//        return memberGameBindDao.findUid(gameid, gameUid);
//    }
//    
//    
//    @Override
//    public List<MemberGameBind> queryBindGameListByUid(int uid){
//        return memberGameBindDao.findByUid(uid);
//    }
//    
//    
//    @Override
//    public List<MemberGameBind> queryBindGameListByGameUid(String gameUid){
//        return memberGameBindDao.findByGameUid(gameUid);
//    }
//    
//
//    /**
//     * 查找游戏绑定列表
//     */
//    @Override
//    public HashMap<String, List<MemberGameBind>> queryBindGameList(int uid) {
//        List<MemberGameBind> list = memberGameBindDao.findByUid(uid);
//        
//        HashMap<String, List<MemberGameBind>> map = new HashMap<String, List<MemberGameBind>>();
//        
//        for (int i = 0, listSize = list.size(); i < listSize; i++) {
//            MemberGameBind mgb = list.get(i);
//            Game game = gameService.queryGameByGameIdFront(mgb.getGameId());
//            int gameUidLen = mgb.getGameUid().length();
//            if(gameUidLen > 10) {
//            	mgb.setGameUid(mgb.getGameUid().substring(0, 4) + "..." + mgb.getGameUid().substring(gameUidLen - 5, gameUidLen - 1));
//            }
//
//            String gameName = game != null ? game.getGameName() : "";
//            
//            //设置游戏名
//            mgb.setGameName(gameName); 
//    
//            if(map.get(gameName) == null) {
//                List<MemberGameBind> l = new ArrayList<MemberGameBind>();
//                l.add(mgb);
//                map.put(gameName, l);
//            } else {
//                map.get(gameName).add(mgb);
//            }
//        }
//        
//        return map;
//    }
//    
//    @Override
//    public int setBindUserNickName(int gameid, int uid ,String gameUid, String nickName) {
//        return memberGameBindDao.setNickName(gameid, uid, gameUid, nickName);
//    }
//    
//    @Override
//    public int setBindUserNickName(int bindId, String nickName) {
//        return memberGameBindDao.setNickName(bindId, nickName);
//    }
//    
//    @Override
//    public String getBindUserNickName(int gameid, int uid, String gameUid) {
//        return  memberGameBindDao.getNickName(gameid, uid, gameUid);
//    }
//    
//    
//    @Override
//    public HashMap<String, String> getBindUserNickNameMap(int gameid, int uid) {
//        return memberGameBindDao.getNickNameMap(gameid, uid);
//    }
//    
//    @Override
//    public int applyBind(int uid, String gameRoleId, String gameRoleName, int gameId, int areaId, String vcode) {
//        return memberGameBindApplyDao.applyBind(uid, gameRoleId, gameRoleName, gameId, areaId, vcode);
//    }
//    
//    
//    @Override
//    public int delBindApply(int uid, String gameRoleId, int gameId, int areaId) {
//        return memberGameBindApplyDao.delApply(uid, gameRoleId, gameId, areaId);
//    }
//    
//    
//    @Override
//    public int delBindApply(int id) {
//        return memberGameBindApplyDao.delApply(id);
//    }
//    
//    
//    @Override
//    public List<MemberGameBindApply> findApplyBindListByUid(int uid) {
//        List<MemberGameBindApply> l = memberGameBindApplyDao.findByUid(uid);
//        if(l == null) {
//            return null;
//        }
//        
//        for(int i = 0, size = l.size(); i < size; i++) {
//            MemberGameBindApply o = l.get(i);
//            Game game = gameService.queryGameByGameIdFront(o.getGameId());
//            o.setGameName(game == null ? "" : game.getGameName());
//            
//            GamePartition area = gameService.findGamePartitionByMainKey(o.getGameId(), o.getAreaId());
//            o.setAreaName(area == null ? "" : area.getPartitionName());
//        }
//        
//        return l;
//    }
//
//    @Override
//    public MemberGameBindApply findApplyBindById(int id) {
//        MemberGameBindApply o =  memberGameBindApplyDao.findById(id);
//        if(o == null) {
//            return null;
//        }
//        
//        Game game = gameService.queryGameByGameIdFront(o.getGameId());
//        o.setGameName(game == null ? "" : game.getGameName());
//        GamePartition area = gameService.findGamePartitionByMainKey(o.getGameId(), o.getAreaId());
//        o.setAreaName(area == null ? "" : area.getPartitionName());
//        return o;
//    }
//    
//    
//    @Override
//    public MemberGameBindApply findApplyBindByInfo(int uid, String gameRoleId, int gameId, int areaId) {
//        MemberGameBindApply o = memberGameBindApplyDao.findByInfo(uid, gameRoleId, gameId, areaId);
//        
//        Game game = gameService.queryGameByGameIdFront(gameId);
//        o.setGameName(game == null ? "" : game.getGameName());
//        GamePartition area = gameService.findGamePartitionByMainKey(gameId, gameId);
//        o.setAreaName(area == null ? "" : area.getPartitionName());
//        return o;
//    }
//
//    
//    @Override
//    public MemberGameBind findBindById(int id) {
//        return memberGameBindDao.findById(id);
//    }
//    
//}
