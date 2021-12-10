
package ptithcm.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReportCTNXController {
	private static final Logger log = Logger.getLogger(ReportCTNXController.class);

	@RequestMapping(value = "/ctnx", method = RequestMethod.GET)
	public String report(ModelMap model) {
		log.info("Detail of Import/Export");
		return "ctnx";
	}
	
	@RequestMapping(value = "/ctnx/export", method = RequestMethod.POST)
	public String export(ModelMap model) {
		log.info("Detail of Import/Export");
		return "report/ctnx_export";
	}

}
