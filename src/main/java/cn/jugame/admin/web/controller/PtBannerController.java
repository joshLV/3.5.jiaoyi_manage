package cn.jugame.admin.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.jugame.util.MD5;
import cn.jugame.util.PageInfo;
import cn.jugame.vo.SysUserinfo;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RequestUtils;
import cn.juhaowan.banner.service.BannerImagesService;
import cn.juhaowan.banner.service.BannerService;
import cn.juhaowan.banner.vo.PtBanner;
import cn.juhaowan.banner.vo.PtBannerImages;
import cn.juhaowan.log.service.BackUserLogService;
import cn.juhaowan.util.AliyunImageUtils;

import com.alibaba.fastjson.JSONObject;



/**
 * 幻灯片管理
 * @author Administrator
 *
 */
@Controller
public class PtBannerController {
	Logger logger = LoggerFactory.getLogger(PtBannerController.class);

	@Autowired
	private BannerService bannerService;

	@Autowired
	private BackUserLogService backUserLogService;

	@Autowired
	private BannerImagesService bannerImagesService;
	
//	@Autowired
//	protected IFileUpload fileUpload;

	
	/**
	 * 从资源文件获取图片上传地址
	 */
	//图片保存地址 
	private static String IMGBASEURL;
	//图片显示地址
	private static String IMGSHOWURL;
	static {
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(PtBannerController.class.getClassLoader().getResource("resources.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		IMGBASEURL = pro.getProperty("banner.imgbaseurl");
		IMGSHOWURL = pro.getProperty("banner.imgshowurl");
	}
	/**
	 * 幻灯片列表
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/banner/bannerList")
	public String bannerList(HttpServletRequest request, Model model) {

		return "banner/bannerList";
	}

	/**
	 * 幻灯片列表数据
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/banner/bannerListJson")
	public String bannerListJson(HttpServletRequest request, Model model) {
		int pageNo = RequestUtils.getParameterInt(request, "page", 1);
		int pageSize = RequestUtils.getParameterInt(request, "rows", 100); // 每页多少条记录
		String sort = RequestUtils.getParameter(request, "sort", "id"); // 排序字段
		String order = RequestUtils.getParameter(request, "order", "desc");

		// 搜索条件
		Map<String, Object> condition = new HashMap<String, Object>();
		String btime = RequestUtils.getParameter(request, "btime", "");
		String etime = RequestUtils.getParameter(request, "etime", "");

		String bannerName = RequestUtils.getParameter(request, "bannerName", "");

		
		if (null != bannerName && !"".equals(bannerName)) {
			condition.put("name", bannerName);
			model.addAttribute("bannerName", bannerName);
		}
//		if (null != etime && !"".equals(etime)) {
//			condition.put("endTime", etime + " 23:59:59");
//			model.addAttribute("etime", etime);
//		}
//		if (uid > 0) {
//			condition.put("uid", uidTemp);
//		}
//		condition.put("login_name", loginname);
		
		PageInfo<PtBanner> pageInfo = bannerService.queryPtBannerForPage(condition, pageNo, pageSize, sort, order);
		if (null == pageInfo){
			return null;
		}


		model.addAttribute("pageInfo", pageInfo);

		return "banner/bannerListJson";
	}

	
	/**
	 * 幻灯片图片列表
	 * @param request 
	 * @param model 
	 * @return 跳转路径
	 */
	@RequestMapping(value = "/banner/bannerImgList")
	public String bannerImgList(HttpServletRequest request, Model model) {
        int bannerId  = RequestUtils.getParameterInt(request, "bannerId", 0);
        int type = RequestUtils.getParameterInt(request, "status_type", 0);
        
        //没有值 
//        if(bannerId == 0){
//         //request.getSession().removeAttribute("sessionBanId");
//        }
        PtBanner baner = bannerService.queryPtBannerById(bannerId);
        if(null != baner){
            model.addAttribute("bannerId", baner.getId());
            model.addAttribute("name", baner.getName());
            model.addAttribute("tag", baner.getTag());
            model.addAttribute("remark", baner.getRemark());
            model.addAttribute("create_time", baner.getCreateTime());
            model.addAttribute("modify_time", baner.getModifyTime());
            model.addAttribute("status", baner.getStatus());
            model.addAttribute("weight", baner.getWeight());
        }
        List<PtBannerImages> imagesList = bannerImagesService.queryListByBanberIdBack(bannerId,type);
       if(null != imagesList){
        //方法1 设置图片显示页面(从流里面读取)
//        String path = request.getContextPath();
//		  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//        String baseUrl = basePath + "readbannerImg?imgurl=";

//        for(int i = 0; i < imagesList.size(); i++){
//        	imagesList.get(i).setImageUrlShow(baseUrl + imagesList.get(i).getId());
//        }
        //方法2 url地址读取  前面加上ip地址 现在修改为数据库直接保存完整地址 （失效）
//        String baseUrl = IMGSHOWURL;
//      for(int i = 0; i < imagesList.size(); i++){
//    	imagesList.get(i).setImageUrlShow(baseUrl + imagesList.get(i).getImageUrl());
//    }
        
        model.addAttribute("imagesList", imagesList);
        model.addAttribute("status_type", type);
        model.addAttribute("baseUrl", IMGSHOWURL);
       }
		return "banner/bannerImgList";
	}
	/**
	 * 添加/编辑 横幅和横幅图片
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/banner/imgAddAndEdit")
    @ResponseBody
	public MessageBean imgAddAndEdit(HttpServletRequest request, Model model){
		try {
			request.setCharacterEncoding("gb2312");//程序开始第一行
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//横幅数据
		int bId = 0; //横幅id 添加时返回
		String banName = "";
		String banremark = "";
		String bantag = "";
		try {
			banName =   RequestUtils.getParameter(request, "banName", "").trim();
			banremark =RequestUtils.getParameter(request, "banremark", "").trim();
			bantag = RequestUtils.getParameter(request, "bantag", "").trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int bannerId = RequestUtils.getParameterInt(request, "bannerId", 0);
		//int flag = RequestUtils.getParameterInt(request, "flag", 0);
		
		int banstatus = RequestUtils.getParameterInt(request, "banstatus", 0);
		
		
	
		PtBanner ban = new PtBanner();
		if(bannerId > 0){
			 ban = bannerService.queryPtBannerById(bannerId);
		}
		
		//有id 更新横幅
		if(bannerId > 0 && null != ban){
		     ban.setName(banName);
		     ban.setRemark(banremark);
		     ban.setStatus(banstatus);
		     ban.setTag(bantag);
		     bannerService.updatePtBanner(ban);
			
		}else{ //无id 添加横幅
			//检查标签是否存在
			boolean f = bannerService.tagExist(bantag);
			if(f){
				return new MessageBean(false,"该标签已存在,请填入其他标签名字");
			 }
		     ban.setName(banName);
		     ban.setRemark(banremark);
		     ban.setStatus(banstatus);
		     ban.setTag(bantag);
		     bId = bannerService.insertPtBanner(ban);
		     
		 	if(bId <= 0){
				return new MessageBean(false,"添加数据失败");
			}
		}
	
		//横幅图片列表数据
		String[] aids = request.getParameterValues("optionId"); 
		String[] name = request.getParameterValues("name"); 
		String[] imageUrl = request.getParameterValues("imageUrl"); 
		String[] alink = request.getParameterValues("alink"); 
		String[] status = request.getParameterValues("status_sub"); 
		String[] weight = request.getParameterValues("weight_sub"); 
		String[] upTime = request.getParameterValues("upTime"); 
		String[] downTime = request.getParameterValues("downTime"); 
		
		//循环添加或修改图片信息
		for(int i = 0;i < name.length;i++){
			//转换时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date up = null;
			Date down = null;
			String newName = "";
			String newAlink = "";
			String newImageUrl = "";
			//转换时间 暂不用 时间
			if(null != upTime[i] && !"".equals(upTime[i])){
				try {
					up = sdf.parse(upTime[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(null != downTime[i] && !"".equals(downTime[i])){
				try {
					down = sdf.parse(downTime[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(null != name[i] && !"".equals(name[i])){
				try {
					newName = name[i];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != alink[i] && !"".equals(alink[i])){
				try {
					newAlink = alink[i];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != imageUrl[i] && !"".equals(imageUrl[i])){
				try {
					newImageUrl = imageUrl[i];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(bId <= 0){
				bId = bannerId; //更新横幅id设置
			}
			
			//无图片id 添加
			if (null == aids[i] || "".equals(aids[i])) {
				
				//如果图片名字和图片都是空 返回
				if(!"".equals(newName) && !"".equals(imageUrl)){
					
					try {
						PtBannerImages newimages = new PtBannerImages();

						newimages.setBannerId(bId);
						newimages.setName(newName);
						newimages.setImageUrl(newImageUrl);
						newimages.setAlink(newAlink);
						newimages.setStatus(Integer.parseInt(status[i]));
						newimages.setWeight(Integer.parseInt(weight[i]));
						newimages.setUpTime(up);
						newimages.setDownTime(down);
						int k = bannerImagesService.insertPtBannerImg(newimages);
						if(k == 0){
								SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
								backUserLogService.addLog(u.getUserId(),RequestUtils.getUserIp(request),
										backUserLogService.BANNER_ADD, u.getFullname() + " 添加幻灯片图片 图片ID为：" + bId
										+ "  图片配字：" + newName);
							
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
			} else { // 有图片id更新
				
				try {
					PtBannerImages images = bannerImagesService.queryPtBannerImgById(Integer.parseInt(aids[i]));
					images.setName(newName);
					images.setImageUrl(newImageUrl);
					images.setAlink(newAlink);
					images.setStatus(Integer.parseInt(status[i]));
					images.setWeight(Integer.parseInt(weight[i]));
					images.setUpTime(up);
					images.setDownTime(down);
					int m = bannerImagesService.updatePtBannerImg(images);
					if(m == 0){
						SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
						backUserLogService.addLog(u.getUserId(),RequestUtils.getUserIp(request),
								backUserLogService.BANNER_UPDATE, u.getFullname() + " 更新幻灯片图片 图片ID为：" + bId
								+ "  图片配字：" + newName);
					
				}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			    
	         
			
		}
		return new MessageBean(true,"操作成功!!" + bId);
	}
	
	
	/**
	 * 删除横幅信息 包括图片
	 * 
	 * @param request 
	 * @return 提示信息
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/banner/bannerDelete")
	@ResponseBody
	public MessageBean bannerDelete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		logger.info("del =" + ids);
		if (StringUtils.isBlank(ids)) {
			return new MessageBean(false, "请选择要删除的内容");
		}

		String[] idArr = ids.split(",");
		if (idArr.length > 5) {
			return new MessageBean(false, "最多只能同时删除5条记录");
		}

		for (int i = 0; i < idArr.length; i++){
			
			try {
				int j = bannerService.deletePtBanner(Integer.parseInt(idArr[i]));
				if(j == 0){
					bannerImagesService.delByBannerId(Integer.parseInt(idArr[i]));
				}
				SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
				backUserLogService.addLog(u.getUserId(),RequestUtils.getUserIp(request),
						backUserLogService.BANNER_DELETE, u.getFullname() + " 删除幻灯片ID为：" + idArr[i]);
			} catch (Exception e) {
				logger.error("", e);
			}
		}

		return new MessageBean(true, "删除成功");
	}
	
	
	/**
	 * 一个或多个删除横幅图片
	 * @param request
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/banner/bannerImgDel")
    @ResponseBody
	public MessageBean bannerImgDel(HttpServletRequest request){
		PtBannerImages images = new PtBannerImages();
	
		String[] itemOffset = request.getParameterValues("itemOffset");
		if (itemOffset.length > 0) {
			for (int i = 0; i < itemOffset.length; i++) {
				if ("".equals(itemOffset[i]) || null == itemOffset[i]) {
					continue;
				}
				images = bannerImagesService.queryPtBannerImgById(Integer.parseInt(itemOffset[i]));
				if (null == images) {
					continue;
				}
				int k = bannerImagesService.deletePtBannerImg(images.getId());
				if(k == 0){
					SysUserinfo u = (SysUserinfo) request.getSession().getAttribute(cn.jugame.web.util.GlobalsKeys.ADMIN_KEY);
					backUserLogService.addLog(u.getUserId(),RequestUtils.getUserIp(request),
							backUserLogService.BANNERIMG_DELETE, u.getFullname() + " 删除幻灯片图片 图片ID为：" + images.getId()
							+ "  图片配字：" + images.getName());
				}
			}

		}
		

		return new MessageBean(true,"操作成功");
	}
	
	/**
	 * 图片上传页面
	 */
	@RequestMapping(value = "/file/toupload")
	 public String bannerImgAdd(HttpServletRequest request,Model model) {   
		String imgnumid = RequestUtils.getParameter(request, "imgnumid", "");
		int flag = RequestUtils.getParameterInt(request, "flag", 0);
		model.addAttribute("imgnumid", imgnumid);
		model.addAttribute("flag", flag);
		
		return "file/toupload";
	           
	} 
	
	//删除最后 一个图片[-]
	@RequestMapping(value = "/banner/delLastImages")
    @ResponseBody
	public MessageBean delLastImages(HttpServletRequest request){
		String imagesId = request.getParameter("optionId");
		PtBannerImages images = bannerImagesService.queryPtBannerImgById(Integer.parseInt(imagesId));
		
		if(null == images){
			return new MessageBean(false,"该图片已被删除");
		}
			bannerImagesService.deletePtBannerImg(images.getId());
			return new MessageBean(true,"操作成功");
		
		
	}
	
	//上传文件图片(旧的)
    @RequestMapping(value = "/file/upload1", method = RequestMethod.POST)
    public String handleFormUpload1(MultipartFile file,HttpServletRequest request, Model model) throws IOException{
		//String picPath = request.getSession().getServletContext().getRealPath("/theme");
    	//String datefile = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String picPath = IMGBASEURL;
		String picTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String picType = file.getContentType();
		String fileTureName = MD5.encode(picTime);
		String imgnumid = RequestUtils.getParameter(request, "imgnumid", "");
		if (picType.equals("image/jpeg")) {
			fileTureName = fileTureName.concat(".jpg");
		} else if (picType.equals("image/png")) {
			fileTureName = fileTureName.concat(".png");
		} else if (picType.equals("image/bmp")) {
			fileTureName = fileTureName.concat(".bmp");
		} else if (picType.equals("image/gif")) {
			fileTureName = fileTureName.concat(".gif");
		} else if (picType.equals("image/jpg")){
			fileTureName = fileTureName.concat(".jpg");
		} else {
			model.addAttribute("msg", "请上传图片格式的文件(jpeg/png/bmp/gif/jpg)");
			return "/file/toupload";
		}
		logger.info("banner文件图片大小==" + file.getSize());
		logger.info("banner文件图片类型==" + picType);
		
		float max = 100000;
		if(file.getSize() > max){
			model.addAttribute("msg", "上传的图片不能大于1M");
		}

		// pic.setPicFile(file_ture_name);
		try {
			// picDAO.add(pic);

			/* 保存文件 */
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(picPath, fileTureName));
			model.addAttribute("msg", "图片保存成功");
			model.addAttribute("picvalue", fileTureName);
			model.addAttribute("imgnumid", imgnumid);
			model.addAttribute("picshowurl", IMGSHOWURL + fileTureName);
			return "/file/toupload";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "图片保存失败");
			return "/file/toupload";

		}

    }
    
    /**
	 * 上传banner图片文件(新的上传到ftp)
	 * @param requet
	 * @param fieldName
	 * @return
	 */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	public String handleFormUpload(HttpServletRequest request,MultipartFile  file, Model model){
    	
		try {
			Thread.sleep(5);//sleep一下，防止图片重名
		} catch (Exception e) {
			logger.error("",e);
		}
		try {
			String imgnumid = RequestUtils.getParameter(request, "imgnumid", "");
			int flag = 0;
			flag = RequestUtils.getParameterInt(request, "flag", 0);
			if (file == null){
				model.addAttribute("msg", "图片为空");
				return "/file/toupload";
			}
			String filename = file.getOriginalFilename();
			if (filename.length() <= 0) {
				model.addAttribute("msg", "图片名字为空");
				return "/file/toupload";
			}
			//文件类型判断
			//上传图片类型
			List<String> imgTypeList = new ArrayList<>();
			imgTypeList.add("image/jpg");
			imgTypeList.add("image/jpeg");
			imgTypeList.add("image/png");
			imgTypeList.add("image/bmp");
			int i = imgTypeList.indexOf(file.getContentType());
			// 上传类型不匹配
			if (i < 0) {
				model.addAttribute("msg", "请上传图片格式文件");
				return "/file/toupload";
			}
			String imgtype = FilenameUtils.getExtension(filename);
			Random ran = new Random();
//			String time = System.currentTimeMillis() + "" +  ran.nextInt(100);
			File myFile = File.createTempFile("pic", "." + imgtype);
			//把文件存到本地
			FileOutputStream fos = new FileOutputStream(myFile); 
			fos.write(file.getBytes());
			fos.close(); 
			//上传图片（新的）
//			String imgServiceUrl =Config.getValue("upload.img.url");
			
			String fileUrl = "";
			try {
				//fileUrl = UploadImageUtil.doUpload(imgServiceUrl, myFile);
				//传到阿里云
				fileUrl = AliyunImageUtils.uploadAliyun(myFile, "pt_banner");
				if(StringUtils.isBlank(fileUrl)){
					logger.info("上传图片返回为空。。");
					model.addAttribute("msg", "上传图片返回为空。。");
					return "/file/toupload";
				}
			} catch (Exception e) {
				logger.error("",e);
				model.addAttribute("msg", "图片上传异常。");
				return "/file/toupload";
			}
			
			model.addAttribute("msg", "图片保存成功");
			model.addAttribute("picvalue", "picvalue");
			model.addAttribute("imgnumid", imgnumid);
			model.addAttribute("picshowurl", fileUrl);
			model.addAttribute("flag", flag);
		
			myFile.delete();
			return "/file/toupload";
		} catch (IOException e) {
			logger.error("",e);
			model.addAttribute("msg", "图片保存失败");
			return "/file/toupload";
		}
		
	}
    /**
     * 上传banner图片文件(新的上传到ftp)
     * @param requet
     * @param fieldName
     * @return
     */
    @RequestMapping(value = "/file/annUpload")
    @ResponseBody
    public JSONObject announcementUpload(HttpServletRequest request,MultipartFile  file, Model model){
    	JSONObject jsonObject = new JSONObject();
    	try {
    		Thread.sleep(5);//sleep一下，防止图片重名
    	} catch (Exception e) {
    		logger.error("",e);
    	}
    	try {
    		String filename = file.getOriginalFilename();
    		if (filename.length() <= 0) {
    			jsonObject.put("error", 1);
    			jsonObject.put("message", "图片名字为空");
    			return jsonObject;
    		}
    		//文件类型判断
    		//上传图片类型
    		List<String> imgTypeList = new ArrayList<>();
    		imgTypeList.add("image/jpg");
    		imgTypeList.add("image/jpeg");
    		imgTypeList.add("image/png");
    		imgTypeList.add("image/bmp");
    		int i = imgTypeList.indexOf(file.getContentType());
    		// 上传类型不匹配
    		if (i < 0) {
    			jsonObject.put("error", 1);
    			jsonObject.put("message", "请上传图片格式文件");
    			return jsonObject;
    		}
    		String imgtype = FilenameUtils.getExtension(filename);
    		Random ran = new Random();
//			String time = System.currentTimeMillis() + "" +  ran.nextInt(100);
    		File myFile = File.createTempFile("pic", "." + imgtype);
    		//把文件存到本地
    		FileOutputStream fos = new FileOutputStream(myFile); 
    		fos.write(file.getBytes());
    		fos.close(); 
    		//上传图片（新的）
//			String imgServiceUrl =Config.getValue("upload.img.url");
    		
    		String fileUrl = "";
    		try {
    			//fileUrl = UploadImageUtil.doUpload(imgServiceUrl, myFile);
    			//传到阿里云
    			fileUrl = AliyunImageUtils.uploadAliyun(myFile, "pt_banner");
    			if(StringUtils.isBlank(fileUrl)){
    				logger.info("上传图片返回为空。。");
    				jsonObject.put("error", 1);
        			jsonObject.put("message", "上传图片返回为空。。");
        			return jsonObject;
    			}
    		} catch (Exception e) {
    			logger.error("",e);
    			jsonObject.put("error", 1);
    			jsonObject.put("message", "图片上传异常。");
    			return jsonObject;
    		}
    		
    		jsonObject.put("error", 0);
			jsonObject.put("message", "图片保存成功");
			jsonObject.put("url",fileUrl);
    		
    		myFile.delete();
    		return jsonObject;
    	} catch (IOException e) {
    		logger.error("",e);
    		jsonObject.put("error", 1);
			jsonObject.put("message", "图片保存失败");
    		return jsonObject;
    	}
    	
    }
    
    
    
    
    
	// 读取横幅图片
	@RequestMapping(value = "/readbannerImg")
	public void readbannerImg(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String  imgurl = request.getParameter("imgurl");
			PtBannerImages bb = bannerImagesService.queryPtBannerImgById(Integer.parseInt(imgurl));
		
			String url = IMGBASEURL + bb.getImageUrl();
			logger.info("url===" + url);
			File file = new File(url);
			byte[] b = new byte[(int) file.length()];
			InputStream inputStream;
			try {
				inputStream = new FileInputStream(file);
				inputStream.read(b);
				response.setContentType("image/jpeg");
				response.setHeader("Cathe-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
				response.getOutputStream().write(b);
				inputStream.close();
				response.getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("图片地址不存在=" + url);
			}
	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

	
	

