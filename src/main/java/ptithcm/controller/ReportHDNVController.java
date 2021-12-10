
package ptithcm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.User;
import ptithcm.service.UserService;

@Controller
public class ReportHDNVController {
	private static final Logger log = Logger.getLogger(ReportHDNVController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/hdnv", method = RequestMethod.GET)
	public String report(ModelMap model) {
		log.info("Employee Evaluation");

		List<User> users = userService.getAllUsers(null, null);
		Map<String, String> mapUser = new HashMap<>();
		for (User user : users) {
			mapUser.put(String.valueOf(user.getId()), user.getLastName() + " " + user.getFirstName());
		}
		model.addAttribute("mapUser", mapUser);

		return "hdnv";
	}

	@RequestMapping(value = "/hdnv/export", method = RequestMethod.POST)
	public String export(ModelMap model) {
		log.info("Employee Evaluation");
		return "report/hdnv_export";
	}

}
