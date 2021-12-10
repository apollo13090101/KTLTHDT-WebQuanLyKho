package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.IssueDetailDao;
import ptithcm.entity.IssueDetail;
import ptithcm.entity.Paging;
import ptithcm.entity.Product;
import ptithcm.util.ConstantUtil;

@Service
public class IssueDetailService {
	private final static Logger log = Logger.getLogger(IssueDetailService.class);

	@Autowired
	private IssueDetailDao<IssueDetail> issueDetailDao;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private StockService stockService;

	public List<IssueDetail> getAllIssueDetails(IssueDetail issueDetail, Paging paging) {
		log.info("Get all issue details");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (issueDetail != null) {
			if (issueDetail.getId() != null && issueDetail.getId() != 0) {
				queryStr.append(" and entity.id = :id");
				mapParams.put("id", issueDetail.getId());
			}

			if (issueDetail.getIssue() != null) {
				if (!StringUtils.isEmpty(issueDetail.getIssue().getCode())) {
					queryStr.append(" and entity.issue.code = :issueCode");
					mapParams.put("issueCode", issueDetail.getIssue().getCode());
				}
			}
			if (issueDetail.getProduct() != null) {
				if (!StringUtils.isEmpty(issueDetail.getProduct().getCode())) {
					queryStr.append(" and entity.product.code = :productCode");
					mapParams.put("productCode", issueDetail.getProduct().getCode());
				}
			}
		}
		return issueDetailDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public List<IssueDetail> getIssueDetailByProperty(String property, Object value) {
		log.info("Get issueDetail by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return issueDetailDao.getByProperty(property, value);
	}

	public IssueDetail getIssueDetailById(int id) {
		log.info("Get issue detail by id = " + id);
		return issueDetailDao.getById(IssueDetail.class, id);
	}

	public void saveIssueDetail(IssueDetail issueDetail) throws Exception {
		log.info("Save issueDetail: " + issueDetail.toString());
		boolean checkIssueDetail = issueDetailDao.checkIssueDetail(issueDetail.getIssueCode(),
				issueDetail.getProductCode());
		if (!checkIssueDetail) {
			Product product = new Product();
			product.setCode(issueDetail.getProductCode());
			issueDetail.setProduct(product);
			issueDetail.setActive(1);
			issueDetail.setCreated(new Date());
			issueDetail.setUpdated(new Date());
			issueDetailDao.save(issueDetail);
			historyService.saveHistoryInIssueDetail(issueDetail, ConstantUtil.ACTION_ADD);
			stockService.saveOrUpdateStockInIssueDetail(issueDetail);
		}
	}

	public void updateIssueDetail(IssueDetail issueDetail) throws Exception {
		log.info("Update issueDetail: " + issueDetail.toString());
		boolean checkIssueDetail = issueDetailDao.checkIssueDetail(issueDetail.getIssueCode(),
				issueDetail.getProductCode());
		if (!checkIssueDetail) {
			int origin_qty = issueDetailDao.getById(IssueDetail.class, issueDetail.getId()).getQuantity();
			Product product = new Product();
			product.setCode(issueDetail.getProductCode());

			issueDetail.setProduct(product);
			issueDetail.setUpdated(new Date());

			IssueDetail issueDetail2 = new IssueDetail();
			issueDetail2.setProduct(issueDetail.getProduct());
			issueDetail2.setQuantity(issueDetail.getQuantity() - origin_qty);
			issueDetail2.setPrice(issueDetail.getPrice());

			issueDetailDao.update(issueDetail);
			historyService.saveHistoryInIssueDetail(issueDetail, ConstantUtil.ACTION_EDIT);
			stockService.saveOrUpdateStockInIssueDetail(issueDetail2);
		}
	}

	public void deleteIssueDetail(IssueDetail issueDetail) throws Exception {
		log.info("Delete issueDetail: " + issueDetail.toString());
		issueDetail.setActive(0);
		issueDetail.setUpdated(new Date());
		issueDetailDao.update(issueDetail);
	}
}
