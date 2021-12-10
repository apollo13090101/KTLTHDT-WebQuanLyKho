package ptithcm.validator;

import java.math.BigDecimal;

/*import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ptithcm.service.OrderDetailService;
*/
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.OrderDetail;

@Component
public class OrderDetailValidator implements Validator {

	/*
	 * @Autowired private OrderDetailService categoryService;
	 */

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == OrderDetail.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		OrderDetail orderDetail = (OrderDetail) target;
		// ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "orderCode", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "productCode", "msg.required");

		/*
		 * if (category.getCode() != null) { List<OrderDetail> results =
		 * categoryService.getOrderDetailByProperty("code", category.getCode()); if
		 * (results != null && !results.isEmpty()) { errors.rejectValue("code",
		 * "msg.code.exist"); } }
		 */

		if (orderDetail.getQuantity() <= 0) {
			errors.rejectValue("quantity", "msg.wrong.value");
		}

		if (orderDetail.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("price", "msg.wrong.value");
		}
	}

}
