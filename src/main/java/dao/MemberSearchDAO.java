package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mysql.cj.util.StringUtils;

import bean.MemberInfoBean;
import bean.SearchParamsBean;
import enums.ParamEnums.Gender;
import enums.ParamEnums.MemberStatus;
import model.DBManager;
import model.namedparameter.NamedParameterStatement;

public class MemberSearchDAO {
	
	private final String TABLE_NAME = "v_customer";
	
	private List<MemberInfoBean> getMemberInfosBySQL(String sql) {
		List<MemberInfoBean> members = null;
		
		try(Connection con = DBManager.getConnection()) {
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			
			members = getMemberInfoListByResultSet(result);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return members;
	}
	
	// ResultSetオブジェクトからデータを取得して、MemberInfoBeanオブジェクトをListに格納して返すメソッド
	private List<MemberInfoBean> getMemberInfoListByResultSet(ResultSet result) throws SQLException {
		List<MemberInfoBean> members = new ArrayList<>();
		
		while(result.next()) {
			MemberInfoBean member = new MemberInfoBean();
			
			int id = result.getInt("id");
			String name = result.getString("name");
			String nameKana = result.getString("name_kana");
			String mail = result.getString("mail");
			Timestamp birthday = result.getTimestamp("birthday");
			boolean gender = result.getBoolean("gender");
			String prefecture = result.getString("prefecture");
			boolean membershipStatus = result.getBoolean("membership_status");
			Timestamp createdAt = result.getTimestamp("created_at");
			Timestamp updatedAt = result.getTimestamp("updated_at");
			
			member.setId(id);
			member.setName(name);
			member.setNameKana(nameKana);
			member.setMail(mail);
			member.setBirthday(birthday);
			member.setGender(gender);
			member.setPrefecture(prefecture);
			member.setMemberStatus(membershipStatus);
			member.setCreatedAt(createdAt);
			member.setUpdatedAt(updatedAt);
			
			members.add(member);
		}
		
		return members;
	}
	
	// 保存されている会員情報の数を取得するメソッド
	public int getMemberInfosCount() {
		int count = -1;
		
		try(Connection con = DBManager.getConnection()) {
			
			String sql = "SELECT COUNT(*) AS count FROM " + TABLE_NAME + " WHERE delete_flag = FALSE";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			
			if(result.next()) {
				count = result.getInt("count");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 検索条件ありで検索した結果が何件か取得するメソッド
	public int getMemberInfosCountBySearch(SearchParamsBean params) {
		int count = -1;
		
		try(Connection con = DBManager.getConnection()) {
			
			StringBuilder sqlSb = new StringBuilder();
            
            NamedParameterStatement npstmt = new NamedParameterStatement();
            
            createSQLBySearchParams(params, sqlSb, npstmt, CreateSQLMode.COUNT_ONLY);
			
            String sql = sqlSb.toString();
            
            npstmt.setConnection(con);
            npstmt.setSql(sql);
            
			PreparedStatement pstmt = npstmt.prepareStatement();
			ResultSet result = pstmt.executeQuery();
			
			if(result.next()) {
				count = result.getInt("count");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	// 全ての会員情報を取得するメソッド
	public List<MemberInfoBean> getAllMemberInfos() {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE delete_flag = FALSE";

		List<MemberInfoBean> member = getMemberInfosBySQL(sql);
		
		return member;
	}
	
	// 検索条件なしで会員情報を取得し、取得するデータ数に制限をかけて取得するメソッド
	public List<MemberInfoBean> getLimitedMemberInfos(int limit, int offset) {
		List<MemberInfoBean> members = null;
		
		try(Connection con = DBManager.getConnection()) {
			NamedParameterStatement npstmt = new NamedParameterStatement();
			StringBuilder sqlSb = new StringBuilder();
			
			sqlSb.append("SELECT c.id, c.name, name_kana, mail, birthday, gender, p.name AS prefecture, membership_status, created_at, updated_at");
			sqlSb.append("  FROM " + TABLE_NAME + " AS c");
			sqlSb.append("  JOIN v_prefecture AS p");
	        sqlSb.append("    ON c.prefecture_id = p.id");
	        sqlSb.append(" WHERE delete_flag = FALSE ");
						
			// LIMIT - OFFSET文を使用して、取得するデータ数を制限する
            sqlSb.append("ORDER BY c.id ");
            sqlSb.append("LIMIT @limit ");
            sqlSb.append("OFFSET @offset");
			
            // limitとoffsetを設定する
            npstmt.parameterAdd("@limit", limit);
            npstmt.parameterAdd("@offset", offset);
            
			String sql = sqlSb.toString();
			
			npstmt.setSql(sql);
			npstmt.setConnection(con);
			
			PreparedStatement pstmt = npstmt.prepareStatement();
			
			ResultSet result = pstmt.executeQuery();
			
			members = getMemberInfoListByResultSet(result);
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return members;
	}
	
	// 検索条件付きで会員情報を取得するメソッド
	public List<MemberInfoBean> getMemberInfosBySearch(SearchParamsBean params) {
		List<MemberInfoBean> members = null;
		
		try(Connection con = DBManager.getConnection()) {
			NamedParameterStatement npstmt = new NamedParameterStatement();
			StringBuilder sqlSb = new StringBuilder();
			
			createSQLBySearchParams(params, sqlSb, npstmt, CreateSQLMode.DATA_ONLY);
			
			String sql = sqlSb.toString();
			
			npstmt.setSql(sql);
			npstmt.setConnection(con);
			
			PreparedStatement pstmt = npstmt.prepareStatement();
			
			System.out.println(pstmt.toString());
			
			ResultSet result = pstmt.executeQuery();
			
			members = getMemberInfoListByResultSet(result);
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return members;
	}
	
	// 検索条件付きで会員情報を取得し、取得するデータ数に制限をかけて取得するメソッド
	public List<MemberInfoBean> getLimitedMemberInfosBySearch(SearchParamsBean params, int limit, int offset) {
		List<MemberInfoBean> members = null;
		
		try(Connection con = DBManager.getConnection()) {
			NamedParameterStatement npstmt = new NamedParameterStatement();
			StringBuilder sqlSb = new StringBuilder();
			
			createSQLBySearchParams(params, sqlSb, npstmt, CreateSQLMode.DATA_ONLY);
			
			// LIMIT - OFFSET文を使用して、取得するデータ数を制限する
            sqlSb.append("ORDER BY c.id ");
            sqlSb.append("LIMIT @limit ");
            sqlSb.append("OFFSET @offset");
			
            // limitとoffsetを設定する
            npstmt.parameterAdd("@limit", limit);
            npstmt.parameterAdd("@offset", offset);
            
			String sql = sqlSb.toString();
			
			npstmt.setSql(sql);
			npstmt.setConnection(con);
			
			PreparedStatement pstmt = npstmt.prepareStatement();
			
			System.out.println(pstmt);
			
			ResultSet result = pstmt.executeQuery();
			
			members = getMemberInfoListByResultSet(result);
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return members;
	}
	
	private enum CreateSQLMode {
		COUNT_ONLY,
		DATA_ONLY
	}
	
	// SearchParamsBeanオブジェクトからSQL文を作成するメソッド
	// 検索条件を表すSearchParamsBeanオブジェクトとSQL文を保存するStringBuilderとNamedParameterStatementオブジェクトを引数に取る
	// NamedParameterStatementオブジェクトを利用して名前付きパラメータに値をセットする
	private void createSQLBySearchParams(SearchParamsBean params, StringBuilder sb, NamedParameterStatement npstmt, CreateSQLMode mode) {
		// SearchParamsBeanオブジェクトから検索条件を取り出し、変数に代入する
		int id = params.getId();
		String email = params.getEmail();
        String name = params.getName();
        String nameKana = params.getNameKana();
        Timestamp birthStart = params.getBirthStart();
        Timestamp birthEnd = params.getBirthEnd();
        int prefectureId = params.getPrefectureId();
        Gender gender = null;
        MemberStatus memberStatus = null;
        
        if(params.getGender() != null) {
        	gender = params.getGender()[0];
        }
        
        if(params.getMemberStatus() != null) {
        	memberStatus = params.getMemberStatus()[0];
        }

        // 名前検索はあいまい検索とOR検索が行える
        // あいまい検索は「*」、OR検索には「,」を利用する
        // この配列には、「*」を「%」に置換し「,」を区切り文字として配列にしたものを格納する
        String[] splitedNames = null;
        
        // nameがnullでないかチェック
        // nullならあいまい検索、OR検索のための処理を実行しない
        if(name != null) {
            splitedNames = asteriskToPercentAndSplitByComma(name);
        }
		
    	// 基準のSQL文
        switch(mode) {
		case COUNT_ONLY:
			sb.append("SELECT COUNT(*) AS count FROM " + TABLE_NAME + " AS c");
			break;
		case DATA_ONLY:
			sb.append("SELECT c.id, c.name, name_kana, mail, birthday, gender, p.name AS prefecture, membership_status, created_at, updated_at");
			sb.append("  FROM " + TABLE_NAME + " AS c");
			break;
        }
        
        sb.append("  JOIN v_prefecture AS p");
        sb.append("    ON c.prefecture_id = p.id");
        sb.append(" WHERE delete_flag = FALSE");	
    
        
        // paramsの中身を見てSQL文を組み立てる
        
        // 検索条件を格納するList
        List<String> conditions = new ArrayList<>();
        
        // idが-1以外なら、検索入力欄に入力されたとして検索条件に追加する
        if(id != -1) {        	
        	conditions.add("c.id = @id");
        	npstmt.parameterAdd("@id", id);
        }
        
        // nameがnull以外なら、検索入力欄に入力されたとして検索条件に追加する
        if(name != null) {
            // splitedNamesの要素数だけループする
            for (int i = 0; i < splitedNames.length; i++) {
                String sName = splitedNames[i];

                // 「田中 太郎」と「田中太郎」で検索できるようにする
                // 変数名は一意にしなくてはいけないので、インデックスを利用して重複しないようにする
                int doubleIdx = (i + 1) * 2;

                conditions.add("c.name LIKE @name" + (doubleIdx - 1));
                conditions.add("REPLACE(c.name, ' ', '') LIKE @name" + doubleIdx);

                npstmt.parameterAdd("@name" + (doubleIdx - 1), sName);
                npstmt.parameterAdd("@name" + doubleIdx, sName);
            }
        }
        
        // nameKanaがnull以外なら、検索入力欄に入力されたとして検索条件に追加する
        if (nameKana != null) {
            conditions.add("name_kana LIKE @nameKana");
            npstmt.parameterAdd("@nameKana", "%" + nameKana + "%");
        }

        // emailがnull以外なら、検索入力欄に入力されたとして検索条件に追加する
        if(email != null) {
        	conditions.add("mail = @email");
            npstmt.parameterAdd("@email", email);
        }
        
        // birthStartとbirthEndのどちらかがnull以外なら、検索入力欄に入力されたとして検索条件に追加する
        if(birthStart != null || birthEnd != null) {
        	// birthStart、birthEndがnullなら現在時刻を代入する
        	if(birthStart == null) {
        		birthStart = new Timestamp(System.currentTimeMillis());
        	} else if (birthEnd == null) {
        		birthEnd = new Timestamp(System.currentTimeMillis());
        	}
        	
        	conditions.add("birthday BETWEEN @birthStart AND @birthEnd");
            npstmt.parameterAdd("@birthStart", birthStart);
            npstmt.parameterAdd("@birthEnd", birthEnd);
        }
        
        // genderがnull以外なら、検索入力欄に入力されたとして検索条件に追加する
        if (gender != null) {
            conditions.add("gender = @gender");
            npstmt.parameterAdd("@gender", gender.getBitNum());
        }

        // prefectureIdが-1以外なら、検索入力欄に入力されたとして検索条件に追加する
        if(prefectureId != -1) {
        	conditions.add("prefecture_id = @prefecture");
            npstmt.parameterAdd("@prefecture", prefectureId);	
        }

        // memberStatusがnull以外なら、検索入力欄に入力されたとして検索条件に追加する
        if (memberStatus != null) {
            // 検索条件(membership_status)
            conditions.add("membership_status = @memberStatus");
            npstmt.parameterAdd("@memberStatus", memberStatus.getBitNum());
        }

     // conditionsに追加された条件があればANDで結合してSQLに追加
      if (!conditions.isEmpty()) {
          sb.append(" AND ( ");
          sb.append(String.join(" OR ", conditions));
          sb.append(" ) ");
      }
	}
	
	private String[] asteriskToPercentAndSplitByComma(String str) {
		// 名前のあいまい検索とOR検索
        // ◇あいまい検索
        //  - 「田中*」、「*太郎」、「*中太*」のように名前検索欄にアスタリスクを記述するとあいまい検索を行うことができる
        //  - 上の例の場合順番に、前方一致、後方一致、部分一致で検索を行う
        //
        // ◇OR検索
        //  - 「田中*,佐藤*」のように名前検索欄にカンマを記述するとカンマ区切りでOR検索される
        //  - この場合、「田中」か「佐藤」から始まる名前のユーザーを検索する
		
		String[] splitedNames = null;

        // 「%」、「_」をエスケープ処理する
        String escapedName = str.replace("%", "\\%").replace("_", "\\_");

        // アスタリスクを%に置換する
        String replacedName = escapedName.replace("*", "%");

        // カンマ区切りで配列にする
        // 「田中,,佐藤」=>「田中」、「」、「佐藤」のようにカンマ区切りにした時に空文字になる場合は無視して配列の中に入れない
        List<String> splitedByComma = Arrays.asList(replacedName.split(","));
        splitedNames = splitedByComma.stream()
        		.filter(n -> !StringUtils.isNullOrEmpty(n))
        		.collect(Collectors.toList())
        		.toArray(new String[0]);
        
        return splitedNames;
	}
}
