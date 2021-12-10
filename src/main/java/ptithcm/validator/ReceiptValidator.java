package ptithcm.validator;

/*import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ptithcm.service.ReceiptService;
*/
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.Receipt;

@Component
public class ReceiptValidator implements Validator {

	/*
	 * @Autowired private ReceiptService receiptService;
	 */

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Receipt.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		// Receipt receipt = (Receipt) target;
		// ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "orderCode", "msg.required");

		/*
		 * if (receipt.getCode() != null) { List<Receipt> results =
		 * receiptService.getReceiptByProperty("code", receipt.getCode()); if (results
		 * != null && !results.isEmpty()) { errors.rejectValue("code",
		 * "msg.code.exist"); } }
		 */
	}

}
