package ptithcm.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.Menu;

@Repository
@Transactional(rollbackFor = Exception.class)
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao<Menu> {

}
