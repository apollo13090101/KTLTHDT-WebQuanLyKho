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

import ptithcm.entity.Issue;
import ptithcm.entity.IssueDetail;
import ptithcm.entity.Paging;
import ptithcm.entity.Product;
import ptithcm.service.IssueDetailService;
import ptithcm.service.IssueService;
import ptithcm.service.ProductService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.IssueDetailValidator;

@Controller
public class IssueDetailController {
	private static final Logger log = Logger.getLogger(IssueDetailController.class);

	@Autowired
	private IssueDetailValidator issueDetailValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == IssueDetail.class) {
			binder.setValidator(issueDetailValidator);
		}
	}

	@Autowired
	private IssueService issueService;

	@Autowired
	private ProductService productService;

	@Autowired
	private IssueDetailService issueDetailService;

	@RequestMapping(value = { "/issue-detail/list", "/issue-detail/list/" })
	public String redirect() {
		return "redirect:/issue-detail/list/1";
	}

	// ==================== View all IssueDetails ====================

	@RequestMapping(value = "/issue-detail/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") IssueDetail issueDetail,
			@PathVariable("page") int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);

		List<IssueDetail> issueDetails = issueDetailService.getAllIssueDetails(issueDetail, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("issueDetails", issueDetails);

		return "issue-detail-list";
	}

	// ==================== View IssueDetail ====================

	@GetMapping("/issue-detail/view/{id}")
	public String view(ModelMap model, @PathVariable("id") int id) {
		log.info("View issue detail with id = " + id);
		IssueDetail issueDetail = issueDetailService.getIssueDetailById(id);
		if (issueDetail != null) {
			model.addAttribute("titlePage", "View Issue Detail");
			model.addAttribute("modelForm", issueDetail);
			model.addAttribute("viewOnly", true);
			return "issue-detail-view";
		}
		return "redirect:/issue-detail/list";
	}

	// ==================== Add IssueDetail ====================

	@GetMapping("/issue-detail/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Issue Detail");
		model.addAttribute("modelForm", new IssueDetail());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);
		model.addAttribute("isCurrentUser", true);

		List<Issue> issues = issueService.getAllIssues(null, null);
		Map<String, String> mapIssue = new HashMap<>();
		for (Issue issue : issues) {
			mapIssue.put(String.valueOf(issue.getCode()), issue.getCode());
		}
		model.addAttribute("mapIssue", mapIssue);

		List<Product> products = productService.getAllProducts(null, null);
		Map<String, String> mapProduct = new HashMap<>();
		for (Product product : products) {
			mapProduct.put(String.valueOf(product.getCode()), product.getName());
		}
		model.addAttribute("mapProduct", mapProduct);

		return "issue-detail-add";
	}

	@PostMapping("/issue-detail/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated IssueDetail issueDetail,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Issue Detail");
			model.addAttribute("modelForm", issueDetail);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);

			List<Issue> issues = issueService.getAllIssues(null, null);
			Map<String, String> mapIssue = new HashMap<>();
			for (Issue issue : issues) {
				mapIssue.put(String.valueOf(issue.getCode()), issue.getCode());
			}
			model.addAttribute("mapIssue", mapIssue);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);

			return "issue-detail-add";
		}

		Issue issue = new Issue();
		issue.setCode(issueDetail.getIssueCode());
		issueDetail.setIssue(issue);

		Product product = new Product();
		product.setCode(issueDetail.getProductCode());
		issueDetail.setProduct(product);

		try {
			issueDetailService.saveIssueDetail(issueDetail);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/issue-detail/list";
	}

	// ==================== Edit IssueDetail ====================

	@GetMapping("/issue-detail/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") int id) {
		log.info("Edit issue detail with id = " + id);
		IssueDetail issueDetail = issueDetailService.getIssueDetailById(id);
		if (issueDetail != null) {
			model.addAttribute("titlePage", "Edit Issue Detail");
			model.addAttribute("modelForm", issueDetail);
			model.addAttribute("viewOnly", false);
			
			List<Issue> issues = issueService.getAllIssues(null, null);
			Map<String, String> mapIssue = new HashMap<>();
			for (Issue issue : issues) {
				mapIssue.put(String.valueOf(issue.getCode()), issue.getCode());
			}
			model.addAttribute("mapIssue", mapIssue);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);
			
			return "issue-detail-edit";
		}
		return "redirect:/issue-detail/list";
	}

	@PostMapping("/issue-detail/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated IssueDetail issueDetail,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Issue Detail");
			model.addAttribute("modelForm", issueDetail);
			model.addAttribute("viewOnly", false);
			
			List<Issue> issues = issueService.getAllIssues(null, null);
			Map<String, String> mapIssue = new HashMap<>();
			for (Issue issue : issues) {
				mapIssue.put(String.valueOf(issue.getCode()), issue.getCode());
			}
			model.addAttribute("mapIssue", mapIssue);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);
			
			return "issue-detail-edit";
		}
		
		Issue issue = new Issue();
		issue.setCode(issueDetail.getIssueCode());
		issueDetail.setIssue(issue);

		Product product = new Product();
		product.setCode(issueDetail.getProductCode());
		issueDetail.setProduct(product);

		if (issueDetail.getId() != null && issueDetail.getId() != 0) {
			try {
				issueDetailService.updateIssueDetail(issueDetail);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/issue-detail/list";
	}

	// ==================== Delete IssueDetail ====================

	@GetMapping("/issue-detail/delete/{id}")
	public String delete(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete issue detail with id = " + id);
		IssueDetail issueDetail = issueDetailService.getIssueDetailByProperty("id", id).get(0);
		if (issueDetail != null) {
			try {
				issueDetailService.deleteIssueDetail(issueDetail);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/issue-detail/list";
	}
}
