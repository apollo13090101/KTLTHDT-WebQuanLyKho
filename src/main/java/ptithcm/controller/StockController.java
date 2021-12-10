package ptithcm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.Stock;
import ptithcm.entity.Paging;
import ptithcm.service.StockService;

@Controller
public class StockController {
	private static final Logger log = Logger.getLogger(StockController.class);

	@Autowired
	private StockService stockService;

	@RequestMapping(value = { "/product-in-stock/list", "/product-in-stock/list/" })
	public String redirect() {
		return "redirect:/product-in-stock/list/1";
	}

	// ==================== View all Stocks ====================

	@RequestMapping(value = "/product-in-stock/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") Stock stock,
			@PathVariable("page") int page) {
		log.info("All Products in stock");
		
		Paging paging = new Paging(20);
		paging.setIndexPage(page);

		List<Stock> stocks = stockService.getAllStocks(stock, paging);

		model.addAttribute("pageInfo", paging);
		model.addAttribute("stocks", stocks);

		return "product-in-stock";
	}

}
