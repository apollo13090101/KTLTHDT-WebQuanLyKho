package ptithcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ptithcm.service.ProductService;
import ptithcm.service.UserService;

@Controller
public class IndexController {
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@GetMapping("/index")
	public String index(ModelMap model) {

		int numUsers = userService.getNumberOfUsers();
		int numProducts = productService.getNumberOfProducts();
		model.addAttribute("numUsers", numUsers);
		model.addAttribute("numProducts", numProducts);
		return "home";
	}
}
