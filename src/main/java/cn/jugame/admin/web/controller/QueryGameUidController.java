//package cn.jugame.admin.web.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import net.sf.json.JSONObject;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.web.util.RequestUtils;
//import cn.juhaowan.interfaces.service.GameBindService;
//import cn.juhaowan.interfaces.service.impl.DefaultGameInterfaceService;
//import cn.juhaowan.member.service.MemberService;
//import cn.juhaowan.member.vo.Member;
//import cn.juhaowan.product.service.GameService;
//import cn.juhaowan.product.vo.Game;
//import cn.juhaowan.product.vo.GamePartition;
//@Controller
//public class QueryGameUidController {
//	Logger logger = LoggerFactory.getLogger(ProductController.class);
//	
//	@Autowired
//	DefaultGameInterfaceService defaultGameInterfaceService;
//	
//	@Autowired
//	private GameBindService gameBindService;
//	
//	@Autowired
//	GameService gameService;
//	
//	@Autowired
//	MemberService memberService;
//	
//	/**
//	 * 查询游戏Uid
//	 * @param request
//	 * @param m
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/queryGameUid")
//	public String queryGameUidList(HttpServletRequest request, Model m) {
//	
//
//		return "/member/queryGameUid";
//	}
//	/**
//	 * 查询游戏Uid
//	 * @param request
//	 * @param m
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/member/query")
//	@ResponseBody
//	public JSONObject query(HttpServletRequest request, Model m) {
//		JSONObject data = new JSONObject();
//
//		String roleId = RequestUtils.getParameter(request, "roleId", "");
//		if (roleId != null || !roleId.equals("")) {
//			String gameId = RequestUtils.getParameter(request, "gameId", "-1");
//			String areaId = RequestUtils.getParameter(request, "partitionId","-1");
//			String serverId = RequestUtils.getParameter(request, "serverId", "-1");
//
//			if (areaId.equals("-1")) {
//				areaId = "0";
//			}
//			if (serverId.equals("-1")) {
//				serverId = "0";
//			}
//			//游戏名字
//			String gname = "";
//			//分区名字
//			String pname = "";
//			//服务器名字
//			String sname = "";
//			//绑定平台账号id
//			String uid = "--";
//			//绑定平台账号
//			String loginId = "--";
//			if(Integer.parseInt(gameId) > 0){
//			Game game  = gameService.queryGameByGameId(Integer.parseInt(gameId));
//			if(null != game){
//				gname = game.getGameName();
//			}
//		    }
//			if(Integer.parseInt(areaId) > 0){
//			GamePartition gp = gameService.queryGamePartitionByMainKey(Integer.parseInt(gameId), Integer.parseInt(areaId));
//			if(null != gp){
//				pname = gp.getPartitionName();
//			}
//			}
//			if (Integer.parseInt(serverId) > 0) {
//				sname = gameService.queryGameServerByMainKey(Integer.parseInt(gameId), Integer.parseInt(areaId),Integer.parseInt(serverId)).getGameServerName();
//			} else {
//				sname = "--";
//			}
//			if (!roleId.equals("")) {
//				String gameUid = defaultGameInterfaceService.queryGameUid(Integer.parseInt(gameId), Integer.parseInt(areaId),Integer.parseInt(serverId), roleId);
//
//				if (gameUid != null) {
//					data.put("gameUid", gameUid);
//					int uidTemp = gameBindService.queryMemberID(Integer.parseInt(gameId), gameUid);
//					Member mer = memberService.findMemberByUid_back(uidTemp);
//					if(null != mer){
//						data.put("uid", uidTemp);
//						data.put("loginId", mer.getLoginName());
//						data.put("phone", mer.getMobile());
//					}
//					
//				}else{
//					data.put("gameUid", "--");
//					data.put("uid", "--");
//					data.put("loginId", "--");
//					data.put("phone", "--");
//				}
//			}
//
//			data.put("gameId", gname);
//			data.put("partitionId", pname);
//			data.put("serverId", sname);
//			data.put("roleId", roleId);
//
//		}
//
//		return data;
//	}
//
//}
