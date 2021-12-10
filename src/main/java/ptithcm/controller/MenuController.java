package ptithcm.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.Auth;
import ptithcm.entity.AuthForm;
import ptithcm.entity.Menu;
import ptithcm.entity.Paging;
import ptithcm.entity.Role;
import ptithcm.service.MenuService;
import ptithcm.service.RoleService;
import ptithcm.util.ConstantUtil;

@Controller
public class MenuController {
	private static final Logger log = Logger.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = { "/menu/list", "/menu/list/" })
	public String redirect() {
		return "redirect:/menu/list/1";
	}

	// ==================== View all Menu ====================

	@RequestMapping(value = "/menu/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Menu menu,
			@PathVariable("page") int page) {
		Paging paging = new Paging(20);
		paging.setIndexPage(page);

		List<Menu> menuList = menuService.getAllMenus(menu, paging);
		List<Role> roles = roleService.getAllRoles(null, null);

		Collections.sort(roles, (o1, o2) -> o1.getId() - o2.getId());

		for (Menu item : menuList) {
			Map<Integer, Integer> mapAuth = new TreeMap<>();
			for (Role role : roles) {
				mapAuth.put(role.getId(), 0); // 1-0, 2-0, 3-0
			}
			for (Object obj : item.getAuths()) {
				Auth auth = (Auth) obj;
				mapAuth.put(auth.getRoles().getId(), auth.getPermission());
			}
			item.setMapAuth(mapAuth);
		}

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("menuList", menuList);
		model.addAttribute("roles", roles);

		return "menu-list";
	}

	@GetMapping("/menu/change-status/{id}")
	public String change(Model model, @PathVariable("id") int id, HttpSession session) {
		try {
			menuService.changeStatus(id);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully changed!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to change status.");
		}
		return "redirect:/menu/list";
	}

	@GetMapping("/menu/permission")
	public String permission(Model model) {
		model.addAttribute("modelForm", new AuthForm());
		initSelectbox(model);
		return "menu-permission";
	}

	@PostMapping("/menu/update-permission")
	public String updatePermission(Model model, HttpSession session, @ModelAttribute("modelForm") AuthForm authForm) {
		try {
			menuService.updatePermission(authForm.getRoleId(), authForm.getMenuId(), authForm.getPermission());
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
		}
		return "redirect:/menu/list";
	}

	private void initSelectbox(Model model) {
		List<Role> roles = roleService.getAllRoles(null, null);
		List<Menu> menus = menuService.getAllMenus(null, null);

		Map<Integer, String> mapRole = new HashMap<>();
		Map<Integer, String> mapMenu = new HashMap<>();

		for (Role role : roles) {
			mapRole.put(role.getId(), role.getRoleName());
		}

		for (Menu menu : menus) {
			mapMenu.put(menu.getId(), menu.getUrl());
		}

		model.addAttribute("mapRole", mapRole);
		model.addAttribute("mapMenu", mapMenu);
	}
}
