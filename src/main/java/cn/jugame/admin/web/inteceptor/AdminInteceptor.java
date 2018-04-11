package cn.jugame.admin.web.inteceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import cn.jugame.dao.SysUserinfoDao;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.GlobalsKeys;
import cn.jugame.web.util.OrderC2cUtils;
import cn.jugame.web.util.ProductC2cUtils;
import cn.jugame.web.util.RequestUtils;

/**
 * 过滤器
 * @author Administrator
 *
 */
public class AdminInteceptor implements HandlerInterceptor {
	Logger logger = LoggerFactory.getLogger(AdminInteceptor.class);
	Logger viLog = LoggerFactory.getLogger("visit_log");
	
	//不需要权限拦截的url
	String[] excludeUrl = {"/login_submit","/login.html","/logout.jsp"};
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object o, Exception e)
			throws Exception {
		 
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object o, ModelAndView mv) throws Exception {
		 
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object o) throws Exception {
		String url = request.getRequestURI();
		
//		logger.info("url == " + url);
		SysUserinfo userinfo = (SysUserinfo) request.getSession().getAttribute(GlobalsKeys.ADMIN_KEY);
		
		if(url.indexOf("/single/") != -1){
			return true;
		}
		if(url.indexOf("/isSMS") != -1){
			return true;
		}
		
		if(url.indexOf("/sendVerifyCode") != -1){
			return true;
		}
		if(url.indexOf("/SSOAuth/SSOAuthTest") != -1){
			return true;
		}
		if(url.indexOf("/api/") != -1){
			return true;
		}
		if(url.indexOf("/webDate/getOrdersC2cDetail.jsp") != -1){
			String orderId = request.getParameter("orderId");
			String token = request.getParameter("token");
			return OrderC2cUtils.validateToken(orderId, token);
		}
		if(url.indexOf("/webDate/getProductC2cDetail.jsp") != -1){
			String productId = request.getParameter("productId");
			String token = request.getParameter("token");
			return ProductC2cUtils.validateToken(productId, token);
		}
//		if(url.indexOf("/orderC2c/getOrderC2cNoSuccessListForQq") != -1){
//			return true;
//		}
		//活动目录，
		if(url.indexOf("/huodong/") != -1){
			return true;
		}
		if (url.indexOf("/api") != -1){
			return true;
		}
		//静态文件，不需要拦截
		if (o instanceof DefaultServletHttpRequestHandler){
			if(url.contains("/main.html") && userinfo == null){
				response.sendRedirect(request.getContextPath() + "/main.jsp");
				
			}
			return true;
		} 
		
		for (int i = 0; i < excludeUrl.length; i++) {
			url = url.replace(request.getContextPath(),	"");
			if (url.equals(excludeUrl[i])){
				return true;
			}
		}
		
	
		if (userinfo == null){
			response.sendRedirect(request.getContextPath() + "/main.jsp");
			return false; 
		}
		
		String str = "";
		Map<String, String[]> map = request.getParameterMap();
        Set<Entry<String, String[]>> set = map.entrySet();  
        Iterator<Entry<String, String[]>> it = set.iterator();  
        while (it.hasNext()) {
        	Entry<String, String[]> entry = it.next(); 
        	str += "key:"+entry.getKey();
            for (String i : entry.getValue()) {
            	str += ", value:" + i + "; ";
            }
        }
        String ip = RequestUtils.getUserIp(request);
		viLog.info("`uid` = " + userinfo.getUserId() + ", `fullName` = " + userinfo.getFullname() + ", `url` = " + url + ", `IP` = " + ip + ", {" + str + "}");
		
		
		if(o instanceof HandlerMethod){
			Class clazz = o.getClass();
			HandlerMethod hm = (HandlerMethod) o;
			Object action = hm.getBean();
			Method mm = hm.getMethod();
			
			if (userinfo.getUsertype() != SysUserinfoDao.USER_TYPE_SUPER_ADMIN){
				Map<String, String>  perMap = (HashMap<String, String>) request.getSession().getAttribute(GlobalsKeys.ADMIN_PERMISSION_MAP);
				
				String clzz = action.getClass().getName(); //类名
				String method = mm.getName(); //方法名
				String permission = (clzz + "." + method).toLowerCase();
		        //logger.info("包路径===" + permission);
				if((permission.equals("cn.jugame.admin.web.controller.userinfocontroller.userinfochangepwd"))){
					return true;
				}
				if((permission.equals("cn.jugame.admin.web.controller.system.menuaccesscontroller.addaccessdata"))){
					return true;
				}
//		        //logger.info("包路径===" + permission);
//				if((permission.equals("cn.jugame.admin.web.controller.LoginController.md5CustomerLogin"))){
//					return true;
//				}
				if (perMap == null || perMap.get(permission) == null){
					response.sendRedirect(request.getContextPath() + "/nopermission2.html");
					logger.info("权限不够"+ permission);
				
					return false;
				}
			}

		}

		return true;
	}
}

