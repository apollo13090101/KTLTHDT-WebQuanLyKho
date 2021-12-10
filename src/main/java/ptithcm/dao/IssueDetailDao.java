package ptithcm.dao;

public interface IssueDetailDao<T> extends BaseDao<T> {
	public boolean checkIssueDetail(String iss_code, String prod_code);
}
