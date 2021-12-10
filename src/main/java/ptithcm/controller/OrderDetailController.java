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

import ptithcm.entity.Order;
import ptithcm.entity.OrderDetail;
import ptithcm.entity.Paging;
import ptithcm.entity.Product;
import ptithcm.service.OrderDetailService;
import ptithcm.service.OrderService;
import ptithcm.service.ProductService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.OrderDetailValidator;

@Controller
public class OrderDetailController {
	private static final Logger log = Logger.getLogger(OrderDetailController.class);

	@Autowired
	private OrderDetailValidator orderDetailValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == OrderDetail.class) {
			binder.setValidator(orderDetailValidator);
		}
	}

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderDetailService orderDetailService;

	@RequestMapping(value = { "/order-detail/list", "/order-detail/list/" })
	public String redirect() {
		return "redirect:/order-detail/list/1";
	}

	// ==================== View all OrderDetails ====================

	@RequestMapping(value = "/order-detail/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") OrderDetail orderDetail,
			@PathVariable("page") int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);

		List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails(orderDetail, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("orderDetails", orderDetails);

		return "order-detail-list";
	}

	// ==================== View OrderDetail ====================

	@GetMapping("/order-detail/view/{id}")
	public String view(ModelMap model, @PathVariable("id") int id) {
		log.info("View order detail with id = " + id);
		OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
		if (orderDetail != null) {
			model.addAttribute("titlePage", "View Order Detail");
			model.addAttribute("modelForm", orderDetail);
			model.addAttribute("viewOnly", true);
			return "order-detail-view";
		}
		return "redirect:/order-detail/list";
	}

	// ==================== Add OrderDetail ====================

	@GetMapping("/order-detail/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Order Detail");
		model.addAttribute("modelForm", new OrderDetail());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);
		model.addAttribute("isCurrentUser", true);

		List<Order> orders = orderService.getAllOrders(null, null);
		Map<String, String> mapOrder = new HashMap<>();
		for (Order order : orders) {
			mapOrder.put(String.valueOf(order.getCode()), order.getCode());
		}
		model.addAttribute("mapOrder", mapOrder);

		List<Product> products = productService.getAllProducts(null, null);
		Map<String, String> mapProduct = new HashMap<>();
		for (Product product : products) {
			mapProduct.put(String.valueOf(product.getCode()), product.getName());
		}
		model.addAttribute("mapProduct", mapProduct);

		return "order-detail-add";
	}

	@PostMapping("/order-detail/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated OrderDetail orderDetail,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Order Detail");
			model.addAttribute("modelForm", orderDetail);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);

			List<Order> orders = orderService.getAllOrders(null, null);
			Map<String, String> mapOrder = new HashMap<>();
			for (Order order : orders) {
				mapOrder.put(String.valueOf(order.getCode()), order.getCode());
			}
			model.addAttribute("mapOrder", mapOrder);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);

			return "order-detail-add";
		}

		Order order = new Order();
		order.setCode(orderDetail.getOrderCode());
		orderDetail.setOrder(order);

		Product product = new Product();
		product.setCode(orderDetail.getProductCode());
		orderDetail.setProduct(product);

		try {
			orderDetailService.saveOrderDetail(orderDetail);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/order-detail/list";
	}

	// ==================== Edit OrderDetail ====================

	@GetMapping("/order-detail/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") int id) {
		log.info("Edit order detail with id = " + id);
		OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
		if (orderDetail != null) {
			model.addAttribute("titlePage", "Edit Order Detail");
			model.addAttribute("modelForm", orderDetail);
			model.addAttribute("viewOnly", false);
			
			List<Order> orders = orderService.getAllOrders(null, null);
			Map<String, String> mapOrder = new HashMap<>();
			for (Order order : orders) {
				mapOrder.put(String.valueOf(order.getCode()), order.getCode());
			}
			model.addAttribute("mapOrder", mapOrder);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);
			
			return "order-detail-edit";
		}
		return "redirect:/order-detail/list";
	}

	@PostMapping("/order-detail/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated OrderDetail orderDetail,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Order Detail");
			model.addAttribute("modelForm", orderDetail);
			model.addAttribute("viewOnly", false);
			
			List<Order> orders = orderService.getAllOrders(null, null);
			Map<String, String> mapOrder = new HashMap<>();
			for (Order order : orders) {
				mapOrder.put(String.valueOf(order.getCode()), order.getCode());
			}
			model.addAttribute("mapOrder", mapOrder);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);
			
			return "order-detail-edit";
		}
		
		Order order = new Order();
		order.setCode(orderDetail.getOrderCode());
		orderDetail.setOrder(order);

		Product product = new Product();
		product.setCode(orderDetail.getProductCode());
		orderDetail.setProduct(product);

		if (orderDetail.getId() != null && orderDetail.getId() != 0) {
			try {
				orderDetailService.updateOrderDetail(orderDetail);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/order-detail/list";
	}

	// ==================== Delete OrderDetail ====================

	@GetMapping("/order-detail/delete/{id}")
	public String delete(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete order detail with id = " + id);
		OrderDetail orderDetail = orderDetailService.getOrderDetailByProperty("id", id).get(0);
		if (orderDetail != null) {
			try {
				orderDetailService.deleteOrderDetail(orderDetail);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/order-detail/list";
	}
}
