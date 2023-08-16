package service;

import java.util.List;
import java.util.Map;

import bean.MemberInfoBean;
import bean.SearchParamsBean;
import dao.MemberSearchDAO;
import model.handler.MemberSearchFormHandler;

public class MemberSearchService {
	public int getMemberInfosCount() {
		MemberSearchDAO dao = new MemberSearchDAO();
		
		int count = dao.getMemberInfosCount();
		
		return count;
	}
	
	public int getMemberInfosCountBySearch(Map<String, String[]> map) {
		MemberSearchDAO dao = new MemberSearchDAO();
		
		MemberSearchFormHandler handler = new MemberSearchFormHandler();
		SearchParamsBean params = handler.populateBeanFromForm(map);
		
		int count = dao.getMemberInfosCountBySearch(params);
		
		return count;
	}
	
	public List<MemberInfoBean> getAllMemberInfos() {
		MemberSearchDAO dao = new MemberSearchDAO();
		
		List<MemberInfoBean> members = dao.getAllMemberInfos();
		
		return members;
	}
	
	public List<MemberInfoBean> getLimitedMemberInfos(int limit, int pageNumber) {
		MemberSearchDAO dao = new MemberSearchDAO();
		
		int offset = calcurateOffset(pageNumber, limit);
		
		List<MemberInfoBean> members = dao.getLimitedMemberInfos(limit, offset);
		
		return members;
	}
	
	public List<MemberInfoBean> getMemberInfosBySearch(Map<String, String[]> map) {
		MemberSearchDAO dao = new MemberSearchDAO();
		MemberSearchFormHandler handler = new MemberSearchFormHandler();
		
		// Mapを渡してBean化する
		SearchParamsBean params = handler.populateBeanFromForm(map);
		
		List<MemberInfoBean> members = dao.getMemberInfosBySearch(params);
		
		return members;
	}
	
	public List<MemberInfoBean> getLimitedMemberInfosBySearch(Map<String, String[]> map, int limit) {
		MemberSearchDAO dao = new MemberSearchDAO();
		MemberSearchFormHandler handler = new MemberSearchFormHandler();
		
		// Mapを渡してBean化する
		SearchParamsBean params = handler.populateBeanFromForm(map);
		
		List<MemberInfoBean> members = null;
		
		try {
			// 現在表示しているページの番号
			int pageNumber = Integer.parseInt(map.get("pageNumber")[0]);
			
			int offset = calcurateOffset(pageNumber, limit);
			
			members = dao.getLimitedMemberInfosBySearch(params, limit, offset);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		return members;
	}
	
	private int calcurateOffset(int pageNumber, int limit) {
		// offsetに指定する値は、現在表示しているのが何ページ目なのかで変化させたい
        // 1ページ目 -> offset = 0, 2ページ目 -> offset = 10
        // つまり、(ページ番号 - 1) * nextCountを計算してoffsetを出す
		int offset = (pageNumber - 1) * limit;
		
		return offset;
	}
}
