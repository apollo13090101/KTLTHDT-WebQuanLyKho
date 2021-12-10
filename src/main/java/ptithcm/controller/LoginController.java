package ptithcm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ptithcm.entity.Auth;
import ptithcm.entity.Menu;
import ptithcm.entity.Role;
import ptithcm.entity.User;
import ptithcm.entity.UserRole;
import ptithcm.service.UserService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.LoginValidator;

@Controller
public class LoginController {
	@Autowired
	private LoginValidator loginValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == User.class) {
			binder.setValidator(loginValidator);
		}
	}

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login(ModelMap model) {
		model.addAttribute("loginForm", new User());
		return "login/login";
	}

	@PostMapping("/processLogin")
	public String processLogin(Model model, @ModelAttribute("loginForm") @Validated User users, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			return "login/login";
		}

		User user = userService.getUserByProperty("username", users.getUsername()).get(0);
		UserRole userRole = user.getUserRoles().iterator().next();
		List<Menu> menuList = new ArrayList<>();
		Role role = userRole.getRole();
		List<Menu> menuChildList = new ArrayList<>();

		for (Object obj : role.getAuths()) {
			Auth auth = (Auth) obj;
			Menu menu = auth.getMenu();
			if (menu.getParentId() == 0 && menu.getOrderIndex() != -1 && menu.getActive() == 1
					&& auth.getPermission() == 1 && auth.getActive() == 1) {
				menu.setMenuId(menu.getUrl().replace("/", "") + "Id");
				menuList.add(menu);
			} else if (menu.getParentId() != 0 && menu.getOrderIndex() != -1 && menu.getActive() == 1
					&& auth.getPermission() == 1 && auth.getActive() == 1) {
				menu.setMenuId(menu.getUrl().replace("/", "") + "Id");
				menuChildList.add(menu);
			}
		}

		for (Menu menu : menuList) {
			List<Menu> childList = new ArrayList<>();
			for (Menu childMenu : menuChildList) {
				if (childMenu.getParentId() == menu.getId()) {
					childList.add(childMenu);
				}
			}
			menu.setChild(childList);
		}

		sortMenu(menuList);
		for (Menu menu : menuList) {
			sortMenu(menu.getChild());
		}

		session.setAttribute(ConstantUtil.MENU_SESSION, menuList);
		session.setAttribute(ConstantUtil.USER_INFO, user);
		return "redirect:/index";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(ConstantUtil.MENU_SESSION);
		session.removeAttribute(ConstantUtil.USER_INFO);
		return "redirect:/login";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "access-denied";
	}

	private void sortMenu(List<Menu> menus) {
		Collections.sort(menus, new Comparator<Menu>() {
			@Override
			public int compare(Menu o1, Menu o2) {
				return o1.getOrderIndex() - o2.getOrderIndex();
			}
		});
	}
}
