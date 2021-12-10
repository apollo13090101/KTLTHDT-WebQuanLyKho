package ptithcm.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.History;

@Repository
@Transactional(rollbackFor = Exception.class)
public class HistoryDaoImpl extends BaseDaoImpl<History> implements HistoryDao<History> {

}
