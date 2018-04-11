//package cn.jugame.admin.web.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.service.ISysUserinfoService;
//import cn.jugame.vo.SysUserinfo;
//import cn.jugame.web.util.MessageBean;
//import cn.jugame.web.util.RequestUtils;
//import cn.juhaowan.interfaces.game.vo.MemberGameBind;
//import cn.juhaowan.interfaces.service.GameInterfaceService;
//import cn.juhaowan.interfaces.service.GameBindService;
//import cn.juhaowan.log.service.BackUserLogService;
//import cn.juhaowan.product.service.GameService;
//import cn.juhaowan.product.vo.Game;
///**
// * 用户管理
// * @author Administrator
// *
// */
//
//@Controller
////@RequestMapping("/memGameInfo") 
//public class MemberGameBindController {
//	Logger logger = LoggerFactory.getLogger(MemberGameBindController.class);
//
//	@Autowired
//	private GameInterfaceService gameInterfaceService;
//	
//	@Autowired
//	private GameBindService gameBindService;
//    
//	@Autowired
//	private ISysUserinfoService sysUserService;
//	
//	@Autowired
//	private GameService gameService;
//	
//	@Autowired
//	private BackUserLogService backUserLogService;
//	
//	/**
//	 * 用户游戏列表
//	 * @return 跳转路径
//	 */
//	@RequestMapping(value = "/getMemGameInfo/gameInfoList")
//	public String gameInfoList() {
//		return "getMemGameInfo/gameInfoList";
//	}
//	
//	/**
//	 * 用户游戏列表
//	 * @param re 
//	 * @param model 
//	 * @return 根据uid查询出的用户游戏信息
//	 */
//	@RequestMapping(value = "/getMemGameInfo/gameInfoList_json")
//	public String gameInfoListJson(HttpServletRequest request, Model model) {
//    String queryType=RequestUtils.getParameter(request, "query_type","");
//    int count=0;
//    if(queryType.equals("uid")) {
//		int uid = RequestUtils.getParameterInt(request, queryType,0);
//	  	List<MemberGameBind> list  = gameBindService.queryBindGameListByUid(uid);
//	  	
//	  	for (MemberGameBind mem:list) {
//	  		count++;
//	  		Game game=gameService.queryGameByGameId(mem.getGameId());
//	  		mem.setGameName(game.getGameName());
//	  		}
//	  	model.addAttribute("count", count);
//		model.addAttribute("list", list);
//    }
//   
//    if(queryType.equals("gameUid")){
//		String gameUid = RequestUtils.getParameter(request, queryType, "");
//		List<MemberGameBind> list  = gameBindService.queryBindGameListByGameUid(gameUid);
//		
//		for (MemberGameBind mem:list) {
//			count++;
//			Game game=gameService.queryGameByGameId(mem.getGameId());
//			mem.setGameName(game.getGameName());
//		}
//		model.addAttribute("count", count);
//		model.addAttribute("list", list);	
//    }
//	return "getMemGameInfo/gameInfoList_json";  
//	}
//	
//	/**
//	 *  解绑游戏
//	 * @param request
//	 * @return 解绑成功提示信息
//	 */
//	@RequestMapping(value = "/getMemGameInfo/gameinfo_delete")
//	@ResponseBody
//	public MessageBean unbindGameUser(HttpServletRequest request) {
//		String ids = request.getParameter("id");
//        //System.out.println(".......id.."+ids);
//		logger.info("del =" + Integer.parseInt(ids));
//		MemberGameBind memGameBind=gameBindService.findBindById(Integer.parseInt(ids));
//		int uid=memGameBind.getUid();
//		String gameUid=memGameBind.getGameUid();
//		//System.out.println("uid..."+uid+"gameUid..."+gameUid);
//		try {
//			int effectRow = gameBindService.unbindGameUser(Integer.parseInt(ids));
//			if(effectRow==0) {
//				//添加日志
//				sysUserService.delete(Integer.parseInt(ids));
//				SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(
//						cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
//				backUserLogService.addLog(u.getUserId(), RequestUtils.getUserIp(request),
//						backUserLogService.GAME_UNBIND, u.getFullname() + "解绑的用户的ID为：" + ids
//						+"，UID为："+uid+"，游戏用户ID为："+gameUid);
//			}
//			
//		} catch (Exception e) {
//			
//			logger.error("", e);
//		}
//	
//		return new MessageBean();       
//	}
//	
//}
