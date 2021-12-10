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
import ptithcm.entity.ReceiptDetail;
import ptithcm.entity.Paging;
import ptithcm.entity.Product;
import ptithcm.service.ReceiptDetailService;
import ptithcm.service.ReceiptService;
import ptithcm.service.ProductService;
import ptithcm.util.ConstantUtil;
import ptithcm.validator.ReceiptDetailValidator;

@Controller
public class ReceiptDetailController {
	private static final Logger log = Logger.getLogger(ReceiptDetailController.class);

	@Autowired
	private ReceiptDetailValidator receiptDetailValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == ReceiptDetail.class) {
			binder.setValidator(receiptDetailValidator);
		}
	}

	@Autowired
	private ReceiptService receiptService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ReceiptDetailService receiptDetailService;

	@RequestMapping(value = { "/receipt-detail/list", "/receipt-detail/list/" })
	public String redirect() {
		return "redirect:/receipt-detail/list/1";
	}

	// ==================== View all ReceiptDetails ====================

	@RequestMapping(value = "/receipt-detail/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") ReceiptDetail receiptDetail,
			@PathVariable("page") int page) {
		Paging paging = new Paging(10);
		paging.setIndexPage(page);

		List<ReceiptDetail> receiptDetails = receiptDetailService.getAllReceiptDetails(receiptDetail, paging);

		if (session.getAttribute(ConstantUtil.MSG_SUCCESS) != null) {
			model.addAttribute(ConstantUtil.MSG_SUCCESS, session.getAttribute(ConstantUtil.MSG_SUCCESS));
			session.removeAttribute(ConstantUtil.MSG_SUCCESS);
		}

		if (session.getAttribute(ConstantUtil.MSG_ERROR) != null) {
			model.addAttribute(ConstantUtil.MSG_ERROR, session.getAttribute(ConstantUtil.MSG_ERROR));
			session.removeAttribute(ConstantUtil.MSG_ERROR);
		}

		model.addAttribute("pageInfo", paging);
		model.addAttribute("receiptDetails", receiptDetails);

		return "receipt-detail-list";
	}

	// ==================== View ReceiptDetail ====================

	@GetMapping("/receipt-detail/view/{id}")
	public String view(ModelMap model, @PathVariable("id") int id) {
		log.info("View receipt detail with id = " + id);
		ReceiptDetail receiptDetail = receiptDetailService.getReceiptDetailById(id);
		if (receiptDetail != null) {
			model.addAttribute("titlePage", "View Receipt Detail");
			model.addAttribute("modelForm", receiptDetail);
			model.addAttribute("viewOnly", true);
			return "receipt-detail-view";
		}
		return "redirect:/receipt-detail/list";
	}

	// ==================== Add ReceiptDetail ====================

	@GetMapping("/receipt-detail/add")
	public String add(ModelMap model) {
		model.addAttribute("titlePage", "Add Receipt Detail");
		model.addAttribute("modelForm", new ReceiptDetail());
		model.addAttribute("viewOnly", false);
		model.addAttribute("editCode", false);
		model.addAttribute("isCurrentUser", true);

		List<Receipt> receipts = receiptService.getAllReceipts(null, null);
		Map<String, String> mapReceipt = new HashMap<>();
		for (Receipt receipt : receipts) {
			mapReceipt.put(String.valueOf(receipt.getCode()), receipt.getCode());
		}
		model.addAttribute("mapReceipt", mapReceipt);

		List<Product> products = productService.getAllProducts(null, null);
		Map<String, String> mapProduct = new HashMap<>();
		for (Product product : products) {
			mapProduct.put(String.valueOf(product.getCode()), product.getName());
		}
		model.addAttribute("mapProduct", mapProduct);

		return "receipt-detail-add";
	}

	@PostMapping("/receipt-detail/add")
	public String add(ModelMap model, @ModelAttribute("modelForm") @Validated ReceiptDetail receiptDetail,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Add Receipt Detail");
			model.addAttribute("modelForm", receiptDetail);
			model.addAttribute("viewOnly", false);
			model.addAttribute("editCode", false);

			List<Receipt> receipts = receiptService.getAllReceipts(null, null);
			Map<String, String> mapReceipt = new HashMap<>();
			for (Receipt receipt : receipts) {
				mapReceipt.put(String.valueOf(receipt.getCode()), receipt.getCode());
			}
			model.addAttribute("mapReceipt", mapReceipt);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);

			return "receipt-detail-add";
		}

		Receipt receipt = new Receipt();
		receipt.setCode(receiptDetail.getReceiptCode());
		receiptDetail.setReceipt(receipt);

		Product product = new Product();
		product.setCode(receiptDetail.getProductCode());
		receiptDetail.setProduct(product);

		try {
			receiptDetailService.saveReceiptDetail(receiptDetail);
			session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully saved!!!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.info(e.getMessage());
			session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to save.");
		}

		return "redirect:/receipt-detail/list";
	}

	// ==================== Edit ReceiptDetail ====================

	@GetMapping("/receipt-detail/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") int id) {
		log.info("Edit receipt detail with id = " + id);
		ReceiptDetail receiptDetail = receiptDetailService.getReceiptDetailById(id);
		if (receiptDetail != null) {
			model.addAttribute("titlePage", "Edit Receipt Detail");
			model.addAttribute("modelForm", receiptDetail);
			model.addAttribute("viewOnly", false);
			
			List<Receipt> receipts = receiptService.getAllReceipts(null, null);
			Map<String, String> mapReceipt = new HashMap<>();
			for (Receipt receipt : receipts) {
				mapReceipt.put(String.valueOf(receipt.getCode()), receipt.getCode());
			}
			model.addAttribute("mapReceipt", mapReceipt);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);
			
			return "receipt-detail-edit";
		}
		return "redirect:/receipt-detail/list";
	}

	@PostMapping("/receipt-detail/edit")
	public String edit(ModelMap model, @ModelAttribute("modelForm") @Validated ReceiptDetail receiptDetail,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			model.addAttribute("titlePage", "Edit Receipt Detail");
			model.addAttribute("modelForm", receiptDetail);
			model.addAttribute("viewOnly", false);
			
			List<Receipt> receipts = receiptService.getAllReceipts(null, null);
			Map<String, String> mapReceipt = new HashMap<>();
			for (Receipt receipt : receipts) {
				mapReceipt.put(String.valueOf(receipt.getCode()), receipt.getCode());
			}
			model.addAttribute("mapReceipt", mapReceipt);

			List<Product> products = productService.getAllProducts(null, null);
			Map<String, String> mapProduct = new HashMap<>();
			for (Product product : products) {
				mapProduct.put(String.valueOf(product.getCode()), product.getName());
			}
			model.addAttribute("mapProduct", mapProduct);
			
			return "receipt-detail-edit";
		}
		
		Receipt receipt = new Receipt();
		receipt.setCode(receiptDetail.getReceiptCode());
		receiptDetail.setReceipt(receipt);

		Product product = new Product();
		product.setCode(receiptDetail.getProductCode());
		receiptDetail.setProduct(product);

		if (receiptDetail.getId() != null && receiptDetail.getId() != 0) {
			try {
				receiptDetailService.updateReceiptDetail(receiptDetail);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update.");
			}
		}

		return "redirect:/receipt-detail/list";
	}

	// ==================== Delete ReceiptDetail ====================

	@GetMapping("/receipt-detail/delete/{id}")
	public String delete(ModelMap model, @PathVariable("id") int id, HttpSession session) {
		log.info("Delete receipt detail with id = " + id);
		ReceiptDetail receiptDetail = receiptDetailService.getReceiptDetailByProperty("id", id).get(0);
		if (receiptDetail != null) {
			try {
				receiptDetailService.deleteReceiptDetail(receiptDetail);
				session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully deleted!!!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				log.info(e.getMessage());
				session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to delete.");
			}
		}
		return "redirect:/receipt-detail/list";
	}
}
