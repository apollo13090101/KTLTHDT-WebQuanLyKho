package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptithcm.dao.RoleDao;
import ptithcm.entity.Role;
import ptithcm.entity.Paging;

@Service
public class RoleService {
	private final static Logger log = Logger.getLogger(RoleService.class);

	@Autowired
	private RoleDao<Role> roleDao;

	public List<Role> getAllRoles(Role role, Paging paging) {
		log.info("Get all roles");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		return roleDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public List<Role> getRoleByProperty(String property, Object value) {
		log.info("Get role by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return roleDao.getByProperty(property, value);
	}

	public Role getRoleById(int id) {
		log.info("Get role by id = " + id);
		return roleDao.getById(Role.class, id);
	}

	public void saveRole(Role role) throws Exception {
		log.info("Save role: " + role.toString());
		role.setActive(1);
		role.setCreated(new Date());
		role.setUpdated(new Date());
		roleDao.save(role);
	}

	public void updateRole(Role role) throws Exception {
		log.info("Update role: " + role.toString());
		role.setUpdated(new Date());
		roleDao.update(role);
	}

	public void deleteRole(Role role) throws Exception {
		log.info("Delete role: " + role.toString());
		role.setActive(0);
		role.setUpdated(new Date());
		roleDao.update(role);
	}
}
