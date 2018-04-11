//package cn.jugame.admin.web.controller;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//
//import javax.servlet.http.HttpServletRequest;
//
//import jxl.read.biff.BiffException;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import cn.jugame.web.util.MessageBean;
//import cn.jugame.web.util.ReadProductXlsUtils;
//import cn.jugame.web.util.RequestUtils;
//import cn.juhaowan.localmessage.service.LocalMessageService;
//import cn.juhaowan.log.service.BackUserLogService;
//import cn.juhaowan.member.service.MemberAccountService;
//import cn.juhaowan.member.service.MemberService;
//import cn.juhaowan.member.service.MemberServiceService;
//import cn.juhaowan.member.service.MemberTakeMoneyService;
//import cn.juhaowan.product.service.ProductService;
//
//
///**
// * 商品批量跟新
// * @author Administrator
// *
// */
//@Controller
//public class ProductUpdateController {
//	Logger logger = LoggerFactory.getLogger(ProductUpdateController.class);
//
//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private MemberAccountService memberAccountService;
//
//	@Autowired
//	private MemberTakeMoneyService memberTakeMoneyService;
//
//	@Autowired
//	private BackUserLogService backUserLogService;
//	
//	@Autowired
//	private ProductService productService;
//
//	@Autowired
//	private LocalMessageService localMessageService;
//	
//	@Autowired
//	private MemberServiceService memberServiceService;
//
//
//	private static String PRODUCT_XLS_DIR;
//	//最大导入行数
//	private static int MAXROWS = 8000;
//
//	static {
//		Properties pro = new Properties();
//		
//		try {
//			pro.load(new FileInputStream(ProductUpdateController.class.getClassLoader().getResource("resources.properties").getPath()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		PRODUCT_XLS_DIR = pro.getProperty("banner.imgbaseurl");
//	}
//	
//
//	
//	/**
//	 * xls文件上传页面
//	 */
//	@RequestMapping(value = "/inputproductonsale/updategameareashow")
//	 public String updateGameAreaShow(HttpServletRequest request,Model model) {  
//		String path = request.getContextPath();
//		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//		model.addAttribute("basePath", basePath);
//		return "/inputproductonsale/updategameareashow";
//	           
//	} 
//	
//	 @RequestMapping(value = "/inputproductonsale/updategamearea", method = RequestMethod.POST)
//	    public String updateGameArea(MultipartFile file,HttpServletRequest request, Model model) throws IOException{
//			String picPath = PRODUCT_XLS_DIR;
//			String picTime = new SimpleDateFormat("yyyy-MM-dd-hhmmSS").format(new Date());
//			String picType = file.getContentType();
//			String fileTureName =  "product_update_" + picTime + ".xls";
//			String fileUrl = picPath + fileTureName;
//			File xlsfile = new File(picPath,fileTureName);
//			
//			
//			String path = request.getContextPath();
//			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//			model.addAttribute("basePath", basePath);
//			
//            //判断文件格式
//			if(!"application/vnd.ms-excel".equals(picType)){
//				model.addAttribute("msg", "请上传xls格式的文件");
//				return "/inputproductonsale/updategameareashow";
//			}
//			
//			try {
//
//				/* 保存文件 */
//				FileUtils.copyInputStreamToFile(file.getInputStream(), xlsfile);
//			
//			} catch (Exception e) {
//
//				model.addAttribute("msg", "文件保存失败" + e);
//				return "/inputproductonsale/updategameareashow";
//
//			}
//			//xls文件行数
//			int rowsnum = 0;
//			try {
//				rowsnum = ReadProductXlsUtils.readXlsRows(fileUrl, 0);
//				rowsnum = rowsnum - 1;
//			} catch (Exception e2) {
//				model.addAttribute("msg", "读取xls文件行数错误");
//				return "/inputproductonsale/updategameareashow";
//			}
//			if(rowsnum > MAXROWS){
//				model.addAttribute("msg", "请不要一次性更新超过8000条数据");
//				return "/inputproductonsale/updategameareashow";
//			}
//			
//			
//			//判断是否是正确文件
//			String xlsTitlePID = "";      //xls标题 商品id
//			//String xlsTitlePName = "";    //xls标题 商品名称
//			String xlsTitleGameAreaId = ""; //xls标题 商品存放位置
//			
//			try {
//				xlsTitlePID = ReadProductXlsUtils.readProductTitle(fileUrl, 0, 0, 0);
//				//xlsTitlePName = ReadProductXlsUtils.readProductTitle(fileUrl, 0, 0, 1);
//				xlsTitleGameAreaId = ReadProductXlsUtils.readProductTitle(fileUrl, 0, 0, 1);
//
//				if(!"商品ID".equals(xlsTitlePID) || !"游戏分区id".equals(xlsTitleGameAreaId)){
//					//删除错误文件
//					if(xlsfile.exists()){
//						xlsfile.delete();
//					}
//					model.addAttribute("msg", "请上传模板文件格式的xls文件");
//					return "/inputproductonsale/updategameareashow";
//				}
//			} catch (Exception e) {
//				//删除错误文件
//				if(xlsfile.exists()){
//					xlsfile.delete();
//				}
//				model.addAttribute("msg", "请上传正确的xls文件");
//				return "/inputproductonsale/updategameareashow";
//			}
//			
//		    //判断字段是否存在空值
//			String nullvalue = "";
//			String numvalue = "";
//			
//			try {
//			    //需要判断非空的字段
//				 int [] colnull = {1,2};
//				nullvalue = ReadProductXlsUtils.readXlsNull(fileUrl, 0, colnull);
//				if(!"".equals(nullvalue)){
//					//删除错误文件
//					if(xlsfile.exists()){
//						xlsfile.delete();
//					}
//					model.addAttribute("msg", nullvalue);
//					return "/inputproductonsale/updategameareashow";
//				}
//				//需要判断数字的字段
//				 int [] colnum = {2};
//				 numvalue = ReadProductXlsUtils.readisNotNum(fileUrl, 0, colnum);
//				 if(!"".equals(numvalue)){
//					   //删除错误文件
//						if(xlsfile.exists()){
//							xlsfile.delete();
//						}
//					 model.addAttribute("msg", numvalue);
//					 return "/inputproductonsale/updategameareashow";
//				 }
//
//			} catch (Exception e1) {
//				//删除错误文件
//				if(xlsfile.exists()){
//					xlsfile.delete();
//				}
//				 model.addAttribute("msg", "请检查xls数据是否有误");
//				 return "/inputproductonsale/updategameareashow";
//			}
//			
//			//显示导入的商品id列表
//			List<String> listProductId = null;
//			
//			try {
//				listProductId = ReadProductXlsUtils.readProductIdList(fileUrl, 0, 1, MAXROWS);
//			} catch (BiffException e) {
//				listProductId = null;
//			}
//			
//		
//			model.addAttribute("rowsnum", rowsnum);
//			model.addAttribute("listProductId", listProductId);
//			model.addAttribute("msg", "xls文件上传成功");
//			model.addAttribute("fileName", fileTureName);
//			model.addAttribute("fileUrl", picPath + fileTureName);
//			return "/inputproductonsale/updategameareashow";
//
//	    }
//	 
//		@RequestMapping(value = "/inputproductonsale/updategameareasql")
//		@ResponseBody
//		 public MessageBean updateGameAreasql(HttpServletRequest request,Model model) {   
//			String fileUrl = RequestUtils.getParameter(request, "fileUrl", "");
//			 //xls标签页 一般为0
//			 int sheetIndex = 0;
//			 //开始转换行 0行为标题
//			 int start = 1;
//			 //结束转换行 
//			 int end = MAXROWS;
//			 //读取xls商品id列表
//			 List<String> productIdList = null;
//			 //转换之后的sql导入语句【商品表】
//			 List<String> sqlProductList = null;
//			 //转换之后的sql导入语句【商品上架表】
//			 List<String> sqlProductOnsaleList = null;
//	
//			 
//			 //判断xls商品id在数据库是否已经存在
//			 
//			 try {
//				productIdList = ReadProductXlsUtils.readProductIdList(fileUrl, sheetIndex, start, end);
//				if(null != productIdList){
//					for(int j = 0;j < productIdList.size();j++){
//						if(StringUtils.isNotBlank(productIdList.get(j))){
//							int m = productService.productIdExist(productIdList.get(j));
//							if(m == 1){
//								 return new MessageBean(false,"XLS文件第 " + (j + 1) + "行的商品ID不存在");      
//							}
//						}
//					}	
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			} 
//			 
//			//批量更新
//			 try {
//				//商品表
//				 sqlProductList = ReadProductXlsUtils.convert(fileUrl, ReadProductXlsUtils.updateProductGameAreaSql("product"),sheetIndex, start, end);
//				//商品上架表
//				 sqlProductOnsaleList = ReadProductXlsUtils.convert(fileUrl, ReadProductXlsUtils.updateProductGameAreaSql("product_onsale"),sheetIndex, start, end);
//				
//			} catch (Exception e) {
//		
//				 return new MessageBean(false,"读取xls失败"); 
//			}
//			//商品表
//			if(null != sqlProductList){
//				for(int i = 0;i < sqlProductList.size();i++){
//					//System.out.println("sql==" + sqlProductList.get(i));
//					int h = productService.productOnsaleSql(sqlProductList.get(i));
//					if(h == 1){
//						 return new MessageBean(false,"更新商品表sql语句错误"); 
//					}
//				}
//			}
//			//商品上架表
//			if(null != sqlProductOnsaleList){
//				for(int o = 0;o < sqlProductOnsaleList.size();o++){
//					//System.out.println("sql==" + sqlProductOnsaleList.get(o));
//					int h = productService.productOnsaleSql(sqlProductOnsaleList.get(o));
//					if(h == 1){
//						 return new MessageBean(false,"更新商品上架表sql语句错误"); 
//					}
//				}
//			}
//
//			
//			//读取导入成功数量
//			int rowsnum = 0;
//			try {
//				rowsnum = ReadProductXlsUtils.readXlsRows(fileUrl, sheetIndex);
//				rowsnum = rowsnum - 1;
//			} catch (Exception e) {
//				 return new MessageBean(false,"获取xls文件总行数出错"); 
//			} 
//			 return new MessageBean(true,"成功更新商品总数" + rowsnum + "行");      
//		  
//		} 
//
//}
