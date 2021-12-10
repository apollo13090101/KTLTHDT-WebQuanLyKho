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

@Component
public class ResetPasswordValidator implements Validator {

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
		ValidationUtils.rejectIfEmpty(errors, "email", "msg.required");

		if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getEmail())) {
			List<User> results = userService.getUserByProperty("username", user.getUsername());
			if (user != null && !results.isEmpty()) {
				if (!results.get(0).getEmail().equals(user.getEmail())) {
					errors.rejectValue("email", "msg.wrong.email");
				}
			} else {
				errors.rejectValue("username", "msg.wrong.username");
			}
		}
	}

}
