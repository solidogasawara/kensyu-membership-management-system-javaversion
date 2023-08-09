package model.json;

import java.util.List;

import bean.MemberInfoBean;

public class SearchResult {
	private List<MemberInfoBean> members;
	private int resultCount;
	
	public SearchResult(List<MemberInfoBean> members, int resultCount) {
		this.members = members;
		this.resultCount = resultCount;
	}
	
	public SearchResult() {
		this.members = null;
		this.resultCount = -1;
	}
	
	public List<MemberInfoBean> getMembers() {
		return members;
	}
	
	public int getResultCount() {
		return resultCount;
	}
	
	public void setMembers(List<MemberInfoBean> members) {
		this.members = members;
	}
	
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
}
