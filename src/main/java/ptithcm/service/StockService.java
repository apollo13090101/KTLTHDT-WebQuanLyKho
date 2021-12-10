package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.StockDao;
import ptithcm.entity.Stock;
import ptithcm.entity.IssueDetail;
import ptithcm.entity.Paging;
import ptithcm.entity.Product;
import ptithcm.entity.ReceiptDetail;

@Service
public class StockService {
	private final static Logger log = Logger.getLogger(StockService.class);

	@Autowired
	private StockDao<Stock> stockDao;

	public List<Stock> getAllStocks(Stock stock, Paging paging) {
		log.info("Get all products in stock");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (stock != null && stock.getProduct() != null) {
			if (!StringUtils.isEmpty(stock.getProduct().getCategory().getCode())) {
				queryStr.append(" and entity.product.category.code = :cateCode");
				mapParams.put("cateCode", stock.getProduct().getCategory().getCode());
			}
			if (!StringUtils.isEmpty(stock.getProduct().getCode())) {
				queryStr.append(" and entity.product.code = :code");
				mapParams.put("code", stock.getProduct().getCode());
			}
			if (!StringUtils.isEmpty(stock.getProduct().getName())) {
				queryStr.append(" and entity.product.name like :name");
				mapParams.put("name", "%" + stock.getProduct().getName() + "%");
			}
		}
		return stockDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public int getStockQuantity(String prod_code) {
		return stockDao.getStockQuantity(prod_code);
	}

	public void saveOrUpdateStockInReceiptDetail(ReceiptDetail receiptDetail) {
		if (receiptDetail.getProduct() != null) {
			String code = receiptDetail.getProduct().getCode();
			List<Stock> stocks = stockDao.getByProperty("product.code", code);
			Stock stock = null;
			if (stocks != null && !stocks.isEmpty()) {
				log.info("Update to stock with quantity = " + receiptDetail.getQuantity() + " and price = "
						+ receiptDetail.getPrice());
				stock = stocks.get(0);

				//stock.setQuantity(stock.getQuantity() + receiptDetail.getQuantity());

				stock.setUpdated(new Date());
				stockDao.update(stock);
			} else {
				log.info("Insert to stock, quantity = " + receiptDetail.getQuantity() + " and price = "
						+ receiptDetail.getPrice());
				stock = new Stock();
				Product product = new Product();
				product.setCode(receiptDetail.getProduct().getCode());
				stock.setProduct(product);
				//stock.setQuantity(receiptDetail.getQuantity());
				stock.setActive(1);
				stock.setCreated(new Date());
				stock.setUpdated(new Date());
				stockDao.save(stock);
			}
		}
	}

	public void saveOrUpdateStockInIssueDetail(IssueDetail issueDetail) {
		if (issueDetail.getProduct() != null) {
			String code = issueDetail.getProduct().getCode();
			List<Stock> stocks = stockDao.getByProperty("product.code", code);
			Stock stock = null;
			if (stocks != null && !stocks.isEmpty()) {
				log.info("Update to stock with quantity = " + issueDetail.getQuantity() + " and price = "
						+ issueDetail.getPrice());
				stock = stocks.get(0);

				//stock.setQuantity(stock.getQuantity() - issueDetail.getQuantity());

				stock.setUpdated(new Date());
				stockDao.update(stock);
			} else {
				log.info("Insert to stock, quantity = " + issueDetail.getQuantity() + " and price = "
						+ issueDetail.getPrice());
				stock = new Stock();
				Product product = new Product();
				product.setCode(issueDetail.getProduct().getCode());
				stock.setProduct(product);
				//stock.setQuantity(issueDetail.getQuantity());
				stock.setActive(1);
				stock.setCreated(new Date());
				stock.setUpdated(new Date());
				stockDao.save(stock);
			}
		}
	}
}
