package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.ReceiptDao;
import ptithcm.entity.Receipt;
import ptithcm.entity.Paging;

@Service
public class ReceiptService {
	private final static Logger log = Logger.getLogger(ReceiptService.class);

	@Autowired
	private ReceiptDao<Receipt> receiptDao;

	public List<Receipt> getAllReceipts(Receipt receipt, Paging paging) {
		log.info("Get all receipts");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (receipt != null) {
			if (receipt.getCode() != null && !StringUtils.isEmpty(receipt.getCode())) {
				queryStr.append(" and entity.code = :code");
				mapParams.put("code", receipt.getCode());
			}
			if (receipt.getOrder() != null) {
				if (receipt.getOrder().getCode() != null && !StringUtils.isEmpty(receipt.getOrder().getCode())) {
					queryStr.append(" and entity.order.code = :orderCode");
					mapParams.put("orderCode", receipt.getOrder().getCode());
				}
			}
		}
		return receiptDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public List<Receipt> getReceiptByProperty(String property, Object value) {
		log.info("Get receipt by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return receiptDao.getByProperty(property, value);
	}

	public Receipt getReceiptByCode(String code) {
		log.info("Get receipt by code = " + code);
		return receiptDao.getByCode(Receipt.class, code);
	}

	public void saveReceipt(Receipt receipt) throws Exception {
		log.info("Save receipt: " + receipt.toString());
		boolean checkReceipt = receiptDao.checkReceipt();
		if (!checkReceipt) {
			receipt.setActive(1);
			receipt.setCreated(new Date());
			receipt.setUpdated(new Date());
			receiptDao.save(receipt);
		}
	}

	public void updateReceipt(Receipt receipt) throws Exception {
		log.info("Update receipt: " + receipt.toString());
		boolean checkReceipt = receiptDao.checkReceipt();
		if (!checkReceipt) {
			receipt.setUpdated(new Date());
			receiptDao.update(receipt);
		}
	}

	public void deleteReceipt(Receipt receipt) throws Exception {
		log.info("Delete receipt: " + receipt.toString());
		receipt.setActive(0);
		receipt.setUpdated(new Date());
		receiptDao.update(receipt);
	}
}
