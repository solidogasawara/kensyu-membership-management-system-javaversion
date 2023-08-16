package servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.MemberInfoBean;
import model.json.JsonResponseSender;
import model.json.SearchResult;
import model.json.SearchResultSender;
import service.MemberSearchService;

/**
 * Servlet implementation class MemberSearch
 */
@WebServlet("/MemberSearch")
public class MemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/MemberSearch.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		request.getParameterMap().entrySet().forEach(e -> System.out.println("key: " + e.getKey() + " value: " + Arrays.asList(e.getValue())));
		
		switch(action) {
		case "search":
			// 1ページに何件結果を表示するか
			final int LIMIT = 10;
			
			// 性別が男性、女性の両方ともチェックが入っていた場合か、
			// 会員状態が有効、退会の両方ともチェックが入っていた場合は
			// 全件検索する
			String gender = "";
			String memberStatus = "";
			
			gender = request.getParameter("gender");
			memberStatus = request.getParameter("memberStatus");
			
			// 両方ともチェックが入っていた場合、「both」が渡される
			if("both".equals(gender) || "both".equals(memberStatus)) {
				SearchResult result = new SearchResult();
				
				try {
					MemberSearchService service = new MemberSearchService();
					
					String pageNumberStr = request.getParameter("pageNumber");
					int pageNumber = Integer.parseInt(pageNumberStr);
					
					List<MemberInfoBean> members = service.getLimitedMemberInfos(LIMIT, pageNumber);
					int count = service.getMemberInfosCount();
					
					result.setMembers(members);
					result.setResultCount(count);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				JsonResponseSender sender = new SearchResultSender(result, response);
				sender.send();
				
				return;
			}
			
			MemberSearchService service = new MemberSearchService();
			Map<String, String[]> params = request.getParameterMap();
			
			List<MemberInfoBean> members = service.getLimitedMemberInfosBySearch(params, LIMIT);
			int count = service.getMemberInfosCountBySearch(params);
			
			SearchResult result = new SearchResult(members, count);
			
			JsonResponseSender sender = new SearchResultSender(result, response);
			sender.send();
			
			break;
		case "memberDelete":
			break;
		case "csvUpload":
			break;
		case "tableSort":
			
			
			break;
		}
	}
}
