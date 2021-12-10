package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.AuthDao;
import ptithcm.dao.MenuDao;
import ptithcm.entity.Auth;
import ptithcm.entity.Menu;
import ptithcm.entity.Paging;
import ptithcm.entity.Role;

@Service
public class MenuService {
	private final static Logger log = Logger.getLogger(MenuService.class);

	@Autowired
	private MenuDao<Menu> menuDao;

	@Autowired
	private AuthDao<Auth> authDao;

	public List<Menu> getAllMenus(Menu menu, Paging paging) {
		log.info("Get all menus");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (menu != null) {
			if (!StringUtils.isEmpty(menu.getUrl())) {
				queryStr.append(" AND entity.url LIKE :url");
				mapParams.put("url", "%" + menu.getUrl() + "%");
			}
			queryStr.append(" OR entity.active = 0");
		}
		return menuDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public void changeStatus(Integer id) throws Exception {
		log.info("Change status");
		Menu menu = menuDao.getById(Menu.class, id);
		if (menu != null) {
			menu.setActive(menu.getActive() == 1 ? 0 : 1);
			menuDao.update(menu);
			return;
		}
	}

	public void updatePermission(int roleId, int menuId, int permission) throws Exception {
		log.info("Update permission");
		Auth auth = authDao.find(roleId, menuId);
		if (auth != null) {
			auth.setPermission(permission);
			authDao.update(auth);
		} else {
			if (permission == 1) {
				auth = new Auth();
				auth.setActive(1);

				Role role = new Role();
				role.setId(roleId);

				Menu menu = new Menu();
				menu.setId(menuId);

				auth.setRoles(role);
				auth.setMenu(menu);
				auth.setPermission(1);
				auth.setCreated(new Date());
				auth.setUpdated(new Date());
				authDao.save(auth);
			}
		}
	}
}
