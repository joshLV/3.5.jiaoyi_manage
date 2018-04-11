package cn.juhaowan.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import cn.juhaowan.product.dao.GameProductTypeReminderDao;
import cn.juhaowan.product.service.GameProductTypeReminderService;
import cn.juhaowan.product.vo.GameProductTypeReminder;

/**
 * 
 * @author Administrator
 * 
 */
@Service("GameProductTypeReminderService")
public class DefaultGameProductTypeReminderService implements GameProductTypeReminderService {


	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Autowired
	private GameProductTypeReminderDao gameProductTypeReminderDao;

	@Override
	public int addReminder(GameProductTypeReminder reminder) {
		return gameProductTypeReminderDao.addReminder(reminder);
	}

	@Override
	public int modifyReminder(GameProductTypeReminder reminder) {
		return gameProductTypeReminderDao.modifyReminder(reminder);
	}

	@Override
	public int delReminder(int reminderId) {
		return gameProductTypeReminderDao.delReminder(reminderId);
	}

	@Override
	public GameProductTypeReminder queryReminderById(int id) {
		return gameProductTypeReminderDao.queryReminderById(id);
	}

	@Override
	public List<GameProductTypeReminder> queryReminder(int gameId, int gameProductTypeId) {
		return gameProductTypeReminderDao.queryReminder(gameId, gameProductTypeId);
	}

	@Override
	public List<GameProductTypeReminder> queryReminder(int gameId, int gameProductTypeId, int reminderType) {
		return gameProductTypeReminderDao.queryReminder(gameId, gameProductTypeId, reminderType);
	}

	@Override
	public boolean isExist(int gameId, int gameProductTypeId, String reminderType) {
		// TODO Auto-generated method stub
		return gameProductTypeReminderDao.isExist(gameId, gameProductTypeId, reminderType);
	}
	
	

	
	
	
}
