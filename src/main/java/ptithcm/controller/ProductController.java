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

import ptithcm.entity.Product;
import ptithcm.entity.Category;
import ptithcm.entity.Paging;
import ptithcm.service.CategoryService;
import ptithcm.service.ProductService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.ProductValidator;

@Controller
public class ProductController {
	private static final Logger log = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductValidator productValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == Product.class) {
			binder.setValidator(productValidator);
		}
	}

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/product/list", "/product/list/" })
	public String redirect() {
		return "redirect:/product/list/1";
	}

	// ==================== View all Products ====================

	@RequestMapping(value = "/product/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Product product,
			@PathVariable("page") int page) {
		Paging paging = new Paging(20);
		paging.setIndexPage(page);

		List<Product> products = productService.getAllProducts(product, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("products", products);

		return "product-list";
	}

	// ==================== View product ====================

	@GetMapping("/product/view/{code}")
	public String view(ModelMap model, @PathVariable("code") String code) {
		log.info("View product with code = " + code);
		Product product = productService.getProductByCode(code);
		if (product != null) {
			model.addAttribute("titlePage", "View Product");
			model.addAttribute("modelForm", product);
			model.addAttribute("viewOnly", true);
			return "product-view";
		}
		return "redirect:/product/list";
	}

	// ==================== Add product ====================

	@GetMapping("/product/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Product");
		model.addAttribute("modelForm", new Product());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);

		List<Category> categories = categoryService.getAllCategories(null, null);
		Map<String, String> mapCategory = new HashMap<>();
		for (Category category : categories) {
			mapCategory.put(String.valueOf(category.getCode()), category.getName());
		}
		model.addAttribute("mapCategory", mapCategory);

		return "product-add";
	}

	@PostMapping("/product/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated Product product, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Product");
			model.addAttribute("modelForm", product);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);

			List<Category> categories = categoryService.getAllCategories(null, null);
			Map<String, String> mapCategory = new HashMap<>();
			for (Category category : categories) {
				mapCategory.put(String.valueOf(category.getCode()), category.getName());
			}
			model.addAttribute("mapCategory", mapCategory);

			return "product-add";
		}

		Category category = new Category();
		category.setCode(product.getCategoryCode());
		product.setCategory(category);

		try {
			productService.saveProduct(product);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/product/list";
	}

	// ==================== Edit product ====================

	@GetMapping("/product/edit/{code}")
	public String edit(ModelMap model, @PathVariable("code") String code) {
		log.info("Edit product with code = " + code);
		Product product = productService.getProductByCode(code);
		if (product != null) {
			model.addAttribute("titlePage", "Edit Product");
			model.addAttribute("modelForm", product);
			model.addAttribute("viewOnly", false);

			List<Category> categories = categoryService.getAllCategories(null, null);
			Map<String, String> mapCategory = new HashMap<>();
			for (Category category : categories) {
				mapCategory.put(String.valueOf(category.getCode()), category.getName());
			}
			model.addAttribute("mapCategory", mapCategory);


			return "product-edit";
		}
		return "redirect:/product/list";
	}

	@PostMapping("/product/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated Product product, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Product");
			model.addAttribute("modelForm", product);
			model.addAttribute("viewOnly", false);

			List<Category> categories = categoryService.getAllCategories(null, null);
			Map<String, String> mapCategory = new HashMap<>();
			for (Category category : categories) {
				mapCategory.put(String.valueOf(category.getCode()), category.getName());
			}
			model.addAttribute("mapCategory", mapCategory);

			return "product-edit";
		}

		Category category = new Category();
		category.setCode(product.getCategoryCode());
		product.setCategory(category);

		if (product.getCode() != null && !product.getCode().isEmpty()) {
			try {
				productService.updateProduct(product);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/product/list";
	}

	// ==================== Delete product ====================

	@GetMapping("/product/delete/{code}")
	public String delete(ModelMap model, @PathVariable("code") String code, HttpSession session) {
		log.info("Delete product with code = " + code);
		Product product = productService.getProductByProperty("code", code).get(0);
		if (product != null) {
			try {
				productService.deleteProduct(product);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/product/list";
	}
}
