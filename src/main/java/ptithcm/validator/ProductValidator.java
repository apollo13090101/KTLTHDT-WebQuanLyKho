package ptithcm.validator;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ptithcm.entity.Product;

@Component
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Product.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Product product = (Product) target;

		ValidationUtils.rejectIfEmpty(errors, "name", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "unit", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "multipartFile", "msg.required");

		if (product.getCode() != null) {

			if (!product.getMultipartFile().getOriginalFilename().isEmpty()) {
				String extension = FilenameUtils.getExtension(product.getMultipartFile().getOriginalFilename());
				if (!extension.equals("jpg") && !extension.equals("png")) {
					errors.rejectValue("multipartFile", "msg.file.extension.error");
				}
			}
		}

	}

}
