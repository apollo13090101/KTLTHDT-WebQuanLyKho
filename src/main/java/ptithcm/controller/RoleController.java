package ptithcm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import ptithcm.entity.Role;
import ptithcm.entity.Paging;
import ptithcm.service.RoleService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.RoleValidator;

@Controller
public class RoleController {
	private static final Logger log = Logger.getLogger(RoleController.class);

	@Autowired
	private RoleValidator roleValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == Role.class) {
			binder.setValidator(roleValidator);
		}
	}

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = { "/role/list", "/role/list/" })
	public String redirect() {
		return "redirect:/role/list/1";
	}

	// ==================== View all Roles ====================

	@RequestMapping(value = "/role/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Role role,
			@PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);

		List<Role> roles = roleService.getAllRoles(role, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("roles", roles);

		return "role-list";
	}

	// ==================== View Role ====================

	@GetMapping("/role/view/{id}")
	public String view(ModelMap model, @PathVariable("id") int id) {
		log.info("View role with id = " + id);
		Role role = roleService.getRoleById(id);
		if (role != null) {
			model.addAttribute("titlePage", "View Role");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", true);
			return "role-view";
		}
		return "redirect:/role/list";
	}

	// ==================== Add Role ====================

	@GetMapping("/role/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Role");
		model.addAttribute("modelForm", new Role());
		model.addAttribute("viewOnly", false);
		return "role-add";
	}

	@PostMapping("/role/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated Role role, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Role");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", false);
			return "role-add";
		}

		try {
			roleService.saveRole(role);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/role/list";
	}

	// ==================== Edit Role ====================

	@GetMapping("/role/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") int id) {
		log.info("Edit role with id = " + id);
		Role role = roleService.getRoleById(id);
		if (role != null) {
			model.addAttribute("titlePage", "Edit Role");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", false);
			return "role-edit";
		}
		return "redirect:/role/list";
	}

	@PostMapping("/role/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated Role role, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Role");
			model.addAttribute("modelForm", role);
			model.addAttribute("viewOnly", false);
			return "role-edit";
		}

		if (role.getId() != null && role.getId() != 0) {
			try {
				roleService.updateRole(role);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/role/list";
	}

	// ==================== Delete Role ====================

	@GetMapping("/role/delete/{id}")
	public String delete(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete role with id = " + id);
		Role role = roleService.getRoleByProperty("id", id).get(0);
		if (role != null) {
			try {
				roleService.deleteRole(role);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/role/list";
	}
}
