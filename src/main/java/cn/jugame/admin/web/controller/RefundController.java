package cn.jugame.admin.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.jugame.service.ISysParameterService;
import cn.jugame.upload.IFileUpload;
import cn.jugame.util.MD5;
import cn.jugame.vo.SysParameter;
import cn.jugame.web.util.BackUserLogUtil;
import cn.jugame.web.util.MessageBean;
import cn.jugame.web.util.RefundUtil;
import cn.jugame.web.util.RequestUtils;
import cn.jugame.web.util.sendMsgUtil;
import cn.juhaowan.log.service.BackUserLogService;
import cn.juhaowan.member.dao.MemberAccountDao;
import cn.juhaowan.member.service.MemberAccountService;
import cn.juhaowan.member.service.MemberService;
import cn.juhaowan.member.vo.Member;
import cn.juhaowan.order.dao.OrderPayInfoDao;
import cn.juhaowan.product.service.ProductService;
import cn.juhaowan.sms.service.SMSService;

/**
 * 人工充值、转账
 * 
 * @author APXer
 * 
 */
@Controller
public class RefundController {
	private static Logger SMS = LoggerFactory.getLogger(RefundController.class);
	Logger logger = LoggerFactory.getLogger(RefundController.class);
	@Autowired
	private SMSService smsService;
	@Autowired
	private MemberAccountService memberAccountService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BackUserLogService backUserLogService;
	@Autowired
	private ProductService productService;
	@Autowired
	protected IFileUpload fileUpload;
	@Autowired
	private ISysParameterService iSysParameterService;

	/**
	 * 获取短信接收者手机号
	 * 
	 * @param amount 交易金额
	 * @return
	 */
	private String getSmsRecipients(double amount) {
//		Properties pro = new Properties();
//		try {
//			pro.load(new FileInputStream(//
//					RefundController.class.getClassLoader().getResource("resources.properties").getPath()));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String[] recipients = pro.getProperty("recipients.sms.member").split(",");
//		String recipientMobile = null;
//		for (String recipient : recipients) {
//			double min = Double.parseDouble(pro.getProperty(recipient + ".min"));
//			double max = Double.parseDouble(pro.getProperty(recipient + ".max"));
//			if (min <= amount && amount < max) {
//				recipientMobile = pro.getProperty(recipient + ".mobile");
//				break;
//			}
//		}
//		return recipientMobile;
		
		List<SysParameter> recipientsList = iSysParameterService.listByParaCode("recipients.sms.member");
		String recipients = recipientsList.get(0).getParaValue();
		String[] recipientsArr = recipients.split(",");
		String recipientMobile = null;
		for (String recipient : recipientsArr) {
			double min = Double.parseDouble(iSysParameterService.listByParaCode(recipient + ".min").get(0).getParaValue());
			double max = Double.parseDouble(iSysParameterService.listByParaCode(recipient + ".max").get(0).getParaValue());
			if (min <= amount && amount < max) {
				recipientMobile = iSysParameterService.listByParaCode(recipient + ".mobile").get(0).getParaValue();
				break;
			}
		}
		return recipientMobile;
	}

	/**
	 * 显示人工退款页面
	 * 
	 * @param request 请求参数
	 * @param model 模型
	 * @return 转向地址
	 */
	@RequestMapping(value = "/order/refundShow")
	public String refundShow(HttpServletRequest request, Model model) {
		return "order/refundShow";
	}

	/**
	 * 显示人工充值页面
	 * 
	 * @param request 请求参数
	 * @param model 模型
	 * @return 转向地址
	 */
	@RequestMapping(value = "/order/rechargeShow")
	public String rechargeShow(HttpServletRequest request, Model model) {
		return "order/rechargeShow";
	}

	/**
	 * 显示人工转账页面
	 * 
	 * @param request 请求参数
	 * @param model 模型
	 * @return 转向地址
	 */
	@RequestMapping(value = "/order/transferShow")
	public String transferShow(HttpServletRequest request, Model model) {
		return "order/transferShow";
	}

	/**
	 * 显示会员信息
	 * 
	 * @param request 请求
	 * @return 会员信息
	 */
	@RequestMapping(value = "/order/displayMemberInfo")
	@ResponseBody
	public JSONObject displayMemberInfo(HttpServletRequest request) {
		JSONObject data = new JSONObject();
		String uid = request.getParameter("uid");
		if (StringUtils.isBlank(uid)) {
			return null;
		}
		Member member = null;
		try {
			member = memberService.findMemberByUid_back(Integer.parseInt(uid));
		} catch (Exception e) {
			return null;
		}
		if (member != null) {
			data.put("status", "true");
			data.put("uid", member.getUid() + "");
			data.put("notCash", memberAccountService.queryBalance(member.getUid(), MemberAccountDao.ACCOUNT_TYPE_NOT_CASH));
			data.put("cash", memberAccountService.queryBalance(member.getUid(), MemberAccountDao.ACCOUNT_TYPE_CASH));
			data.put("loginName", member.getLoginName());
			data.put("nickName", member.getNickName());
			data.put("mobile", member.getMobile());
			String userType = "未知用户类型";
			if (member.getUserType() == 1) {
				userType = "普通用户";
			} else if (member.getUserType() == 2) {
				userType = "VIP用户";
			}
			data.put("userType", userType);
			data.put("isRealValidate", //
					member.getRealValidate() == 0 ? "未认证" : member.getRealValidate() == 1 ? "认证通过" : "未知状态");
		}
		return data;
	}

	/**
	 * 人工退款
	 * 
	 * @param request 请求参数
	 * @param model 模型
	 * @return 转向地址
	 */
	@RequestMapping(value = "/order/refund")
	@ResponseBody
	public MessageBean refund(HttpServletRequest request, Model model) {
		String uid = request.getParameter("uid");
		String orderId = request.getParameter("orderId");
		String amount = request.getParameter("amount");
		String remark = request.getParameter("remark");
		String verifyCode = request.getParameter("vcode");
		String accountType = request.getParameter("atype");
		// 前提条件订单号不为空,校验码不为空
		if (StringUtils.isBlank(uid)) {
			return new MessageBean(false, "请输入用户ID");
		}
		if (StringUtils.isBlank(orderId)) {
			return new MessageBean(false, "请输入订单号");
		}
		if (StringUtils.isBlank(amount)) {
			return new MessageBean(false, "请输入充值金额");
		}
		if (StringUtils.isBlank(remark)) {
			return new MessageBean(false, "请填写退款原因");
		}
		if (StringUtils.isBlank(verifyCode)) {
			return new MessageBean(false, "请填写校验码");
		}
		if (StringUtils.isBlank(accountType)) {
			return new MessageBean(false, "请选择账户类型");
		}
		Member member = memberService.findMemberByUid_back(Integer.parseInt(uid));
		if (null == member) {
			return new MessageBean(false, "用户不存在");
		}
		Integer accountTypeI = null;
		int accountTypeInt = Integer.parseInt(accountType);
		if (accountTypeInt == MemberAccountDao.ACCOUNT_TYPE_CASH) {
			accountTypeI = MemberAccountDao.ACCOUNT_TYPE_CASH;
		} else if (accountTypeInt == MemberAccountDao.ACCOUNT_TYPE_NOT_CASH) {
			accountTypeI = MemberAccountDao.ACCOUNT_TYPE_NOT_CASH;
		}

		// 2.退款--用户平台资金帐户
		double amountD = Double.parseDouble(amount);
		String recipientMobile = getSmsRecipients(amountD);
		boolean isSuccess = RefundUtil.checkRefundVCode(recipientMobile, verifyCode);
		if (isSuccess) {
			RefundUtil.removeVerifyCode(recipientMobile);
			String fee2 = RequestUtils.getParameter(request, "fee2", "0");
			if (StringUtils.isBlank(fee2)) {
				return new MessageBean(false, "请选择费率");
			}
			int fee2Int = Integer.parseInt(fee2);
			double feeD = 0;
			if (fee2Int == 0) {
				feeD = 0;
			} else if (fee2Int == 1) {
				feeD = Double.parseDouble(String.format("%.2f", amountD * 0.02));
			} else if (fee2Int == 2) {
				feeD = Double.parseDouble(String.format("%.2f", amountD * 0.06));
			}
			String chargeID = productService.generationProductId("MCZ");
			int chargeFlag = memberAccountService.recharge(//
					Integer.parseInt(uid), //
					OrderPayInfoDao.PAY_PLATFORM_JUGAME, accountTypeI,//
					amountD,//
					feeD, //
					"", "", "", chargeID, new Date(),//
					(System.currentTimeMillis() / 1000L) + "", orderId + remark);
			Map<String, String> map = BackUserLogUtil.findUserInfo(request);
			backUserLogService.addLog(Integer.parseInt(map.get(BackUserLogUtil.USER_ID)), map.get(BackUserLogUtil.USER_IP), BackUserLogService.ARTIFICIAL_REFUND, "管理员：" + map.get(BackUserLogUtil.USER_NAME) + " 对UID为[" + uid + "]的用户进行手动充值操作,金额: "
					+ amountD + ",手续费: " + feeD);

			if (chargeFlag == 0) {
				return new MessageBean(true, "充值操作成功");
			} else {
				return new MessageBean(true, "充值操作失败");
			}
		} else {
			return new MessageBean(false, "你的校验码有误");
		}
	}

	/**
	 * 人工充值
	 * 
	 * @param request 请求参数
	 * @param model 模型
	 * @return 转向地址
	 */
	@RequestMapping(value = "/order/recharge")
	@ResponseBody
	public MessageBean recharge(HttpServletRequest request, Model model) {
		String uid = request.getParameter("uid");
		String orderId = request.getParameter("orderId");
		String amount = request.getParameter("amount");
		String remark = request.getParameter("remark");
		String verifyCode = request.getParameter("vcode");
		String accountType = request.getParameter("atype");
		// 前提条件订单号不为空,校验码不为空
		if (StringUtils.isBlank(uid)) {
			return new MessageBean(false, "请输入用户ID");
		}
		if (StringUtils.isBlank(orderId)) {
			return new MessageBean(false, "请输入订单号");
		}
		if (StringUtils.isBlank(amount)) {
			return new MessageBean(false, "请输入充值金额");
		}
		if (StringUtils.isBlank(remark)) {
			return new MessageBean(false, "请填写退款原因");
		}
		if (StringUtils.isBlank(verifyCode)) {
			return new MessageBean(false, "请填写校验码");
		}
		if (StringUtils.isBlank(accountType)) {
			return new MessageBean(false, "请选择账户类型");
		}
		Member member = memberService.findMemberByUid_back(Integer.parseInt(uid));
		if (null == member) {
			return new MessageBean(false, "用户不存在");
		}
		Integer accountTypeI = null;
		int accountTypeInt = Integer.parseInt(accountType);
		if (accountTypeInt == MemberAccountDao.ACCOUNT_TYPE_CASH) {
			accountTypeI = MemberAccountDao.ACCOUNT_TYPE_CASH;
		} else if (accountTypeInt == MemberAccountDao.ACCOUNT_TYPE_NOT_CASH) {
			accountTypeI = MemberAccountDao.ACCOUNT_TYPE_NOT_CASH;
		}
		double amountD = Double.parseDouble(amount);
		String recipientMobile = getSmsRecipients(amountD);
		boolean isSuccess = false;// RefundUtil.checkRefundVCode(recipientMobile, verifyCode);
		
		String result = sendMsgUtil.verify(verifyCode, recipientMobile);
		if (StringUtils.isBlank(result)) {
			logger.info(recipientMobile + ",检校校验码失败" + verifyCode);
		} else {

			logger.info(recipientMobile + "verify sms result:" + result);
			JSONObject json = JSONObject.fromObject(result);
			if (json.getBoolean("data")) {
				isSuccess = true;
			}
		}
		if (isSuccess) {
			RefundUtil.removeVerifyCode(recipientMobile);
			int chargeFlag = memberAccountService.rechargeForUser(uid, amount, accountTypeI + "", "99", orderId + remark);
			Map<String, String> map = BackUserLogUtil.findUserInfo(request);
			backUserLogService.addLog(Integer.parseInt(map.get(BackUserLogUtil.USER_ID)), map.get(BackUserLogUtil.USER_IP), BackUserLogService.ARTIFICIAL_REFUND, "管理员：" + map.get(BackUserLogUtil.USER_NAME) + " 对UID为[" + uid + "]的用户进行手动充值操作,金额: "
					+ amountD);

			if (chargeFlag == 0) {
				return new MessageBean(true, "充值操作成功");
			} else {
				return new MessageBean(true, "充值操作失败");
			}
		} else {
			return new MessageBean(false, "你的校验码有误");
		}
	}

	/**
	 * 发送校验码
	 * 
	 * @param request 请求
	 * @return 温馨提示
	 */
	@RequestMapping(value = "order/sendVerifyCode")
	@ResponseBody
	public MessageBean sendVerifyCode(HttpServletRequest request) {
		MessageBean mb = null;

		String amount = request.getParameter("amount");
		if (StringUtils.isBlank(amount)) {
			return new MessageBean(false, "充值金额不能为空");
		}
		//发送到哪个手机号码
		double amountD = Double.parseDouble(amount);
		String recipientMobile = getSmsRecipients(amountD);
		
//		// 1.生成6位校验码:您正在使用人工退款服务,校验码:123456【任何人向您索取校验码均为诈骗,请勿泄漏】 【聚好玩】
//		String verifyCode = RefundUtil.randomVerifyCode();
//		// String smsContent = "您正在使用人工充值服务，校验码:" + verifyCode + "，15分钟内有效。【任何人向您索取校验码均为诈骗,请勿泄漏】【聚好玩】";
//		String smsContent = "您正在使用人工充值服务，校验码：" + verifyCode + "，15分钟内有效。【8868交易平台】";
//		SMS.info("[后台充值转账短信]" + smsContent);
//		
//		int result = smsService.sendSms(recipientMobile, smsContent);
		
		String resesult = sendMsgUtil.send(recipientMobile);
		if (StringUtils.isBlank(resesult)) {
			mb = new MessageBean(false, "登录发送校验码失败");
		} else {
			logger.info(recipientMobile + "，send sms result:" + resesult + ",amountD: " + amountD);
			JSONObject json = JSONObject.fromObject(resesult);
			if(json.getInt("code") == 0){
				mb = new MessageBean(true, "发送校验码成功");
			}else{
				mb = new MessageBean(false, json.getString("message"));
			}
			
		}
		
//		if (result == 0) {
//			logger.info("send sms result:" + result);
//			// 3.存储校验码
//			SMS.info("[存储验证码2]" + verifyCode);
//			RefundUtil.storyVerfiyCode(recipientMobile, verifyCode);
//			mb = new MessageBean(true, "发送校验码成功");
//		} else {
//			mb = new MessageBean(true, "发送校验码失败");
//		}
		return mb;
	}

//	// excel保存地址
//	private static String EXCELUPLOADURL;
//	// // excel读取地址
//	// private static String EXCELDOWNLOADURL;
//	static {
//		Properties pro = new Properties();
//		try {
//			pro.load(new FileInputStream(RefundController.class.getClassLoader().getResource("resources.properties").getPath()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		EXCELUPLOADURL = pro.getProperty("batchCharge.excelUploadUrl");
//		// EXCELDOWNLOADURL = pro.getProperty("batchCharge.excelDownloadUrl");
//	}

	/**
	 * Excel文件上传页面
	 */
	@RequestMapping(value = "/order/batchChargeView")
	public String batchChargeView(HttpServletRequest request, Model model) {
		return "order/batchChargeView";
	}

	/**
	 * Excel文件上传
	 * 
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/order/excelUpload", method = RequestMethod.POST)
	public String excelUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) throws IOException {
		//String excelUplodPath = EXCELUPLOADURL;
		String picTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// String excelType = file.getContentType();
		String originalFileName = file.getOriginalFilename();
		String excelFileName = MD5.encode(picTime);
		if (originalFileName.endsWith(".xls")) {
			excelFileName = excelFileName.concat(".xls");
		} else {
			model.addAttribute("msg", "请上传Excel的文件(xls)");
			return "order/batchChargeView";
		}
		logger.info("Excel文件大小==" + file.getSize());

		float max = 50000;
		if (file.getSize() > max) {
			model.addAttribute("msg", "上传的Excel文件不能大于512KB");
		}
		try {
			String filename = file.getOriginalFilename();
			String imgtype = FilenameUtils.getExtension(filename);
			String time = System.currentTimeMillis() + "" + new Random().nextInt(100);
			String newFileName = time + "." + imgtype;
			File myFile = File.createTempFile("xls", "." + imgtype);
			// 把文件存到本地
			FileOutputStream fos = new FileOutputStream(myFile);
			fos.write(file.getBytes());
			fos.close();

			// 上传文件
			String fileUrl = fileUpload.upload(myFile, "batchCharge_" + newFileName, false);

			myFile.delete();
			/* 保存文件 */
			// FileUtils.copyInputStreamToFile(file.getInputStream(), new File(excelUplodPath, excelFileName));
			model.addAttribute("msg", "Excel文件上传成功");
			model.addAttribute("excelFileName", fileUrl);
			// model.addAttribute("excelDownloadURL", EXCELDOWNLOADURL +
			// excelFileName);
			return "order/batchCharge";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "Excel文件上传失败");
			return "order/batchChargeView";
		}
	}

	/**
	 * 发送批量充值校验码
	 * 
	 * @param request 请求
	 * @return 温馨提示
	 */
	@RequestMapping(value = "order/sendBatchChargeVerifyCode")
	@ResponseBody
	public MessageBean sendBatchChargeVerifyCode(HttpServletRequest request) {
		MessageBean mb = null;
		String adminMobile = getSmsRecipients(99999);
		String verifyCode = RefundUtil.randomVerifyCode();
		String smsContent = "您正在使用批量充值服务，校验码：" + verifyCode + "，15分钟内有效。【8868交易平台】";
		SMS.info("[批量充值短信]" + smsContent);
		int result = smsService.sendSms(adminMobile, smsContent);
		if (result == 0) {
			logger.info("短信响应结果:" + result);
			// 3.存储校验码
			SMS.info("[存储验证码2]" + verifyCode);
			RefundUtil.storyVerfiyCode(adminMobile, verifyCode);
			mb = new MessageBean(true, "发送校验码成功");
		} else {
			mb = new MessageBean(true, "发送校验码失败");
		}
		return mb;
	}

	/**
	 * 批量充值
	 * 
	 * @param request 请求
	 * @return 温馨提示
	 */
	@RequestMapping(value = "order/batchCharge")
	@ResponseBody
	public MessageBean batchCharge(HttpServletRequest request, Model model) {
		String excelFileName = request.getParameter("excelFile");
		String remark = request.getParameter("remark");
		String verifyCode = request.getParameter("vcode");
		String accountType = request.getParameter("accountType");
		if (StringUtils.isBlank(excelFileName)) {
			return new MessageBean(false, "请输入上传后的Excel文件名称");
		}
		if (StringUtils.isBlank(remark)) {
			return new MessageBean(false, "请填写退款原因");
		}
		if (StringUtils.isBlank(verifyCode)) {
			return new MessageBean(false, "请填写校验码");
		}
		int accountTypeI = Integer.parseInt(accountType);
		String adminMobile = getSmsRecipients(99999);
		boolean isSuccess = RefundUtil.checkRefundVCode(adminMobile, verifyCode);
		if (isSuccess) {
			RefundUtil.removeVerifyCode(adminMobile);
			int successCount = 0;
			double amountAll = 0;
			int uid = 0;
			Double amount = 0.0;
			StringBuffer noExistsUID = new StringBuffer();
			try {
				// File file = new File(EXCELUPLOADURL + File.separator + excelFileName);
				URL u = new URL(excelFileName);// 这个是ftp文件完整地址
				URLConnection urlconn = u.openConnection();
				if (urlconn != null) {
					// InputStream is = new FileInputStream();
					HSSFWorkbook wb = new HSSFWorkbook(urlconn.getInputStream());
					HSSFSheet sheet = wb.getSheetAt(0);
					int totalRecord = sheet.getLastRowNum() + 1;
					if (totalRecord > 501) {
						return new MessageBean(false, "批处理记录数过大,因小于500");
					}
					for (int i = 1; i < totalRecord; i++) {
						HSSFRow row = sheet.getRow(i);
						int columns = row.getLastCellNum();

						for (int j = 0; j < columns; j++) {
							if (row.getCell((short) 0) == null) {// 防止无成员溢出
								break;
							}
							uid = (int) row.getCell((short) 0).getNumericCellValue();
							amount = row.getCell((short) 1).getNumericCellValue();

						}
						Member member = memberService.findMemberByUid_back(uid);
						if (null == member) {
							noExistsUID.append(uid + " ");
							break;
						}
						String chargeID = productService.generationProductId("BCZ");

						int chargeFlag = memberAccountService.rechargeForUser(uid + "", amount + "", accountTypeI + "", "99", chargeID + remark);
						if (chargeFlag == 0) {
							successCount = i;
							amountAll = amount + amountAll;
						}
					}
					Map<String, String> map = BackUserLogUtil.findUserInfo(request);
					backUserLogService.addLog(0, map.get(BackUserLogUtil.USER_IP), BackUserLogService.ARTIFICIAL_REFUND, "管理员：" + map.get(BackUserLogUtil.USER_NAME) + " 对" + excelFileName + "文件中的用户进行手动充值操作,总人数:" + (totalRecord - 1) + ",充值总额: "
							+ amountAll + ",成功充值人数：" + successCount + "。");

				}
			} catch (Exception e) {
				e.printStackTrace();
				return new MessageBean(false, "Excel文件内容有误");
			}
			String msg = "批量充值操作成功。成功充值人数：" + successCount + "。";
			if (null != noExistsUID.toString()) {
				msg += "操作不存在的UID:" + noExistsUID + "。";
			}
			return new MessageBean(true, msg);
		} else {
			return new MessageBean(false, "校验码校验失败");
		}
	}

	/**
	 * 生成模板文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "order/generateTemplate")
	@ResponseBody
	public void generateTemplate(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		try {
			response.setHeader("Content-disposition", "inline;filename=" + new String("批量充值模板.xls".getBytes("utf-8"), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("批量充值");
			HSSFRow field = sheet.createRow(0);
			HSSFCell uidCell = field.createCell((short) 0);
			uidCell.setCellValue(new HSSFRichTextString("UID"));
			HSSFCell amountCell = field.createCell((short) 1);
			amountCell.setCellValue(new HSSFRichTextString("充值金额"));
			wb.write(sos);
			sos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 人工转账
	 * 
	 * @param request 请求参数
	 * @param model 模型
	 * @return 转向地址
	 */
	@RequestMapping(value = "/order/transfer")
	@ResponseBody
	public MessageBean transfer(HttpServletRequest request, Model model) {
		String fromUid = request.getParameter("fromUid");
		String toUid = request.getParameter("toUid");
		String cash = request.getParameter("cash");
		String notCash = request.getParameter("notCash");
		String remark = request.getParameter("remark");
		String verifyCode = request.getParameter("vcode");
		// 前提条件订单号不为空,校验码不为空
		if (StringUtils.isBlank(fromUid)) {
			return new MessageBean(false, "请输入转出用户ID");
		}
		if (StringUtils.isBlank(toUid)) {
			return new MessageBean(false, "请输入转入用户ID");
		}
		if (StringUtils.isBlank(cash)) {
			return new MessageBean(false, "请输入转账现金金额");
		}
		if (StringUtils.isBlank(cash)) {
			return new MessageBean(false, "请输入转账非现金金额");
		}
		if (StringUtils.isBlank(remark)) {
			return new MessageBean(false, "请填写转账原因");
		}
		if (StringUtils.isBlank(verifyCode)) {
			return new MessageBean(false, "请填写校验码");
		}
		//test--
//		String recipientMobile3 = getSmsRecipients(1);
//		boolean isSuccess3 = RefundUtil.checkRefundVCode(recipientMobile3, verifyCode);
		
		Member mFrom = memberService.findMemberByUid_back(Integer.parseInt(fromUid));
		if (null == mFrom) {
			return new MessageBean(false, "转出用户不存在");
		}
		Member mTo = memberService.findMemberByUid_back(Integer.parseInt(toUid));
		if (null == mTo) {
			return new MessageBean(false, "转入用户不存在");
		}
		int fromUidI = Integer.parseInt(fromUid);
		double notCashD = Double.parseDouble(notCash);
		double cashD = Double.parseDouble(cash);
		double notCashBalance = memberAccountService.queryBalance(fromUidI, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH);
		double cashBalance = memberAccountService.queryBalance(fromUidI, MemberAccountDao.ACCOUNT_TYPE_CASH);
		if (notCashD > notCashBalance) {
			return new MessageBean(false, "非现金余额不足，不能转账");
		}
		if (cashD > cashBalance) {
			return new MessageBean(false, "现金余额不足，不能转账");
		}

		double totalAmount = cashD + notCashD;
		String recipientMobile = getSmsRecipients(totalAmount);
//		boolean isSuccess = RefundUtil.checkRefundVCode(recipientMobile, verifyCode);
		boolean isSuccess = false;// RefundUtil.checkRefundVCode(recipientMobile, verifyCode);
		
		String result = sendMsgUtil.verify(verifyCode, recipientMobile);
		if (StringUtils.isBlank(result)) {
			logger.info(recipientMobile + ",检校校验码失败" + verifyCode);
		} else {

			logger.info(recipientMobile + "verify sms result:" + result);
			JSONObject json = JSONObject.fromObject(result);
			if (json.getBoolean("data")) {
				isSuccess = true;
			}
		}
		
		
		if (isSuccess) {
			RefundUtil.removeVerifyCode(recipientMobile);
			String transferId = productService.generationProductId("TRF");
			int transferNotCashFlag = -1;
			int transferCashFlag = -1;
			if (notCashD > 0) {
				transferNotCashFlag = memberAccountService.transfer(fromUid, toUid, MemberAccountDao.ACCOUNT_TYPE_NOT_CASH + "", notCashD + "", transferId + remark);
			}
			if (cashD > 0) {
				transferCashFlag = memberAccountService.transfer(fromUid, toUid, MemberAccountDao.ACCOUNT_TYPE_CASH + "", cashD + "", transferId + remark);
			}
			Map<String, String> map = BackUserLogUtil.findUserInfo(request);
			backUserLogService.addLog(Integer.parseInt(map.get(BackUserLogUtil.USER_ID)), map.get(BackUserLogUtil.USER_IP), BackUserLogService.ARTIFICIAL_TRANSFER, "管理员：" + map.get(BackUserLogUtil.USER_NAME) + " 对UID为[" + fromUid
					+ "]的用户进行支付操作,非现金金额: " + notCashD + "、现金金额: " + cashD);
			StringBuffer msg = new StringBuffer();
			if (transferNotCashFlag == 0) {
				msg.append("非现金转账成功,转账金额：" + notCashD);
			} else {
				msg.append("非现金转账失败");
			}
			msg.append(";");
			if (transferCashFlag == 0) {
				msg.append("现金转账成功,转账金额：" + cashD);
			} else {
				msg.append("现金转账失败");
			}
			return new MessageBean(true, msg.toString());

		} else {
			return new MessageBean(false, "你的校验码有误");
		}
	}
}
