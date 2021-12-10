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

import ptithcm.entity.Receipt;
import ptithcm.entity.Order;
import ptithcm.entity.Paging;
import ptithcm.entity.User;
import ptithcm.service.OrderService;
import ptithcm.service.ReceiptService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.ReceiptValidator;

@Controller
public class ReceiptController {
	private static final Logger log = Logger.getLogger(ReceiptController.class);

	@Autowired
	private ReceiptValidator receiptValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == Receipt.class) {
			binder.setValidator(receiptValidator);
		}
	}

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReceiptService receiptService;

	@RequestMapping(value = { "/receipt/list", "/receipt/list/" })
	public String redirect() {
		return "redirect:/receipt/list/1";
	}

	// ==================== View all Receipts ====================

	@RequestMapping(value = "/receipt/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Receipt receipt,
			@PathVariable("page") int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);

		List<Receipt> receipts = receiptService.getAllReceipts(receipt, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("receipts", receipts);

		return "receipt-list";
	}

	// ==================== View Receipt ====================

	@GetMapping("/receipt/view/{code}")
	public String view(ModelMap model, @PathVariable("code") String code) {
		log.info("View receipt with code = " + code);
		Receipt receipt = receiptService.getReceiptByCode(code);
		if (receipt != null) {
			model.addAttribute("titlePage", "View Receipt");
			model.addAttribute("modelForm", receipt);
			model.addAttribute("viewOnly", true);
			return "receipt-view";
		}
		return "redirect:/receipt/list";
	}

	// ==================== Add Receipt ====================

	@GetMapping("/receipt/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Receipt");
		model.addAttribute("modelForm", new Receipt());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);
		model.addAttribute("isCurrentUser", true);
		
		List<Order> orders = orderService.getAllOrders(null, null);
		Map<String, String> mapOrder = new HashMap<>();
		for (Order order : orders) {
			mapOrder.put(String.valueOf(order.getCode()), order.getCode());
		}
		model.addAttribute("mapOrder", mapOrder);
		
		return "receipt-add";
	}

	@PostMapping("/receipt/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated Receipt receipt, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Receipt");
			model.addAttribute("modelForm", receipt);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);

			List<Order> orders = orderService.getAllOrders(null, null);
			Map<String, String> mapOrder = new HashMap<>();
			for (Order order : orders) {
				mapOrder.put(String.valueOf(order.getCode()), order.getCode());
			}
			model.addAttribute("mapOrder", mapOrder);

			return "receipt-add";
		}

		User user = (User) session.getAttribute(ConstantUtil.USER_INFO);
//		user.setId(receipt.getUserId());
		receipt.setUser(user);
		
		Order order = new Order();
		order.setCode(receipt.getOrderCode());
		receipt.setOrder(order);
		

		try {
			receiptService.saveReceipt(receipt);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/receipt/list";
	}

	// ==================== Edit Receipt ====================

	@GetMapping("/receipt/edit/{code}")
	public String edit(ModelMap model, @PathVariable("code") String code) {
		log.info("Edit receipt with code = " + code);
		Receipt receipt = receiptService.getReceiptByCode(code);
		if (receipt != null) {
			model.addAttribute("titlePage", "Edit Receipt");
			model.addAttribute("modelForm", receipt);
			model.addAttribute("viewOnly", false);
			
			List<Order> orders = orderService.getAllOrders(null, null);
			Map<String, String> mapOrder = new HashMap<>();
			for (Order order : orders) {
				mapOrder.put(String.valueOf(order.getCode()), order.getCode());
			}
			model.addAttribute("mapOrder", mapOrder);
			
			return "receipt-edit";
		}
		return "redirect:/receipt/list";
	}

	@PostMapping("/receipt/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated Receipt receipt, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Receipt");
			model.addAttribute("modelForm", receipt);
			model.addAttribute("viewOnly", false);
			
			List<Order> orders = orderService.getAllOrders(null, null);
			Map<String, String> mapOrder = new HashMap<>();
			for (Order order : orders) {
				mapOrder.put(String.valueOf(order.getCode()), order.getCode());
			}
			model.addAttribute("mapOrder", mapOrder);
			
			return "receipt-edit";
		}

		User user = (User) session.getAttribute(ConstantUtil.USER_INFO);
//		user.setId(receipt.getUserId());
		receipt.setUser(user);
		
		Order order = new Order();
		order.setCode(receipt.getOrderCode());
		receipt.setOrder(order);

		if (receipt.getCode() != null && !receipt.getCode().isEmpty()) {
			try {
				receiptService.updateReceipt(receipt);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/receipt/list";
	}

	// ==================== Delete Receipt ====================

	@GetMapping("/receipt/delete/{code}")
	public String delete(ModelMap model, @PathVariable("code") String code, HttpSession session) {
		log.info("Delete receipt with code = " + code);
		Receipt receipt = receiptService.getReceiptByProperty("code", code).get(0);
		if (receipt != null) {
			try {
				receiptService.deleteReceipt(receipt);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/receipt/list";
	}
}
