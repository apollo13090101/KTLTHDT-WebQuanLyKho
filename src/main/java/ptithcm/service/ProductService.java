package ptithcm.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.dao.ProductDao;
import ptithcm.entity.Paging;
import ptithcm.entity.Product;
import ptithcm.util.UploadUtil;

@Service
public class ProductService {

	private final static Logger log = Logger.getLogger(ProductService.class);

	@Autowired
	private ProductDao<Product> productDao;

	public List<Product> getAllProducts(Product product, Paging paging) {
		log.info("Get all products");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (product != null) {
			if (product.getCode() != null && !StringUtils.isEmpty(product.getCode())) {
				queryStr.append(" and entity.code = :code");
				mapParams.put("code", product.getCode());
			}
			if (product.getName() != null && !StringUtils.isEmpty(product.getName())) {
				queryStr.append(" and entity.name like :name");
				mapParams.put("name", "%" + product.getName() + "%");
			}
		}
		return productDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public int getNumberOfProducts() {
		return productDao.getNumberOfProducts();
	}

	public List<Product> getProductByProperty(String property, Object value) {
		log.info("Get product by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return productDao.getByProperty(property, value);
	}

	public Product getProductByCode(String code) {
		log.info("Get product by code = " + code);
		return productDao.getByCode(Product.class, code);
	}

	public void saveProduct(Product product) throws Exception {
		log.info("Save product: " + product.toString());
		product.setActive(1);
		product.setCreated(new Date());
		product.setUpdated(new Date());

		String filename = System.currentTimeMillis() + "_" + product.getMultipartFile().getOriginalFilename();
		processUploadFile(product.getMultipartFile(), filename);
		product.setImageUrl("/upload/" + filename);

		productDao.save(product);
	}

	public void updateProduct(Product product) throws Exception {
		log.info("Update product: " + product.toString());
		if (!product.getMultipartFile().getOriginalFilename().isEmpty()) {
			String filename = System.currentTimeMillis() + "_" + product.getMultipartFile().getOriginalFilename();
			processUploadFile(product.getMultipartFile(), filename);
			product.setImageUrl("/upload/" + filename);
		}
		product.setUpdated(new Date());
		productDao.update(product);
	}

	public void deleteProduct(Product product) throws Exception {
		log.info("Delete product: " + product.toString());
		product.setActive(0);
		product.setUpdated(new Date());
		productDao.update(product);
	}

	public void processUploadFile(MultipartFile multipartFile, String filename)
			throws IllegalStateException, IOException {
		if (!multipartFile.getOriginalFilename().isEmpty()) {
			File dir = new File(UploadUtil.getInstance().getValue("upload.location"));
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(UploadUtil.getInstance().getValue("upload.location"), filename);
			multipartFile.transferTo(file);
		}
	}
}
