package cn.jugame.web.util;

import javax.mail.Authenticator;
/**
 * Smtp帮助类
 * @author houjt
 *
 */
public class SmtpAuth extends Authenticator {
    private String username;
    private String password;   
    /**
     * 带参构造方法
     * @param username
     * @param password
     */
    public SmtpAuth(String username,String password){   
        this.username = username;    
        this.password = password;    
    }   
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {   
        return new javax.mail.PasswordAuthentication(username,password);   
    }  
}
