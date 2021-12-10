package ptithcm.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.User;
import ptithcm.service.UserService;

@Component
public class UserValidator implements Validator {

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

		ValidationUtils.rejectIfEmpty(errors, "lastName", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "firstName", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "password", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "email", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "address", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "birthday", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "salary", "msg.required");

		if (user.getUsername() != null) {
			List<User> results = userService.getUserByProperty("username", user.getUsername());
			if (results != null && !results.isEmpty()) {
				if (user.getId() != null && user.getId() != 0) {
					if (results.get(0).getId() != user.getId()) {
						errors.rejectValue("username", "msg.username.exist");
					}
				} else {
					errors.rejectValue("username", "msg.username.exist");
				}
			}
		}

//		if (user.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
//			errors.rejectValue("salary", "msg.wrong.value");
//		}
	}

}
