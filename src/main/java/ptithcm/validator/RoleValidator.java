package ptithcm.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.Role;
import ptithcm.service.RoleService;

@Component
public class RoleValidator implements Validator {

	@Autowired
	private RoleService roleService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Role.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Role role = (Role) target;
		ValidationUtils.rejectIfEmpty(errors, "roleName", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");

		if (role.getRoleName() != null) {
			List<Role> results = roleService.getRoleByProperty("roleName", role.getRoleName());
			if (results != null && !results.isEmpty()) {
				if (role.getId() != null && role.getId() != 0) {
					if (results.get(0).getId() != role.getId()) {
						errors.rejectValue("roleName", "msg.role.exist");
					}
				} else {
					errors.rejectValue("roleName", "msg.role.exist");
				}
			}
		}
	}

}
