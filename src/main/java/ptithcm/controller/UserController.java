package ptithcm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.User;
import ptithcm.entity.UserRole;
import ptithcm.entity.Paging;
import ptithcm.entity.Role;
import ptithcm.service.RoleService;
import ptithcm.service.UserService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.UserValidator;

@Controller
public class UserController {
	private static final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private UserValidator userValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == User.class) {
			binder.setValidator(userValidator);
		}
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/user/list", "/user/list/" })
	public String redirect() {
		return "redirect:/user/list/1";
	}

	// ==================== View all Users ====================

	@RequestMapping(value = "/user/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") User user,
			@PathVariable("page") int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);

		List<User> users = userService.getAllUsers(user, paging);
		
		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("users", users);

		return "user-list";
	}

	// ==================== View User ====================

	@GetMapping("/user/view/{id}")
	public String view(ModelMap model, @PathVariable("id") int id) {
		log.info("View user with id = " + id);
		List<User> results = userService.getUserByProperty("id", id);
		if (results != null && !results.isEmpty()) {
			User user = results.get(0);
			
			model.addAttribute("titlePage", "View User");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", true);

			List<Role> roles = roleService.getAllRoles(null, null);
			Map<String, String> mapRole = new HashMap<>();
			for (Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			model.addAttribute("mapRole", mapRole);

			return "user-view";
		}
		return "redirect:/user/list";
	}

	// ==================== Add User ====================

	@GetMapping("/user/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add User");
		model.addAttribute("modelForm", new User());
		model.addAttribute("viewOnly", false);

		List<Role> roles = roleService.getAllRoles(null, null);
		Map<String, String> mapRole = new HashMap<>();
		for (Role role : roles) {
			mapRole.put(String.valueOf(role.getId()), role.getRoleName());
		}
		model.addAttribute("mapRole", mapRole);

		return "user-add";
	}

	@PostMapping("/user/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated User user, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add User");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", false);

			List<Role> roles = roleService.getAllRoles(null, null);
			Map<String, String> mapRole = new HashMap<>();
			for (Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			model.addAttribute("mapRole", mapRole);

			return "user-add";
		}

		try {
			userService.saveUser(user);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/user/list";
	}

	// ==================== Edit User ====================

	@GetMapping("/user/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") int id) {
		log.info("Edit user with id = " + id);
		List<User> results = userService.getUserByProperty("id", id);
		if (results != null && !results.isEmpty()) {
			User user = results.get(0);
			
			model.addAttribute("titlePage", "Edit User");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editMode", true);

			List<Role> roles = roleService.getAllRoles(null, null);
			Map<String, String> mapRole = new HashMap<>();
			for (Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			model.addAttribute("mapRole", mapRole);

			UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
			user.setRoleId(userRole.getRole().getId());
			
			model.addAttribute("editMode", true);

			return "user-edit";
		}
		return "redirect:/user/list";
	}

	@PostMapping("/user/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated User user, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit User");
			model.addAttribute("modelForm", user);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editMode", true);

			List<Role> roles = roleService.getAllRoles(null, null);
			Map<String, String> mapRole = new HashMap<>();
			for (Role role : roles) {
				mapRole.put(String.valueOf(role.getId()), role.getRoleName());
			}
			model.addAttribute("mapRole", mapRole);

			return "user-edit";
		}

		if (user.getId() != null && user.getId() != 0) {
			try {
				userService.updateUser(user);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/user/list";
	}

	// ==================== Delete User ====================

	@GetMapping("/user/delete/{id}")
	public String delete(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete user with id = " + id);
		List<User> results = userService.getUserByProperty("id", id);
		if (results != null && !results.isEmpty()) {
			User user = results.get(0);
			try {
				userService.deleteUser(user);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/user/list";
	}
}
