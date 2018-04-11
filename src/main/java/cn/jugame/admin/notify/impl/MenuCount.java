package cn.jugame.admin.notify.impl;

import org.springframework.stereotype.Service;

import cn.jugame.admin.notify.IMenuCount;

@Service("IMenuCount")
public class MenuCount implements IMenuCount {

	@Override
	public int getCount(int kefuId, int userType) {
		return 0;
	}

}
