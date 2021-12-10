package ptithcm.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.dao.UserDao;
import ptithcm.dao.UserRoleDao;
import ptithcm.entity.Paging;
import ptithcm.entity.Role;
import ptithcm.entity.User;
import ptithcm.entity.UserRole;
import ptithcm.util.HashingPasswordUtil;
import ptithcm.util.UploadUtil;

@Service
public class UserService {

	private final static Logger log = Logger.getLogger(UserService.class);

	@Autowired
	private UserDao<User> userDao;

	@Autowired
	private UserRoleDao<UserRole> userRoleDao;

	public List<User> getAllUsers(User user, Paging paging) {
		log.info("Get all users");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (user != null) {
			if (user.getUsername() != null && !StringUtils.isEmpty(user.getUsername())) {
				queryStr.append(" and entity.username = :username");
				mapParams.put("username", user.getUsername());
			}
			if (user.getLastName() != null && !StringUtils.isEmpty(user.getLastName())) {
				queryStr.append(" and entity.lastName like :lastName");
				mapParams.put("lastName", "%" + user.getLastName() + "%");
			}
			if (user.getFirstName() != null && !StringUtils.isEmpty(user.getFirstName())) {
				queryStr.append(" and entity.firstName like :firstName");
				mapParams.put("firstName", "%" + user.getFirstName() + "%");
			}
		}
		return userDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public int getNumberOfUsers() {
		return userDao.getNumberOfUsers();
	}

	public List<User> getUserByProperty(String property, Object value) {
		log.info("Get user by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return userDao.getByProperty(property, value);
	}

	public User getUserById(int id) {
		log.info("Get user by id = " + id);
		return userDao.getById(User.class, id);
	}

	public void saveUser(User user) {
		log.info("Save user: " + user.toString());
		user.setActive(1);
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(HashingPasswordUtil.encrypt(user.getPassword()));
		userDao.save(user);

		UserRole userRole = new UserRole();
		userRole.setUser(user);
		Role role = new Role();
		role.setId(user.getRoleId());
		userRole.setRole(role);
		userRole.setActive(1);
		userRole.setCreated(new Date());
		userRole.setUpdated(new Date());
		userRoleDao.save(userRole);
	}

	public void updateUser(User users) {
		log.info("Update user: " + users.toString());
		User user = getUserById(users.getId());
		if (user != null) {
			UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
			Role role = userRole.getRole();
			role.setId(users.getRoleId());
			userRole.setRole(role);
			userRole.setUpdated(new Date());
			user.setLastName(users.getLastName());
			user.setFirstName(users.getFirstName());
			user.setUsername(users.getUsername());
			user.setEmail(users.getEmail());
			user.setAddress(users.getAddress());
			user.setBirthday(users.getBirthday());
			user.setSalary(users.getSalary());
			user.setUpdated(new Date());
			userRoleDao.update(userRole);
		}

		userDao.update(user);
	}

	public void deleteUser(User user) {
		log.info("Delete user: " + user.toString());
		user.setActive(0);
		user.setUpdated(new Date());
		userDao.update(user);
	}

	public void processUploadFile(MultipartFile multipartFile, String filename)
			throws IllegalStateException, IOException {
		if (!multipartFile.getOriginalFilename().isEmpty()) {
			File dir = new File(UploadUtil.getInstance().getValue("user.upload.location"));
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(UploadUtil.getInstance().getValue("user.upload.location"), filename);
			multipartFile.transferTo(file);
		}
	}
}
