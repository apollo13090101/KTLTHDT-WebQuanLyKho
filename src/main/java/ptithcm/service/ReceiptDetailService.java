package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.ReceiptDetailDao;
import ptithcm.entity.ReceiptDetail;
import ptithcm.util.ConstantUtil;
import ptithcm.entity.Paging;
import ptithcm.entity.Product;

@Service
public class ReceiptDetailService {
	private final static Logger log = Logger.getLogger(ReceiptDetailService.class);

	@Autowired
	private ReceiptDetailDao<ReceiptDetail> receiptDetailDao;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private StockService stockService;

	public List<ReceiptDetail> getAllReceiptDetails(ReceiptDetail receiptDetail, Paging paging) {
		log.info("Get all receipt details");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (receiptDetail != null) {
			if (receiptDetail.getId() != null && receiptDetail.getId() != 0) {
				queryStr.append(" and entity.id = :id");
				mapParams.put("id", receiptDetail.getId());
			}

			if (receiptDetail.getReceipt() != null) {
				if (!StringUtils.isEmpty(receiptDetail.getReceipt().getCode())) {
					queryStr.append(" and entity.receipt.code = :receiptCode");
					mapParams.put("receiptCode", receiptDetail.getReceipt().getCode());
				}
			}
			if (receiptDetail.getProduct() != null) {
				if (!StringUtils.isEmpty(receiptDetail.getProduct().getCode())) {
					queryStr.append(" and entity.product.code = :productCode");
					mapParams.put("productCode", receiptDetail.getProduct().getCode());
				}
			}
		}
		return receiptDetailDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public List<ReceiptDetail> getReceiptDetailByProperty(String property, Object value) {
		log.info("Get receiptDetail by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return receiptDetailDao.getByProperty(property, value);
	}

	public ReceiptDetail getReceiptDetailById(int id) {
		log.info("Get receipt detail by id = " + id);
		return receiptDetailDao.getById(ReceiptDetail.class, id);
	}

	public void saveReceiptDetail(ReceiptDetail receiptDetail) throws Exception {
		log.info("Save receiptDetail: " + receiptDetail.toString());
		boolean checkReceiptDetail = receiptDetailDao.checkReceiptDetail(receiptDetail.getReceiptCode(),
				receiptDetail.getProductCode());
		if (!checkReceiptDetail) {
			Product product = new Product();
			product.setCode(receiptDetail.getProductCode());
			receiptDetail.setProduct(product);
			receiptDetail.setActive(1);
			receiptDetail.setCreated(new Date());
			receiptDetail.setUpdated(new Date());
			receiptDetailDao.save(receiptDetail);
			historyService.saveHistoryInReceiptDetail(receiptDetail, ConstantUtil.ACTION_ADD);
			stockService.saveOrUpdateStockInReceiptDetail(receiptDetail);
		}
	}

	public void updateReceiptDetail(ReceiptDetail receiptDetail) throws Exception {
		log.info("Update receiptDetail: " + receiptDetail.toString());
		boolean checkReceiptDetail = receiptDetailDao.checkReceiptDetail(receiptDetail.getReceiptCode(),
				receiptDetail.getProductCode());
		if (!checkReceiptDetail) {
			int origin_qty = receiptDetailDao.getById(ReceiptDetail.class, receiptDetail.getId()).getQuantity();
			Product product = new Product();
			product.setCode(receiptDetail.getProductCode());

			receiptDetail.setProduct(product);
			receiptDetail.setUpdated(new Date());

			ReceiptDetail receiptDetail2 = new ReceiptDetail();
			receiptDetail2.setProduct(receiptDetail.getProduct());
			receiptDetail2.setQuantity(receiptDetail.getQuantity() - origin_qty);
			receiptDetail2.setPrice(receiptDetail.getPrice());

			receiptDetailDao.update(receiptDetail);
			historyService.saveHistoryInReceiptDetail(receiptDetail, ConstantUtil.ACTION_EDIT);
			stockService.saveOrUpdateStockInReceiptDetail(receiptDetail2);
		}
	}

	public void deleteReceiptDetail(ReceiptDetail receiptDetail) throws Exception {
		log.info("Delete receiptDetail: " + receiptDetail.toString());
		receiptDetail.setActive(0);
		receiptDetail.setUpdated(new Date());
		receiptDetailDao.update(receiptDetail);
	}
}
