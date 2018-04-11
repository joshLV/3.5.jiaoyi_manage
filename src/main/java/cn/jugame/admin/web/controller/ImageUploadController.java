package cn.jugame.admin.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.jugame.util.MD5;

/**
 * 图片上传
 * 
 * @author APXer
 * 
 */
@Controller
public class ImageUploadController {
	Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	private static String IMAGEUPLOADURL;
	private static String IMAGEURL;
	static {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(ImageUploadController.class.getClassLoader().getResource("resources.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		IMAGEURL = pro.getProperty("imageURL");
		IMAGEUPLOADURL = pro.getProperty("imageUploadURL");
	}

	@RequestMapping(value = "/editorImage/imageUpload", method = RequestMethod.POST)
	@ResponseBody
	public void editorImageImageUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpServletResponse response,
			Model model) {
		JSONObject json = new JSONObject();
		// String imageUploadURL = request.getServletContext().getRealPath("/")
		// + IMAGEUPLOADURL;
		String imageUploadURL = IMAGEUPLOADURL;
		System.err.println("上传到目录：" + imageUploadURL);
		File imageDir = new File(imageUploadURL);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
			logger.info("创建图片目录成功_上传");
		}
		logger.info("_上传");
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		String picTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + new Random().nextInt(1000);
		String originalFileName = file.getOriginalFilename();
		String extType = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		String imageFileName = MD5.encode(picTime);
		if (!Arrays.<String> asList(extMap.get("image").split(",")).contains(extType)) {
			imageFileName = imageFileName.concat(extType);
		} else {
			json.put("message", "上传文件扩展名是不允许的扩展名。只允许" + extMap.get("image")+ "格式");
			json.put("error", "1");
			// return json.toString();
		}
		if (file.getSize() > 10000000) {
			json.put("message", "上传的图片文件不能大于10M");
			json.put("error", "1");
			// return json.toString();
		}

		/* 保存文件 */
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imageUploadURL, imageFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		json.put("error", "0");
		json.put("url", imageFileName);
		json.put("message", "上传成功");
		// return json.toString();
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		ServletOutputStream out;
		try {
			out = response.getOutputStream();
			out.write(json.toString().getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/editorImage/imageList_json")
	@ResponseBody
	public JSONObject imageListJSON(HttpServletRequest request, Model model)
			throws IOException {
		// String rootPath = request.getServletContext().getRealPath("/")+
		// IMAGEUPLOADURL;
		String rootPath = IMAGEUPLOADURL;
		File rootFile = new File(rootPath);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
			logger.info("创建图片目录成功");
		}
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		String path = request.getParameter("path") != null ? request
				.getParameter("path") : "";
		String currentPath = IMAGEUPLOADURL + File.separator + path;
		// String currentUrl = IMAGEUPLOADURL + path;
		// String currentDirPath = path;
		String moveupDirPath = "";
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			return null;
		}
		List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
		if (currentPathFile.listFiles() != null) {

			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = "png";
					// fileName.substring(fileName.lastIndexOf(".") +
					// 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo",
							Arrays.<String> asList(extMap.get("image"))
									.contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
								.lastModified()));
				fileList.add(hash);
			}
		}
		String order = request.getParameter("order") != null ? request
				.getParameter("order").toLowerCase() : "name";
		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		JSONObject json = new JSONObject();
		json.put("moveup_dir_path", moveupDirPath);
		json.put("current_dir_path", path);
		json.put("current_url", IMAGEURL + path);
		json.put("total_count", fileList.size());
		json.put("file_list", fileList);
		return json;
	}
}

class NameComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir"))
				&& ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String) hashA.get("filename")).compareTo((String) hashB
					.get("filename"));
		}
	}
}

class SizeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir"))
				&& ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
				return 1;
			} else if (((Long) hashA.get("filesize")) < ((Long) hashB
					.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}

class TypeComparator implements Comparator {
	public int compare(Object a, Object b) {
		Hashtable hashA = (Hashtable) a;
		Hashtable hashB = (Hashtable) b;
		if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
			return -1;
		} else if (!((Boolean) hashA.get("is_dir"))
				&& ((Boolean) hashB.get("is_dir"))) {
			return 1;
		} else {
			return ((String) hashA.get("filetype")).compareTo((String) hashB
					.get("filetype"));
		}
	}
}