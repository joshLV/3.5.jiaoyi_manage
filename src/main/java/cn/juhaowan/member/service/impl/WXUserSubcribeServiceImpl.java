package cn.juhaowan.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.juhaowan.member.dao.WXUserSubcribeDao;
import cn.juhaowan.member.service.WXUserSubcribeService;

/**
 * 微信用户订阅实现
 * **/
@Service("WXUserSubcribeService")
public class WXUserSubcribeServiceImpl implements WXUserSubcribeService {
	
	@Autowired
	private WXUserSubcribeDao daoSubcribe;
	
	@Override
	public int subcribe(String wxOpenId, String wxNickname,
			String wxHeadImgUrl) {
		return daoSubcribe.newWXSubcribe(wxOpenId, wxNickname, wxHeadImgUrl);
	}

	@Override
	public String wxNickname(String wxOpenId) {
		return daoSubcribe.wxNickname(wxOpenId);
	}
}
