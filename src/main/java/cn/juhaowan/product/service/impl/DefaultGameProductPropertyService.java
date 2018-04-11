package cn.juhaowan.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.juhaowan.product.dao.GameProductPropertyDao;
import cn.juhaowan.product.dao.GameProductTypeDao;
import cn.juhaowan.product.dao.GameProductTypeReminderDao;
import cn.juhaowan.product.service.GameProductPropertyService;
import cn.juhaowan.product.vo.GameProductProperty;
import cn.juhaowan.product.vo.GameProductType;
import cn.juhaowan.product.vo.GameProductTypeReminder;

/**
 * 
 * @author Administrator
 * 
 */
@Service("GameProductPropertyService")
public class DefaultGameProductPropertyService implements GameProductPropertyService {

	@Autowired
	private JdbcOperations jdbcOperations;

	@Autowired
	private GameProductPropertyDao gameProductPropertyDao;

	@Autowired
	private GameProductTypeReminderDao gameProductTypeReminderDao;

	@Autowired
	private GameProductTypeDao gameProductTypeDao;

	@Override
	public int addProperty(GameProductProperty property) {
		int i = gameProductPropertyDao.addProperty(property);
		return i;
	}

	@Override
	public int modifyProperty(GameProductProperty property) {
		int i = gameProductPropertyDao.modifyProperty(property);
		return i;
	}

	@Override
	public int delProperty(int propertyId) {
		int i = gameProductPropertyDao.delProperty(propertyId);
		return i;
	}

	@Override
	public List<GameProductProperty> queryProperty(int gameId, int gameProductTypeId, int propertyType) {
		List<GameProductProperty> list = gameProductPropertyDao.queryProperty(gameId, gameProductTypeId, propertyType);
		return list;
	}

	@Override
	public GameProductProperty queryPropertyById(int id) {
		return gameProductPropertyDao.queryPropertyById(id);
	}

	@Override
	public int copyProperty(int sourceGameId, int tagerGameId, int gameProductTypeId) {
		// 1 复制game_product_type 表
		// 源数据
		GameProductType gpt = gameProductTypeDao.queryTypeByGameIdAndTypeId(sourceGameId, gameProductTypeId);
		if (null == gpt) {
			return 1;
		}
		// 目标数据
		GameProductType gpt2 = gameProductTypeDao.queryTypeByGameIdAndTypeId(tagerGameId, gameProductTypeId);
		if (null == gpt2) {
			gpt.setGameId(tagerGameId);
			gameProductTypeDao.insert(gpt);
		} else {
			gpt.setId(gpt2.getId());
			gpt.setGameId(tagerGameId);
			gameProductTypeDao.update(gpt);
		}

		// 2 复制自定义属性表
		List<GameProductProperty> gppList = gameProductPropertyDao.queryProperty(sourceGameId, gameProductTypeId);
		if (null != gppList) {
			// 删除目标属性表
			gameProductPropertyDao.delProperty(tagerGameId, gameProductTypeId);
			for (int i = 0; i < gppList.size(); i++) {
				GameProductProperty gpp = gameProductPropertyDao.queryPropertyById(gppList.get(i).getId());
				if (null == gpp) {
					continue;
				}
				gpp.setGameId(tagerGameId);
				gameProductPropertyDao.addProperty(gpp);
			}
		}

		// 3 复制自定义信息
		List<GameProductTypeReminder> gptrList = gameProductTypeReminderDao.queryReminder(sourceGameId, gameProductTypeId);
		if (null != gptrList) {
			// 删除目标自定义表
			gameProductTypeReminderDao.delReminder(tagerGameId, gameProductTypeId);
			for (int i = 0; i < gptrList.size(); i++) {
				GameProductTypeReminder gptr = gameProductTypeReminderDao.queryReminderById(gptrList.get(i).getId());
				if (null == gptr) {
					continue;
				}
				gptr.setGameId(tagerGameId);
				gameProductTypeReminderDao.addReminder(gptr);
			}
		}

		return 0;
	}

	@Override
	public GameProductType queryProductTypeByGameIdAndgameProductTypeId(int gameId, int gameProductTypeId) {
		GameProductType gpt = null;
		gpt = gameProductTypeDao.queryTypeByGameIdAndTypeId(gameId, gameProductTypeId);
		return gpt;
	}

}
