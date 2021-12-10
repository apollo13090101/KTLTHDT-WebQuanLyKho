package ptithcm.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.Issue;

@Repository
@Transactional(rollbackFor = Exception.class)
public class IssueDaoImpl extends BaseDaoImpl<Issue> implements IssueDao<Issue> {

}
