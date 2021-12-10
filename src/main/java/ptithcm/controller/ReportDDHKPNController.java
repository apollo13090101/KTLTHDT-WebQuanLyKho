
package ptithcm.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReportDDHKPNController {
	private static final Logger log = Logger.getLogger(ReportDDHKPNController.class);

	@RequestMapping(value = "/ddhkpn", method = RequestMethod.GET)
	public String report(ModelMap model) {
		log.info("Orders without receipts");
		return "ddhkpn";
	}
	
	@RequestMapping(value = "/ddhkpn/export", method = RequestMethod.POST)
	public String export(ModelMap model) {
		log.info("Orders without receipts");
		return "report/ddhkpn_export";
	}

}
