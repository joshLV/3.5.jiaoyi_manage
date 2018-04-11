package cn.juhaowan.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.juhaowan.member.service.WXUserBindService;
import cn.juhaowan.member.dao.*;

/**
 * 微信用户绑定实现
 * **/
@Service("WXUserBindService")
public class WXUserBindServiceImpl implements WXUserBindService {
	
	@Autowired
	private WXUserBindDao daoBind;
	
	@Override
	public boolean isWXOpenIdBinded(String wxOpenId) {
		return daoBind.isWXOpenIdBinded(wxOpenId);
	}

	@Override
	public int bind(String wxOpenId, int uid) {
		return daoBind.newWXBind(wxOpenId, uid);
	}

	@Override
	public int uidWithWXOpenId(String wxOpenId) {
		return daoBind.uidWithWXOpenId(wxOpenId);
	}

	@Override
	public boolean isUidBinded(int uid) {
		return daoBind.isUidBinded(uid);
	}

	@Override
	public String wxOpenIdWithUid(int uid) {
		return daoBind.wxOpenIdWithUid(uid);
	}

}
