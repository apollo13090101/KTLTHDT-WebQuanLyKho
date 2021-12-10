package ptithcm.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.User;
import ptithcm.service.UserService;
import ptithcm.util.HashingPasswordUtil;

@Component
public class LoginValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == User.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");

		if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
			List<User> results = userService.getUserByProperty("username", user.getUsername());
			if (user != null && !results.isEmpty()) {
				if (!results.get(0).getPassword().equals(HashingPasswordUtil.encrypt(user.getPassword()))) {
					errors.rejectValue("password", "msg.wrong.password");
				}
			} else {
				errors.rejectValue("username", "msg.wrong.username");
			}
		}
	}

}
