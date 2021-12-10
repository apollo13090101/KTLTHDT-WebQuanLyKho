package ptithcm.validator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.IssueDetail;
import ptithcm.service.StockService;

@Component
public class IssueDetailValidator implements Validator {

	@Autowired
	private StockService stockService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == IssueDetail.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		IssueDetail issueDetail = (IssueDetail) target;
		// ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "issueCode", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "productCode", "msg.required");

		/*
		 * if (category.getCode() != null) { List<IssueDetail> results =
		 * categoryService.getIssueDetailByProperty("code", category.getCode()); if
		 * (results != null && !results.isEmpty()) { errors.rejectValue("code",
		 * "msg.code.exist"); } }
		 */

		if ((issueDetail.getQuantity() <= 0)
				|| (issueDetail.getQuantity() > stockService.getStockQuantity(issueDetail.getProductCode()))) {
			errors.rejectValue("quantity", "msg.wrong.value");
		}

		if (issueDetail.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("price", "msg.wrong.value");
		}
	}

}
