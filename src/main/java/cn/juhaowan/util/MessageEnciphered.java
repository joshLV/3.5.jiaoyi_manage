package cn.juhaowan.util;

import cn.jugame.util.MD5;
/**
 * MD5加密
 * **/
public class MessageEnciphered {

	public static String MD5_CODE = "admin";
	
	
	public static String loginPassEnciphered(String loginPass){
		return MD5.encode(MD5.encode(MD5_CODE + loginPass));	//两次md5
	}
	
	public static void main(String[] args) {
		System.out.println(MessageEnciphered.loginPassEnciphered("caibin"));
		System.out.println(MessageEnciphered.loginPassEnciphered("xiaoqiang"));
	}
}
