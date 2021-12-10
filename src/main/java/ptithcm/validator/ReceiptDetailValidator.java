package ptithcm.validator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.ReceiptDetail;
import ptithcm.service.OrderDetailService;

@Component
public class ReceiptDetailValidator implements Validator {

	@Autowired
	private OrderDetailService orderDetailService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == ReceiptDetail.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ReceiptDetail receiptDetail = (ReceiptDetail) target;
		// ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "receiptCode", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "productCode", "msg.required");

		/*
		 * if (category.getCode() != null) { List<ReceiptDetail> results =
		 * categoryService.getReceiptDetailByProperty("code", category.getCode()); if
		 * (results != null && !results.isEmpty()) { errors.rejectValue("code",
		 * "msg.code.exist"); } }
		 */
			
		if (orderDetailService.getOrderDetailQuantity(receiptDetail.getProductCode()) == 0) {
			errors.rejectValue("productCode", "msg.quantity");
		}
		
		if ((receiptDetail.getQuantity() <= 0) || (receiptDetail.getQuantity() > orderDetailService
				.getOrderDetailQuantity(receiptDetail.getProductCode()))) {
			errors.rejectValue("quantity", "msg.wrong.value");
		}

		if ((receiptDetail.getPrice().compareTo(BigDecimal.ZERO) <= 0)) {
			errors.rejectValue("price", "msg.wrong.value");
		}
	}

}
