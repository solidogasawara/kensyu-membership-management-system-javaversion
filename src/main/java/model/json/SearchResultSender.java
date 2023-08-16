package model.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import bean.MemberInfoBean;

public class SearchResultSender implements JsonResponseSender {
	private SearchResult result;
	private HttpServletResponse response;

	public SearchResultSender(SearchResult result, HttpServletResponse response) {
		this.result = result;
		this.response = response;
	}
	
	@Override
	public void send() {
		PrintWriter out = null;
		
		List<MemberInfoBean> members = result.getMembers();
		
		// membersがnullの場合、取得処理が正常に完了しなかったことを表す
		// ステータスコード500をクライアント側に返す
		if(members == null) {
			try {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				
				out = response.getWriter();
				
				out.print("");
				
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.close();
			}
			
			return;
		}
		
		ResultJsonCreator<SearchResult> creator = new ResultJsonCreator<>(result);
		
		try {
			String json = creator.create();
			response.setContentType("application/json;charset=UTF-8");
			
			out = response.getWriter();
			
			out.print(json);
			
			out.close();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
