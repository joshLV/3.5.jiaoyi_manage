package cn.jugame.web.util;

import java.io.File;
import java.io.FileInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

/**
 * 表单submit方式图片上传
 * 
 *
 */
public class UploadImageUtil {
	
	public static Logger logger = LoggerFactory.getLogger(UploadImageUtil.class);
	
	/**
	 * 上传图片 
	 * 
	 * @param imgServiceUrl
	 * @param imgBase64
	 * @return
	 */
	public static String doUpload(String imgServiceUrl, File file){
		
		try {
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			if(StringUtils.isBlank(imgServiceUrl) || StringUtils.isBlank(new BASE64Encoder().encode(buffer))){
				return null;
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("image_base64_data", new BASE64Encoder().encode(buffer));
			String result = HttpRequestUtil.doHttpRequest(imgServiceUrl,jsonObject.toString());
			JSONObject jsonObj = JSONObject.fromObject(result);
		    logger.info("upload img result :" + result);
		    if(jsonObj.getBoolean("status")){
		    	String small_image_url = jsonObj.getString("image_url"); //原图
		    	return small_image_url;
		    }
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		return null;
	}
}
