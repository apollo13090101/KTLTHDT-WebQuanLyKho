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

import ptithcm.entity.Order;
import ptithcm.entity.Paging;
import ptithcm.entity.User;
import ptithcm.service.OrderService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.OrderValidator;

@Controller
public class OrderController {
	private static final Logger log = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderValidator orderValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == Order.class) {
			binder.setValidator(orderValidator);
		}
	}

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = { "/order/list", "/order/list/" })
	public String redirect() {
		return "redirect:/order/list/1";
	}

	// ==================== View all Orders ====================

	@RequestMapping(value = "/order/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Order order,
			@PathVariable("page") int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);

		List<Order> orders = orderService.getAllOrders(order, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("orders", orders);

		return "order-list";
	}

	// ==================== View Order ====================

	@GetMapping("/order/view/{code}")
	public String view(ModelMap model, @PathVariable("code") String code) {
		log.info("View order with code = " + code);
		Order order = orderService.getOrderByCode(code);
		if (order != null) {
			model.addAttribute("titlePage", "View Order");
			model.addAttribute("modelForm", order);
			model.addAttribute("viewOnly", true);
			return "order-view";
		}
		return "redirect:/order/list";
	}

	// ==================== Add Order ====================

	@GetMapping("/order/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Order");
		model.addAttribute("modelForm", new Order());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);
		model.addAttribute("isCurrentUser", true);
		return "order-add";
	}

	@PostMapping("/order/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated Order order, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Order");
			model.addAttribute("modelForm", order);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);
			return "order-add";
		}

		User user = (User) session.getAttribute(ConstantUtil.USER_INFO);
//		user.setId(order.getUserId());
		order.setUser(user);

		try {
			orderService.saveOrder(order);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/order/list";
	}

	// ==================== Edit Order ====================

	@GetMapping("/order/edit/{code}")
	public String edit(ModelMap model, @PathVariable("code") String code) {
		log.info("Edit order with code = " + code);
		Order order = orderService.getOrderByCode(code);
		if (order != null) {
			model.addAttribute("titlePage", "Edit Order");
			model.addAttribute("modelForm", order);
			model.addAttribute("viewOnly", false);
			return "order-edit";
		}
		return "redirect:/order/list";
	}

	@PostMapping("/order/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated Order order, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Order");
			model.addAttribute("modelForm", order);
			model.addAttribute("viewOnly", false);
			return "order-edit";
		}

		User user = (User) session.getAttribute(ConstantUtil.USER_INFO);
//		user.setId(order.getUserId());
		order.setUser(user);

		if (order.getCode() != null && !order.getCode().isEmpty()) {
			try {
				orderService.updateOrder(order);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/order/list";
	}

	// ==================== Delete Order ====================

	@GetMapping("/order/delete/{code}")
	public String delete(ModelMap model, @PathVariable("code") String code, HttpSession session) {
		log.info("Delete order with code = " + code);
		Order order = orderService.getOrderByProperty("code", code).get(0);
		if (order != null) {
			try {
				orderService.deleteOrder(order);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/order/list";
	}
}
