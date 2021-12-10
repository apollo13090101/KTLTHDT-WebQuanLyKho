package ptithcm.validator;

/*import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ptithcm.service.IssueService;
*/
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.Issue;

@Component
public class IssueValidator implements Validator {

	/*
	 * @Autowired private IssueService categoryService;
	 */

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Issue.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		// Issue category = (Issue) target;
		// ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "customer", "msg.required");

		/*
		 * if (category.getCode() != null) { List<Issue> results =
		 * categoryService.getIssueByProperty("code", category.getCode()); if (results
		 * != null && !results.isEmpty()) { errors.rejectValue("code",
		 * "msg.code.exist"); } }
		 */
	}

}
