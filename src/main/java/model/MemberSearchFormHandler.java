package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.mysql.cj.util.StringUtils;

import bean.SearchParamsBean;
import enums.ParamEnums.Gender;
import enums.ParamEnums.MemberStatus;

public class MemberSearchFormHandler {
	
	/*
	 * 入力された検索条件をBeanクラスのフィールドに格納するメソッド
	 * */
	public SearchParamsBean populateBeanFromForm(Map<String, String[]> search) {
		SearchParamsBean bean = new SearchParamsBean();
		
		for(String key : search.keySet()) {
			switch(key) {
			case "id":
				String idStr = search.get("id")[0];
				
				if(!StringUtils.isNullOrEmpty(idStr)) {
					// int型に変換する
					// 変換に失敗したらsetしない
					try {
						int id = Integer.parseInt(idStr);
						bean.setId(id);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}	
				}
				
				break;
			case "email":
				String email = search.get("email")[0];
				
				if(!StringUtils.isNullOrEmpty(email)) {
					bean.setEmail(email);	
				}
				
				break;
			case "name":
				String name = search.get("name")[0];
				
				if(!StringUtils.isNullOrEmpty(name)) {
					bean.setName(name);	
				}
				
				break;
			case "nameKana":
				String nameKana = search.get("nameKana")[0];
				
				if(!StringUtils.isNullOrEmpty(nameKana)) {
					bean.setNameKana(nameKana);	
				}
				
				break;
			case "birthStart":
				String birthStartStr = search.get("birthStart")[0];
				
				if(!StringUtils.isNullOrEmpty(birthStartStr)) {
					// TimeStamp型に変換し、Beanクラスのフィールドにsetする
					// 処理中に例外が発生したらsetしない
					try {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						 
						LocalDate ld = LocalDate.parse(birthStartStr, dtf);
						LocalDateTime ldt = ld.atTime(LocalTime.MIN);
						 
						Timestamp ts = Timestamp.valueOf(ldt);
						 
						bean.setBirthStart(ts);	
					} catch(Exception e) {
						e.printStackTrace();
					}	
				}
				
				break;
			case "birthEnd":
				String birthEndStr = search.get("birthEnd")[0];
				
				if(!StringUtils.isNullOrEmpty(birthEndStr)) {
					// TimeStamp型に変換し、Beanクラスのフィールドにsetする
					// 処理中に例外が発生したらsetしない
					try {
						DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						 
						LocalDate ld = LocalDate.parse(birthEndStr, dtf);
						LocalDateTime ldt = ld.atTime(LocalTime.MIN);
						 
						Timestamp ts = Timestamp.valueOf(ldt);
						 
						bean.setBirthEnd(ts);	
					} catch(Exception e) {
						e.printStackTrace();
					}	
				}
				
				break;
			case "prefectureId":
				String prefectureIdStr = search.get("prefectureId")[0];
				
				if(!StringUtils.isNullOrEmpty(prefectureIdStr)) {
					// int型に変換する
					try {
						int prefectureId = Integer.parseInt(prefectureIdStr);
						
						// 1～47の範囲内か調べる
						// 範囲外ならsetしない
						if(prefectureId >= 1 && prefectureId <= 47) {
							bean.setPrefectureId(prefectureId);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}	
				}
				
				break;
			case "gender":
				String genderStr = search.get("gender")[0];
				
				if(!StringUtils.isNullOrEmpty(genderStr)) {
					// 男性は1、女性は2として渡される
					String male = "1";
					String female = "2";
					
					Gender[] gender = null;
					
					// 男性と女性の両方がチェックされた場合「both」が渡される
					if("both".equals(genderStr)) {
						gender = new Gender[] {
							Gender.MALE,
							Gender.FEMALE
						};
					} else if (male.equals(genderStr)) {
						gender = new Gender[] { Gender.MALE };
					} else if (female.equals(genderStr)) {
						gender = new Gender[] { Gender.FEMALE };
					}
					
					bean.setGender(gender);	
				}
				
				break;
			case "memberStatus":
				String memberStatusStr = search.get("memberStatus")[0];
				
				if(!StringUtils.isNullOrEmpty(memberStatusStr)) {
					// 有効は1、退会は2として渡される
					String active = "1";
					String inactive = "2";
					
					MemberStatus[] memberStatus = null;
					
					// 男性と女性の両方がチェックされた場合「both」が渡される
					if("both".equals(memberStatusStr)) {
						memberStatus = new MemberStatus[] {
							MemberStatus.ACTIVE,
							MemberStatus.INACTIVE
						};
					} else if (active.equals(memberStatusStr)) {
						memberStatus = new MemberStatus[] { MemberStatus.ACTIVE };
					} else if (inactive.equals(memberStatusStr)) {
						memberStatus = new MemberStatus[] { MemberStatus.INACTIVE };
					}
					
					bean.setMemberStatus(memberStatus);
				}
						
				break;
			}
		}
		
		return bean;
	}
}
