package cn.juhaowan.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jugame.util.Config;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectRequest;

public class AliyunImageUtils {
	private static String endpoint;

	private static String accessKeyId;

	private static String accessKeySecret;

	private static String bucket;

	private static String imgEndpoint;

	private static OSSClient oss = null;

	private static Logger logger = LoggerFactory.getLogger(AliyunImageUtils.class);

	public static void init() {
		endpoint = Config.getValue("endpoint");
		accessKeyId = Config.getValue("accessKeyId");
		accessKeySecret = Config.getValue("accessKeySecret");
		bucket = Config.getValue("bucket");
		imgEndpoint = Config.getValue("imgEndpoint");
		// oss = new OSSClient(endpoint,accessKeyId,accessKeySecret);
		CredentialsProvider cprodiver = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
		oss = new OSSClient(endpoint, cprodiver);
	}

	/**
	 * @param key，格式为： orderid或者productid + '/' + 时间戳精确到毫秒 + '.jpg'
	 * @param data
	 * @return
	 */
	public static boolean uploadFile(String key, byte[] data) {
		if (oss == null) {
			logger.error("oss is null, uploadFile failed");
			return false;
		}

		PutObjectRequest putReq = new PutObjectRequest(bucket, key,new ByteArrayInputStream(data));
		try {
			oss.putObject(putReq);
			logger.info("key: " + key + ", upload ok");
			return true;
		} catch (ClientException e) {
			logger.error("uploadFile error", e);
		} catch (ServiceException e) {
			logger.error("uploadFile error", e);
		}

		return false;
	}

	/**
	 * @param key
	 *            , 上传时使用的KEY
	 * @param sizeRate
	 *            30-100。 100是原图，图片大小缩放比例
	 * @param qualityRate
	 *            10-100, jpeg图片质量
	 * @return
	 */
	public static String getUploadedImgUrl(String key, int sizeRate,int qualityRate) {
		return "https://" +  imgEndpoint + "/" + key+ "?x-oss-process=style/watermark.jpg";
	}

	public static String getUploadedImgUrl(String key) {
		return getUploadedImgUrl(key, 100, 70);
	}

	/**
	 * 把一个文件转化为字节
	 * 
	 * @param file
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] getByte(File file) throws Exception {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = new FileInputStream(file);
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) // 当文件的长度超过了int的最大值
			{
				is.close();
				return null;
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// 如果得到的字节长度和file实际的长度不一致就可能出错了
			if (offset < bytes.length) {
				is.close();
				return null;
			}
			is.close();
		}
		return bytes;
	}

	/**
	 * 获取没有水印的图片地址
	 * 
	 * @param picUrl
	 * @return
	 */
	public static String parseOrginPic(String picUrl) {
		if (StringUtil.isEmpty(picUrl)) {
			return "";
		}
		String result = "";
		if (picUrl.contains("style/watermark.jpg")) {
			result = picUrl.replace("/watermark.jpg", "/origin");
			// result = picUrl.replace("style/watermark.jpg",
			// "image/resize,p_55");
		} else {
			result = picUrl;
		}
		return result;
	}

	/**
	 * 上传图片
	 * 
	 * @param imgFile
	 * @param busiId
	 * @return
	 */
	public static String uploadAliyun(File imgFile, String busiId) {
		try {
			byte[] b = null;
			b = AliyunImageUtils.getByte(imgFile);
			AliyunImageUtils.init();
			long currentTime = System.currentTimeMillis();
			String key = busiId + "/" + currentTime + ".jpg";
			boolean flag = AliyunImageUtils.uploadFile(key, b);
			if (flag) {
				String imgUrl = AliyunImageUtils.getUploadedImgUrl(key);
				return parseOrginPic(imgUrl);
			}
		} catch (Exception e) {
			logger.error("上传图片到阿里云失败，busiId:" + busiId);
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		// http://zeus8868.img-cn-shenzhen.aliyuncs.com/ORD-170522-11090874633/1495423231754.img?x-oss-process=style/origin
		File f = new File("d:\\3333.jpg");

		String str = uploadAliyun(f, "banner");
		System.out.println(str);

		// byte [] b = null;
		// try {
		// b = getByte(f);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println(f);
		// AliyunImageUtils.init();
		// long currentTime=System.currentTimeMillis();
		// String key = "ORD-test/"+currentTime+".jpg";
		// boolean flag = AliyunImageUtils.uploadFile(key, b);
		// System.out.println(flag);
		// System.out.println(AliyunImageUtils.getUploadedImgUrl(key));
		// // System.out.println(AliyunImageUtils.getUploadedImgUrl(key, 100,
		// 100));
		// System.out.println(parseOrginPic("http://zeus8868.img-cn-shenzhen.aliyuncs.com/ORD-170615-17580566251/1497521280357.jpg?x-oss-process=style/watermark.jpg"));

	}

}
