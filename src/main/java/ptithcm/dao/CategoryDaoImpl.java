package ptithcm.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.Category;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao<Category> {

}
