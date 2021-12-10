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

import ptithcm.entity.Category;
import ptithcm.entity.Paging;
import ptithcm.service.CategoryService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.CategoryValidator;

@Controller
public class CategoryController {
	private static final Logger log = Logger.getLogger(CategoryController.class);

	@Autowired
	private CategoryValidator categoryValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == Category.class) {
			binder.setValidator(categoryValidator);
		}
	}

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = { "/category/list", "/category/list/" })
	public String redirect() {
		return "redirect:/category/list/1";
	}

	// ==================== View all Categories ====================

	@RequestMapping(value = "/category/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Category category,
			@PathVariable("page") int page) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);

		List<Category> categories = categoryService.getAllCategories(category, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("categories", categories);

		return "category-list";
	}

	// ==================== View Category ====================

	@GetMapping("/category/view/{code}")
	public String view(ModelMap model, @PathVariable("code") String code) {
		log.info("View category with code = " + code);
		Category category = categoryService.getCategoryByCode(code);
		if (category != null) {
			model.addAttribute("titlePage", "View Category");
			model.addAttribute("modelForm", category);
			model.addAttribute("viewOnly", true);
			return "category-view";
		}
		return "redirect:/category/list";
	}

	// ==================== Add Category ====================

	@GetMapping("/category/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Category");
		model.addAttribute("modelForm", new Category());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);
		return "category-add";
	}

	@PostMapping("/category/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated Category category, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Category");
			model.addAttribute("modelForm", category);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);
			return "category-add";
		}

		try {
			categoryService.saveCategory(category);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/category/list";
	}

	// ==================== Edit Category ====================

	@GetMapping("/category/edit/{code}")
	public String edit(ModelMap model, @PathVariable("code") String code) {
		log.info("Edit category with code = " + code);
		Category category = categoryService.getCategoryByCode(code);
		if (category != null) {
			model.addAttribute("titlePage", "Edit Category");
			model.addAttribute("modelForm", category);
			model.addAttribute("viewOnly", false);
			return "category-edit";
		}
		return "redirect:/category/list";
	}

	@PostMapping("/category/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated Category category, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Category");
			model.addAttribute("modelForm", category);
			model.addAttribute("viewOnly", false);
			return "category-edit";
		}

		if (category.getCode() != null && !category.getCode().isEmpty()) {
			try {
				categoryService.updateCategory(category);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/category/list";
	}

	// ==================== Delete Category ====================

	@GetMapping("/category/delete/{code}")
	public String delete(ModelMap model, @PathVariable("code") String code, HttpSession session) {
		log.info("Delete category with code = " + code);
		Category category = categoryService.getCategoryByProperty("code", code).get(0);
		if (category != null) {
			try {
				categoryService.deleteCategory(category);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/category/list";
	}
}
