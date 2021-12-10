package ptithcm.validator;


/*import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ptithcm.service.OrderService;
*/
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.Order;

@Component
public class OrderValidator implements Validator {

	/*
	 * @Autowired private OrderService categoryService;
	 */

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Order.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		// Order category = (Order) target;
		// ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "supplier", "msg.required");

		/*
		 * if (category.getCode() != null) { List<Order> results =
		 * categoryService.getOrderByProperty("code", category.getCode()); if
		 * (results != null && !results.isEmpty()) { errors.rejectValue("code",
		 * "msg.code.exist"); } }
		 */
	}

}
