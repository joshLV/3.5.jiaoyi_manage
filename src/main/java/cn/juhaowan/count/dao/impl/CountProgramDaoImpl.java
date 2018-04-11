package cn.juhaowan.count.dao.impl;


import org.springframework.stereotype.Repository;

import cn.jugame.dal.dao.impl.BaseDaoImplJdbc;
import cn.juhaowan.count.dao.CountProgramDao;
import cn.juhaowan.count.vo.CountProgram;

@Repository("CountProgramDao")
public class CountProgramDaoImpl extends BaseDaoImplJdbc<CountProgram> implements
		CountProgramDao {


}
