
package ptithcm.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReportTHNXController {
	private static final Logger log = Logger.getLogger(ReportTHNXController.class);

	@RequestMapping(value = "/thnx", method = RequestMethod.GET)
	public String report(ModelMap model) {
		log.info("Summary of Import/Export");
		return "thnx";
	}

	@RequestMapping(value = "/thnx/export", method = RequestMethod.POST)
	public String export(ModelMap model) {
		log.info("Summary of Import/Export");
		return "report/thnx_export";
	}

}
