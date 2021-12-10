package ptithcm.controller;

import java.sql.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.dao.UserDao;
import ptithcm.dao.VerifyId;
import ptithcm.entity.User;
import ptithcm.service.UserService;
import ptithcm.util.ConstantUtil;
import ptithcm.util.HashingPasswordUtil;
import ptithcm.validator.ResetPasswordValidator;

@Controller
public class ResetPasswordController {
	private String ID = "";
	private User user;
	private static final Logger log = Logger.getLogger(ResetPasswordController.class);

	@Autowired
	ResetPasswordValidator passwordValidator;

	@Autowired
	UserService service;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null) {
			return;
		}
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		if (binder.getTarget().getClass() == User.class) {
			binder.setValidator(passwordValidator);
		}
	}

	public String randomIdCode(int lenght) {
		String Id = "";
		Random random = new Random();
		for (int i = 0; i < lenght; i++) {
			Id = Id + random.nextInt(10);
		}
		return Id;
	}

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao<User> userDao;

	@Autowired
	JavaMailSender javaMailSender;

	/* Reset password */
	@GetMapping("/resetpassword")
	public String resetpassword(ModelMap model) {
		model.addAttribute("resetpasswordForm", new User());
		return "login/resetpassword";
	}

	@PostMapping("/resetpassword")
	public String resetpassword(Model model, @ModelAttribute("resetpasswordForm") @Validated User users,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "login/resetpassword";
		}

		try {
			ID = randomIdCode(4);
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("tieuhaoauto01@gmail.com");
			helper.setTo(users.getEmail());
			helper.setSubject("IMS - ID RESET PASSWORD");
			helper.setText("Your Code: " + ID);
			javaMailSender.send(message);
			user = users;
		} catch (Exception e) {
			e.printStackTrace();
			return "login/resetpassword";
		}

		return "redirect:/verifyid";
	}

	@GetMapping("/verifyid")
	public String verifyId() {
		return "login/verify-id";
	}

	@PostMapping("/verifyid")
	public String verifyId(Model model, @RequestParam("code") String code, HttpSession session) {

		if (code.isEmpty() || code == "") {
			model.addAttribute("message", "Required!");
			System.out.println("Rỗng");
			return "login/verify-id";
		} else {
			int temp = Integer.parseInt(code);
			int IDt = Integer.parseInt(ID);
			if (temp == IDt) {
				System.out.println("Nhập đúng");
				List<User> users = userService.getUserByProperty("username", user.getUsername());
				if (users != null && !users.isEmpty()) {
					User user1 = users.get(0);
					String newPass = randomIdCode(6);

					user1.setPassword(HashingPasswordUtil.encrypt(newPass));
					try {
						userDao.update(user1);
						session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated password!!!");

						MimeMessage message = javaMailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						helper.setFrom("tieuhaoauto01@gmail.com");
						helper.setTo(user1.getEmail());
						helper.setSubject("IMS - RESET PASSWORD SUCCESSFULLY");
						helper.setText("Your new password: " + newPass);
						javaMailSender.send(message);

						return "redirect:/login";
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						log.info(e.getMessage());
						session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update password.");
					}
				}
			}
		}

//		if(code.isEmpty()){
//			model.addAttribute("message", "Required!");
//			System.out.println("Rỗng");
//			return "login/verify-id";
//		}

		if (!code.equals(ID)) {
			model.addAttribute("message", "Invalid Code Id!");
			System.out.println("Nhập sai");
			System.out.println("ID right: " + ID);
			System.out.println("ID input: " + code);
			return "login/verify-id";
		}
//		else {
//			System.out.println("Nhập đúng");
//			List<User> users = userService.getUserByProperty("username", user.getUsername());
//			if(users != null && !users.isEmpty()) {
//				User user1 = users.get(0);
//				String newPass = randomIdCode(6);
//				
//				user1.setPassword(HashingPasswordUtil.encrypt(newPass));
//				try {
//					userService.updateUser(user1);
//					session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated password!!!");
//					
//					MimeMessage message = javaMailSender.createMimeMessage();
//					MimeMessageHelper helper = new MimeMessageHelper(message, true);
//					helper.setFrom("tieuhaoauto01@gmail.com");
//					helper.setTo(user1.getEmail());
//					helper.setSubject("IMS - RESET PASSWORD SUCCESSFULLY");
//					helper.setText("Your new password: "+newPass);
//					javaMailSender.send(message);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					log.info(e.getMessage());
//					session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update password.");
//				}
//			}

		return "login/verify-id";
	}

//	@PostMapping("/verifyid")
//	public String verifyId(Model model, @RequestParam("code") String code, HttpSession session) {
//		VerifyId id2 = new VerifyId();
//		id2.setId(code);
//		
//		if(id2.getId().isEmpty()){
//			model.addAttribute("message", "Required!");
//			return "login/verify-id";
//		}
//		if(id2.getId()!=ID){
//			model.addAttribute("message", "Invalid Code Id!");
//			System.out.println("ID right: "+ID);
//			System.out.println("ID input: "+id2.toString());
//			return "login/verify-id";
//		}
//		if(id2.getId()==ID){
//			List<User> users = userService.getUserByProperty("username", user.getUsername());
//			if(users != null && !users.isEmpty()) {
//				User user1 = users.get(0);
//				String newPass = randomIdCode(6);
//				
//				user1.setPassword(HashingPasswordUtil.encrypt(newPass));
//				try {
//					userService.updateUser(user1);
//					session.setAttribute(ConstantUtil.MSG_SUCCESS, "Successfully updated password!!!");
//					
//					MimeMessage message = javaMailSender.createMimeMessage();
//					MimeMessageHelper helper = new MimeMessageHelper(message, true);
//					helper.setFrom("tieuhaoauto01@gmail.com");
//					helper.setTo(user1.getEmail());
//					helper.setSubject("IMS - RESET PASSWORD SUCCESSFULLY");
//					helper.setText("Your new password: "+newPass);
//					javaMailSender.send(message);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//					log.info(e.getMessage());
//					session.setAttribute(ConstantUtil.MSG_ERROR, "Failed to update password.");
//				}
//			}
//			
//		}
//		return "login/login";
//	}

}
