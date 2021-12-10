package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.HistoryDao;
import ptithcm.entity.History;
import ptithcm.entity.IssueDetail;
import ptithcm.entity.Paging;
import ptithcm.entity.ReceiptDetail;

@Service
public class HistoryService {
	private final static Logger log = Logger.getLogger(HistoryService.class);

	@Autowired
	private HistoryDao<History> historyDao;

	public List<History> getAllHistories(History history, Paging paging) {
		log.info("View all histories");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (history != null) {
			if (history.getProduct() != null) {
				if (!StringUtils.isEmpty(history.getProduct().getCategory().getName())) {
					queryStr.append(" and entity.product.category.name like :cateName");
					mapParams.put("cateName", "%" + history.getProduct().getCategory().getName() + "%");
				}
				if (!StringUtils.isEmpty(history.getProduct().getCode())) {
					queryStr.append(" and entity.product.code = :code");
					mapParams.put("code", history.getProduct().getCode());
				}
				if (!StringUtils.isEmpty(history.getProduct().getName())) {
					queryStr.append(" and entity.product.name like :name");
					mapParams.put("name", "%" + history.getProduct().getName() + "%");
				}
			}
			if (!StringUtils.isEmpty(history.getAction())) {
				queryStr.append(" and entity.action like :action");
				mapParams.put("action", "%" + history.getAction() + "%");
			}
			if (history.getType() != 0) {
				queryStr.append(" and entity.type = :type");
				mapParams.put("type", history.getType());
			}
			queryStr.append(" order by 1 DESC");
		}
		return historyDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public void saveHistoryInReceiptDetail(ReceiptDetail receiptDetail, String action) {
		log.info("Save history in receipt detail");
		History history = new History();
		history.setProduct(receiptDetail.getProduct());
		history.setQuantity(receiptDetail.getQuantity());
		history.setType(1);
		history.setPrice(receiptDetail.getPrice());
		history.setAction(action);
		history.setActive(1);
		history.setCreated(new Date());
		history.setUpdated(new Date());
		historyDao.save(history);
	}

	public void saveHistoryInIssueDetail(IssueDetail issueDetail, String action) {
		log.info("Save history in issue detail");
		History history = new History();
		history.setProduct(issueDetail.getProduct());
		history.setQuantity(issueDetail.getQuantity());
		history.setType(2);
		history.setPrice(issueDetail.getPrice());
		history.setAction(action);
		history.setActive(1);
		history.setCreated(new Date());
		history.setUpdated(new Date());
		historyDao.save(history);
	}
}
