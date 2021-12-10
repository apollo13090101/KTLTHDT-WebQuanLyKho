package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.CategoryDao;
import ptithcm.entity.Category;
import ptithcm.entity.Paging;

@Service
public class CategoryService {
	private final static Logger log = Logger.getLogger(CategoryService.class);

	@Autowired
	private CategoryDao<Category> categoryDao;

	public List<Category> getAllCategories(Category category, Paging paging) {
		log.info("Get all categories");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (category != null) {
			if (category.getCode() != null && !StringUtils.isEmpty(category.getCode())) {
				queryStr.append(" and entity.code = :code");
				mapParams.put("code", category.getCode());
			}
			if (category.getCode() != null && !StringUtils.isEmpty(category.getName())) {
				queryStr.append(" and entity.name like :name");
				mapParams.put("name", "%" + category.getName() + "%");
			}
		}
		return categoryDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public List<Category> getCategoryByProperty(String property, Object value) {
		log.info("Get category by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return categoryDao.getByProperty(property, value);
	}

	public Category getCategoryByCode(String code) {
		log.info("Get category by code = " + code);
		return categoryDao.getByCode(Category.class, code);
	}

	public void saveCategory(Category category) throws Exception {
		log.info("Save category: " + category.toString());
		category.setActive(1);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		categoryDao.save(category);
	}

	public void updateCategory(Category category) throws Exception {
		log.info("Update category: " + category.toString());
		category.setUpdated(new Date());
		categoryDao.update(category);
	}

	public void deleteCategory(Category category) throws Exception {
		log.info("Delete category: " + category.toString());
		category.setActive(0);
		category.setUpdated(new Date());
		categoryDao.update(category);
	}
}
