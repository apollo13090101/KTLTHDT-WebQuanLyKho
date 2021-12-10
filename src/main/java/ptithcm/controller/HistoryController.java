package ptithcm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.History;
import ptithcm.entity.Paging;
import ptithcm.service.HistoryService;
import ptithcm.util.ConstantUtil;

@Controller
public class HistoryController {
	private static final Logger log = Logger.getLogger(HistoryController.class);

	@Autowired
	private HistoryService historyService;

	@RequestMapping(value = { "/history/list", "/history/list/" })
	public String redirect() {
		return "redirect:/history/list/1";
	}

	// ==================== View all Historys ====================

	@RequestMapping(value = "/history/list/{page}")
	public String list(ModelMap model, HttpSession session, @ModelAttribute("searchForm") History history,
			@PathVariable("page") int page) {
		log.info("All histories");

		Paging paging = new Paging(20);
		paging.setIndexPage(page);

		List<History> histories = historyService.getAllHistories(history, paging);

		model.addAttribute("pageInfo", paging);
		model.addAttribute("histories", histories);

		Map<String, String> mapType = new HashMap<>();
		mapType.put(String.valueOf(ConstantUtil.TYPE_ALL), "All");
		mapType.put(String.valueOf(ConstantUtil.TYPE_GOODS_RECEIPT), "Goods Receipt");
		mapType.put(String.valueOf(ConstantUtil.TYPE_GOODS_ISSUES), "Goods Issue");

		model.addAttribute("mapType", mapType);

		return "history";
	}

}
