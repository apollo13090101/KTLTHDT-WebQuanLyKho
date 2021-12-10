package ptithcm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ptithcm.dao.IssueDao;
import ptithcm.entity.Issue;
import ptithcm.entity.Paging;

@Service
public class IssueService {
	private final static Logger log = Logger.getLogger(IssueService.class);

	@Autowired
	private IssueDao<Issue> issueDao;

	public List<Issue> getAllIssues(Issue issue, Paging paging) {
		log.info("Get all issues");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (issue != null) {
			if (issue.getCode() != null && !StringUtils.isEmpty(issue.getCode())) {
				queryStr.append(" and entity.code = :code");
				mapParams.put("code", issue.getCode());
			}
			if (issue.getCustomer() != null && !StringUtils.isEmpty(issue.getCustomer())) {
				queryStr.append(" and entity.customer like :customer");
				mapParams.put("customer", "%" + issue.getCustomer() + "%");
			}
		}
		return issueDao.getAll(queryStr.toString(), mapParams, paging);
	}

	public List<Issue> getIssueByProperty(String property, Object value) {
		log.info("Get issue by property");
		log.info("Property = " + property + " and value = " + value.toString());
		return issueDao.getByProperty(property, value);
	}

	public Issue getIssueByCode(String code) {
		log.info("Get issue by code = " + code);
		return issueDao.getByCode(Issue.class, code);
	}

	public void saveIssue(Issue issue) throws Exception {
		log.info("Save issue: " + issue.toString());
		issue.setActive(1);
		issue.setCreated(new Date());
		issue.setUpdated(new Date());
		issueDao.save(issue);
	}

	public void updateIssue(Issue issue) throws Exception {
		log.info("Update issue: " + issue.toString());
		issue.setUpdated(new Date());
		issueDao.update(issue);
	}

	public void deleteIssue(Issue issue) throws Exception {
		log.info("Delete issue: " + issue.toString());
		issue.setActive(0);
		issue.setUpdated(new Date());
		issueDao.update(issue);
	}
}
