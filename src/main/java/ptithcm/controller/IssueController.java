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

import ptithcm.entity.Issue;
import ptithcm.entity.Paging;
import ptithcm.entity.User;
import ptithcm.service.IssueService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.IssueValidator;

@Controller
public class IssueController {
	private static final Logger log = Logger.getLogger(IssueController.class);

	@Autowired
	private IssueValidator issueValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == Issue.class) {
			binder.setValidator(issueValidator);
		}
	}

	@Autowired
	private IssueService issueService;

	@RequestMapping(value = { "/issue/list", "/issue/list/" })
	public String redirect() {
		return "redirect:/issue/list/1";
	}

	// ==================== View all Issues ====================

	@RequestMapping(value = "/issue/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Issue issue,
			@PathVariable("page") int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);

		List<Issue> issues = issueService.getAllIssues(issue, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("issues", issues);

		return "issue-list";
	}

	// ==================== View Issue ====================

	@GetMapping("/issue/view/{code}")
	public String view(ModelMap model, @PathVariable("code") String code) {
		log.info("View issue with code = " + code);
		Issue issue = issueService.getIssueByCode(code);
		if (issue != null) {
			model.addAttribute("titlePage", "View Issue");
			model.addAttribute("modelForm", issue);
			model.addAttribute("viewOnly", true);
			return "issue-view";
		}
		return "redirect:/issue/list";
	}

	// ==================== Add Issue ====================

	@GetMapping("/issue/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Issue");
		model.addAttribute("modelForm", new Issue());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);
		model.addAttribute("isCurrentUser", true);
		return "issue-add";
	}

	@PostMapping("/issue/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated Issue issue, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Issue");
			model.addAttribute("modelForm", issue);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);
			return "issue-add";
		}

		User user = (User) session.getAttribute(ConstantUtil.USER_INFO);
//		user.setId(issue.getUserId());
		issue.setUser(user);

		try {
			issueService.saveIssue(issue);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/issue/list";
	}

	// ==================== Edit Issue ====================

	@GetMapping("/issue/edit/{code}")
	public String edit(ModelMap model, @PathVariable("code") String code) {
		log.info("Edit issue with code = " + code);
		Issue issue = issueService.getIssueByCode(code);
		if (issue != null) {
			model.addAttribute("titlePage", "Edit Issue");
			model.addAttribute("modelForm", issue);
			model.addAttribute("viewOnly", false);
			return "issue-edit";
		}
		return "redirect:/issue/list";
	}

	@PostMapping("/issue/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated Issue issue, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Issue");
			model.addAttribute("modelForm", issue);
			model.addAttribute("viewOnly", false);
			return "issue-edit";
		}

		User user = (User) session.getAttribute(ConstantUtil.USER_INFO);
//		user.setId(issue.getUserId());
		issue.setUser(user);

		if (issue.getCode() != null && !issue.getCode().isEmpty()) {
			try {
				issueService.updateIssue(issue);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/issue/list";
	}

	// ==================== Delete Issue ====================

	@GetMapping("/issue/delete/{code}")
	public String delete(ModelMap model, @PathVariable("code") String code, HttpSession session) {
		log.info("Delete issue with code = " + code);
		Issue issue = issueService.getIssueByProperty("code", code).get(0);
		if (issue != null) {
			try {
				issueService.deleteIssue(issue);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/issue/list";
	}
}
