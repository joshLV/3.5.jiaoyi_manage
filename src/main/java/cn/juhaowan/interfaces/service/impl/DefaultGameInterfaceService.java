//package cn.juhaowan.interfaces.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import cn.jugame.util.Cache;
//import cn.jugame.util.DES;
//import cn.jugame.util.MD5;
//import cn.juhaowan.interfaces.game.GameRequest;
//import cn.juhaowan.interfaces.game.vo.GameArea;
//import cn.juhaowan.interfaces.game.vo.GameProduct;
//import cn.juhaowan.interfaces.game.vo.GameResponse;
//import cn.juhaowan.interfaces.game.vo.GameRole;
//import cn.juhaowan.interfaces.game.vo.GameServer;
//import cn.juhaowan.interfaces.game.vo.GameUserInfo;
//import cn.juhaowan.interfaces.service.GameInterfaceService;
//import cn.juhaowan.product.service.GameService;
//import cn.juhaowan.product.vo.Game;
//
///**
// * 游戏接口服务
// * @author cai9911
// *
// */
//@Service("GameInterfaceServicer")
//public class DefaultGameInterfaceService implements GameInterfaceService {
//    
//	@Autowired
//	private GameService gameService;
//	 
//	Logger logger = LoggerFactory.getLogger(DefaultGameInterfaceService.class);
//	
//	@Override
//	public String decodeData(int gameid, String dataEnCode, String sign) {
//		
//		String mySign = MD5.encode(dataEnCode).substring(0, 8);
//		if (!mySign.equals(sign.toUpperCase())){
//			logger.warn("签名验证不通过：mySign=" + mySign + ",gameid=" + gameid + ",dataEnCode="
//						+ dataEnCode + ",sign=" + sign);
//			return null;
//		}
//		Game game = gameService.queryGameByGameIdFront(gameid);  
//		if (game == null) {
//			return null;
//		}
//		if (StringUtils.isBlank(game.getInterfaceKey())){
//			return dataEnCode;
//		} 
//		try{
//			return DES.decode(dataEnCode, game.getInterfaceKey());
//		}catch(Exception ex){
//			logger.error("",ex);
//		}
//		
//		return null;
//	}
//	
//	@Override
//	public GameUserInfo getUserinfo(int gameid, String uid, String sessionid) {
//		
//		Game game = gameService.queryGameByGameIdFront(gameid); //缓存了 ？？
//		
//		if (game == null) {
//			return null;
//		}
//		
//		try{
//			GameRequest gameRequest = new GameRequest(gameid, game.getInterfaceUrl(), game.getInterfaceKey());
//			
//			JSONObject json = gameRequest.getUserinfo(uid + "", sessionid);
//			if (json.getInt("code") != 0){
//				logger.warn("读取用户信息失败，uid=" + uid + ",sessionid=" + sessionid + ",code=" + json.getInt("code")
//							+ ",msg=" + json.getString("msg"));
//				return null;
//			}
//			GameUserInfo gameUserInfo = (GameUserInfo)JSONObject.toBean(json.getJSONObject("body"),GameUserInfo.class);
//			return gameUserInfo;
//		}catch (Exception e) {
//			logger.error("", e);
//		}
//		return null;
//	}
//
//	@Override
//	public List<GameArea> getAreaList(int gameid) {
//		String cacheKey = "game_area_list_" + gameid;
//		
//		List<GameArea> areaList = Cache.get(cacheKey);
//		if (areaList != null)  {
//			return areaList;
//		}
//		
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid);
//			
//			JSONObject json = gameRequest.getAreaList();
//			if (json.getInt("code") != 0){
//				logger.warn("读取游戏区信息失败，uid=" + gameid + "," + json.getString("msg"));
//				return null;
//			}
//			
//			JSONArray arr = json.getJSONArray("body");
//			areaList = new ArrayList<>();
//			for (int i = 0; i < arr.size(); i++) {
//				GameArea gameArea = (GameArea)JSONObject.toBean(arr.getJSONObject(i),GameArea.class);
//				areaList.add(gameArea);
//				//缓存30天
//				Cache.add("game_area_" + gameid + "_" + gameArea.getAreaid(), 3600 * 28, gameArea.getAreaName());
//			}
//			//缓存1天
//			Cache.add(cacheKey, 3600, areaList);
//		} catch (Exception e) {
//			logger.error("", e);
//		} 
//		return areaList;
//	}
//	
//	@Override
//	public String queryAreaName(int gameid,int areaid){
//		String str = Cache.get("game_area_" + gameid + "_" + areaid);
//		if (str == null) {
//			this.getAreaList(gameid);
//		}
//		if (str == null) {
//			str = "--";
//		}
//		return str; 
//	}
//	@Override
//	public List<GameServer> getServerList(int gameid, int areaid) {
//		
//		String cacheKey = "game_server_list_" + gameid + "_" + areaid; 
//		List<GameServer> serverList = Cache.get(cacheKey);
//		if (serverList != null) {
//			return serverList; 
//		}
//		try{
//			Game game = gameService.queryGameByGameIdFront(gameid);  
//			GameRequest gameRequest = new GameRequest(gameid, game.getInterfaceUrl(), game.getInterfaceKey());
//			
//			JSONObject json = gameRequest.getServerList(areaid);
//			if (json.getInt("code") != 0){
//				logger.warn("读取游戏服务器信息失败，uid=" + gameid + "," + json.getString("msg"));
//				return null;
//			}
//			
//			JSONArray arr = json.getJSONArray("body");
//			serverList = new ArrayList<>();
//			for (int i = 0; i < arr.size(); i++) {
//				GameServer gameServer = (GameServer)JSONObject.toBean(arr.getJSONObject(i),GameServer.class);
//				serverList.add(gameServer);
//				Cache.add("game_server_" + gameid + "_" + gameServer.getServerid(), 3600 * 28, gameServer.getServerName());
//			}
//			//缓存1天
//			Cache.add(cacheKey, 3600, serverList);
//		} catch (Exception e) {
//			logger.error("", e);
//		} 
//		return serverList;
//	}
//	
//	@Override
//	public String queryServerName(int gameid,int serverid){
//		String str = Cache.get("game_server_" + gameid + "_" + serverid);
//		
//		if (str == null) {
//			str = "--";
//		}
//		return str;
//	}
//	
//
//	@Override
//	public List<GameRole> getRoleList(int gameid, String uid, int areaid,int serverid) {
//		
//		String cacheKey = "game_user_role_list_" + gameid + "_" + uid + "_" + areaid + "_" + serverid; 
//		
//		List<GameRole> userRoleList = Cache.get(cacheKey);
//		if(userRoleList != null) {
//			return userRoleList; 
//		}
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid);
//			
//			JSONObject json = gameRequest.getRoleList(uid, areaid, serverid);
//			if (json.getInt("code") != 0){
//				logger.warn("读取用户角色失败，uid=" + gameid + "," + json.getString("msg"));
//				return null;
//			}
//			
//			JSONArray arr = json.getJSONArray("body");
//			Map<String, Class> classMap = new HashMap<String, Class>();
//			classMap.put("products", GameProduct.class);
//			userRoleList = new ArrayList<>();
//			for (int i = 0; i < arr.size(); i++) {
//				GameRole gameRole = (GameRole)JSONObject.toBean(arr.getJSONObject(i),GameRole.class,classMap);
//				userRoleList.add(gameRole);
//			}
//			//userRoleList.get(0).getProducts().get(0);
//			//缓存1小时
//			Cache.add(cacheKey, 60, userRoleList);
//		} catch (Exception e) { 
//			logger.error("", e);
//		}
//		
//		return userRoleList;
//	}
//
//	@Override
//	public GameRole queryRoleName(int gameid, int areaid,int serverid, String roleid) {
//		String cacheKey = "game_user_role_name_" + gameid + "_" + areaid + "_" + serverid + "_" + roleid;
//
//		GameRole gameRole = Cache.get(cacheKey);
//		if (gameRole != null) {
//			return gameRole;
//		}
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid);
//			
//			JSONObject json = gameRequest.queryRoleName(areaid, serverid, roleid);
//			if (json.getInt("code") != 0){
//				logger.warn("读取用户角色名失败，gameid=" + gameid + "," + json.getString("msg"));
//				return null;
//			}
//			gameRole = (GameRole)JSONObject.toBean(json.getJSONObject("body"),GameRole.class);
//			//该接口没有roleid,自己set回去
//			if(StringUtils.isBlank(gameRole.getRoleid())){
//			    gameRole.setRoleid(roleid);
//			}
//			//缓存1小时天
//			Cache.add(cacheKey, 3600, gameRole);
//		}catch (Exception e) {
//			logger.error("", e);
//		}
//		
//		return gameRole;
//	}
//	@Override
//	public GameRole queryRoleInfo(int gameid,int areaid,int serverid,String roleName){
//	    try{
//            GameRequest gameRequest = this.createGameRequest(gameid);
//            
//            JSONObject json = gameRequest.queryRoleInfo(areaid, serverid, roleName);
//            if (json.getInt("code") != 0){
//                logger.warn("读取用户角色信息失败，gameid=" + gameid + "," + json.getString("msg"));
//                return null;
//            }
//            GameRole gameRole = (GameRole)JSONObject.toBean(json.getJSONObject("body"),GameRole.class);
//            //缓存60秒
//            //Cache.add(cacheKey, 30, gameRole);
//            return gameRole;
//        }catch (Exception e) {
//            logger.error("", e);
//        } 
//        return null;
//	}
//
//	@Override
//	public GameResponse productOnShelf(int gameid, String uid, int areaid, int serverid,String roleid, int productid, long amount, String pid) {
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid);
//			JSONObject json = gameRequest.productOnShelf(uid, areaid, serverid, roleid, productid, amount, pid);
//			return new GameResponse(json.getInt("code"),json.getString("msg"));
//		}catch (Exception e) {
//			logger.error("上架失败:" + pid + "，" + uid, e);
//			return new GameResponse(99999,"请求失败");
//		}
//	}
//
//	@Override
//	public GameResponse productOffShelf(int gameid, String pid,int areaid,int serverid, String reason,long amount) {
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid);
//			
//			JSONObject json = gameRequest.productOffShelf(pid,areaid,serverid,reason,amount);
//			
//			GameResponse gr = new GameResponse(json.getInt("code"),json.getString("msg"));
//			gr.setBody(json.getJSONObject("body"));
//			return gr;
//		}catch (Exception e) {
//			logger.error("下架失败：pid=" + pid , e);
//			return new GameResponse(99999,"请求失败");
//		}
//	}
//
//	@Override
//	public GameResponse productDeliver(int gameid, String pid, String orderid,int areaid,
//			int serverid, String roleid, String roleName, long amount) {
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid);
//			JSONObject json = gameRequest.productDeliver(pid, orderid,areaid, serverid, roleid, roleName, amount);
//			return new GameResponse(json.getInt("code"),json.getString("msg"));
//		}catch (Exception e) {
//			logger.error("发货失败：" + orderid, e);
//			return new GameResponse(99999,"请求失败");
//		}
//	}
//	
//	/**
//     * 发送邮件验证码（部分游戏无法直接调用发邮件接口）
//     * @param areaid
//     * @param serverid
//     * @param roleid
//     * @param vcode
//     * @param type （验证码类型，0普通验证码，1绑定账号验证码，2商品上架验证码
//     * @return
//     */
//	@Override
//	public boolean sendMailVcode(int gameid,int areaid,int serverid ,String roleid,String vcode,int type){
//	    try{
//            GameRequest gameRequest = this.createGameRequest(gameid);
//            JSONObject json = null;
//            Game game = gameService.queryGameByGameIdFront(gameid);
//            //api版本为1的，可以直接写邮件内容 （包括 世界ol，江湖）
//            if (game.getApiVersion() == API_VERSION_1){
//                String title = null;
//                String content = null;
//                if (type == 0){
//                    title = "8868交易验证码";
//                    content = "您在【8868交易平台】的验证码是：" + vcode + " 。请注意保密，不要泄露给第三方。";
//                }else if (type == 1){
//                    title = "绑定验证邮件";
//                    content = "您的验证码是：" + vcode + " ";
//                    content += "\n【8868.cn】是官方唯一承认的游戏物品交易平台。卖家可以自由发布商品信息，买家购买物品后，系统会把该物品以邮件的形式发布送到你的本帐号内。安全、便捷!";
//                }else{
//                    title = "发布商品验证邮件";
//                    content = "您的验证码是：" + vcode + "(30分钟内有效)";
//                    content += "\n您当前正在使用8868交易平台发布商品，请勿将验证码泄漏给他人。如有疑问，请联系8868交易平台。客服热线：020-62334488";
//                }
//                json = gameRequest.sendMail(areaid, serverid, roleid, title,content);
//            }else{
//                json = gameRequest.sendEmailVcode(areaid, serverid, roleid, vcode, type);
//            }
//            return json.getInt("code") == 0 ? true : false;
//        }catch (Exception e) {
//            logger.error("", e);
//        }
//        return false;
//	} 
//	
//	@Override
//	public String queryGameUid(int gameid, int areaid, int serverid, String roleid) {
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid);
//			JSONObject json = gameRequest.queryGameUid(areaid, serverid, roleid);
//			if (json.getInt("code") == 0){
//				return json.getJSONObject("body").getString("uid");
//			}
//		}catch (Exception e) {
//			logger.error("", e);
//		}
//		return null;
//		
//	}
//	@Override
//	public JSONArray queryAllRoleList(int gameid, String gameUid) {
//		String cacheKey = "game/interface/allrolelist/" + gameid + "/" + gameUid;
//		
//		String str = Cache.get(cacheKey);
//		if (str != null) {
//			return JSONArray.fromObject(str);
//		}
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid); 
//			
//			JSONObject json = gameRequest.queryAllRoleList(gameUid);
//			if (json.getInt("code") == 0){
//			    JSONArray body = json.getJSONArray("body"); 
//				Cache.set(cacheKey, 300, body.toString());
//				
//				return body;
//			}
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		return null;		
//	}
//	
//	@Override
//	public List<GameRole> queryAllRoleList1(int gameid, String gameUid){
//	    JSONArray body = this.queryAllRoleList(gameid, gameUid);
//	    List<GameRole> list = new ArrayList<>();
//	    if (body != null){
//	        list = JSONArray.toList(body,GameRole.class);
//	    }
//	    return list;
//	}
//
//	/**
//	 * 创建一个游戏请求对象
//	 * @param gameid
//	 * @return
//	 */
//	public GameRequest createGameRequest(int gameid){
//		Game game = gameService.queryGameByGameIdFront(gameid); 
//		GameRequest gameRequest = new GameRequest(gameid, game.getInterfaceUrl(), game.getInterfaceKey());
//		return gameRequest;
//	}
//	
//	
//	
//	@Override
//	public JSONObject queryProductInfo(int gameid,int areaid,int serverid,String pid){
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid); 
//			
//			JSONObject json = gameRequest.queryProductInfo(areaid,serverid,pid);
//			if (json.getInt("code") == 0){ 
//				return json.getJSONObject("body");
//			}
//		}catch (Exception e) {
//			logger.error("", e);
//		}
//		return null;
//	}
//	
//	
//	@Override 
//	public JSONObject queryOrderInfo(int gameid,int areaid,int serverid,String orderid){
//		try{
//			GameRequest gameRequest = this.createGameRequest(gameid); 
//			
//			JSONObject json = gameRequest.queryOrderInfo(areaid,serverid,orderid);
//			if (json.getInt("code") == 0){ 
//				return json.getJSONObject("body");
//			}
//		}catch (Exception e) {
//			logger.error("", e);
//		}
//		return null;
//	}
//
//}
//
