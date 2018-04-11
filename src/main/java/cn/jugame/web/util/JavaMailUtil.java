package cn.jugame.web.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 发邮件工具类
 * @author Administrator
 *
 */
public class JavaMailUtil {
	public static Logger LOGGER = LoggerFactory.getLogger(JavaMailUtil.class);
	// 定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等
	private String displayName;
	private String to;
	private String from;
	private String smtpServer = "smtp.163.com";
	private String username;
	private String password;
	private String subject;
	private String content;
	private boolean ifAuth; // 服务器是否要身份认证
	private String filename = "";
	private Vector<String> file = new Vector<>(); // 用于保存发送附件的文件名的集合

	/**
	 * 设置SMTP服务器地址
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * 设置发件人的地址
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 设置显示的名称
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 设置服务器是否需要身份认证
	 */
	public void setIfAuth(boolean ifAuth) {
		this.ifAuth = ifAuth;
	}

	/**
	 * 设置E-mail用户名
	 */
	public void setUserName(String username) {
		this.username = username;
	}

	/**
	 * 设置E-mail密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 设置接收者
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 设置主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 设置主体内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 该方法用于收集附件名
	 */
	public void addAttachfile(String fname) {
		file.addElement(fname);
	}
	/**
	 * 无参构造方法
	 */
	public JavaMailUtil() {

	}
	/**
	 * 须要身份验证
	 * @param smtpServer 邮件服务器
	 * @param from 发件人
	 * @param displayName 显示名字
	 * @param username 用户名
	 * @param password 密码
	 * @param to 收件人
	 * @param subject 主题
	 * @param content 内容
	 * @param haveFile 是否附带附件 
	 * @param fileName 附件名
	 */
	public JavaMailUtil(String smtpServer, String from, String displayName,
			String username, String password, String to, String subject,
			String content,boolean haveFile,String fileName) {
		this.smtpServer = smtpServer;
		this.from = from;
		this.displayName = displayName;
		this.ifAuth = true;
		this.username = username;
		this.password = password;
		this.to = to;
		this.subject = subject;
		this.content = content;
		if(haveFile){
			this.addAttachfile(fileName);
		}
	}
	/**
	 * 不须验证身份
	 * @param smtpServer 邮件服务器
	 * @param from 发送者
	 * @param displayName 显示名字
	 * @param to 接收者
	 * @param subject 主题
	 * @param content 内容
	 * @param haveFile 是否附带附件 
	 * @param fileName 附件名
	 */
	public JavaMailUtil(String smtpServer, String from, String displayName, String to,
			String subject, String content,boolean haveFile,String fileName) {
		this.smtpServer = smtpServer;
		this.from = from;
		this.displayName = displayName;
		this.ifAuth = false;
		this.to = to;
		this.subject = subject;
		this.content = content;
		if(haveFile){
			this.addAttachfile(fileName);
		}
	}

	/**
	 * 发送邮件
	 */
	public JSONObject send() {
		JSONObject json = new JSONObject();
		json.put("state", "success");
		String message = "邮件发送成功！";
		Session session = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		if (ifAuth) { // 服务器需要身份认证
			props.put("mail.smtp.auth", "true");
			SmtpAuth smtpAuth = new SmtpAuth(username, password);
			session = Session.getDefaultInstance(props, smtpAuth);
		} else {
			props.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(props, null);
		}
		session.setDebug(true);
		Transport trans = null;
		try {
			Message msg = new MimeMessage(session);
			try {
				Address fromAddress = new InternetAddress(from, displayName);
				msg.setFrom(fromAddress);
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			InternetAddress[] address = new InternetAddress().parse(to); 
			
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(MimeUtility.encodeText(subject,"gb2312","B"));
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(content.toString(), "text/html;charset=gb2312");
			mp.addBodyPart(mbp);
			if (!file.isEmpty()) {// 有附件
				Enumeration efile = file.elements();
				while (efile.hasMoreElements()) {
					mbp = new MimeBodyPart();
					filename = efile.nextElement().toString(); // 选择出每一个附件名
					FileDataSource fds = new FileDataSource(filename); // 得到数据源
					mbp.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
					mbp.setFileName(fds.getName()); // 得到文件名同样至入BodyPart
					mp.addBodyPart(mbp);
				}
				file.removeAllElements();
			}
			msg.setContent(mp); // Multipart加入到信件
			msg.setSentDate(new Date()); // 设置信件头的发送日期
			// 发送信件
			msg.saveChanges();
			trans = session.getTransport("smtp");
			trans.connect(smtpServer, username, password);
			trans.sendMessage(msg, msg.getAllRecipients());
			trans.close();

		} catch (AuthenticationFailedException e) {
			json.put("state", "failed");
			message = "邮件发送失败！错误原因：\n" + "身份验证错误!";
			LOGGER.error(message);
			e.printStackTrace();
		} catch (MessagingException e) {
			message = "邮件发送失败！错误原因：\n" + e.getMessage();
			json.put("state", "failed");
			LOGGER.error(message);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			message = "邮件发送失败！错误原因：\n" + e.getMessage();
			json.put("state", "failed");
			LOGGER.error(message);
			e.printStackTrace();
		}
		json.put("message", message);
		return json;
	}
}