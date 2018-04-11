//package cn.jugame.admin.web.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import cn.jugame.util.RequestUtils;
//
//@Controller
//public class PinyinController {
//	@RequestMapping(value="/convertpy")
//	@ResponseBody
//	public JSONObject convert(Model model, HttpServletRequest req, HttpServletResponse resp){
//		String word = RequestUtils.getParameter(req, "word", "");
//		
//		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
//		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//		StringBuffer buf = new StringBuffer();
//		for(char c : word.toCharArray()){
//			//如果是字母或者英文字符
//			if(c >= 'a' && c <= 'z')
//				buf.append(c);
//			else if(c >= 'A' && c <= 'Z')
//				buf.append(c);
//			else if(c >= '0' && c <= '9')
//				buf.append(c);
//			else{
//				try{
//					String[] ss = PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
//					if(ss != null && ss.length > 0){
//						if(buf.length() != 0)
//							buf.append(",");
//						buf.append(ss[0]);
//					}
//				}catch(Exception e){
//					//出错忽略
//				}
//			}
//		}
//		
//		JSONObject rtn = new JSONObject();
//		rtn.put("success", true);
//		rtn.put("py", buf.toString());
//		return rtn;
//	}
//}
