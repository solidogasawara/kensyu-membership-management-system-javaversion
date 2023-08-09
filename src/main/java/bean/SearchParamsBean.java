package bean;

import java.sql.Timestamp;

import enums.ParamEnums.Gender;
import enums.ParamEnums.MemberStatus;

/*
 * 検索条件を格納するクラス
 * */
public class SearchParamsBean {
	private int id;
	private String email;
	private String name;
	private String nameKana;
	private Timestamp birthStart;
	private Timestamp birthEnd;
	private int prefectureId;
	private Gender[] gender;
	private MemberStatus[] memberStatus;
	
	public SearchParamsBean() {
		this.id = -1;
		this.email = null;
		this.name = null;
		this.nameKana = null;
		this.birthStart = null;
		this.birthEnd = null;
		this.prefectureId = -1;
		this.gender = null;
		this.memberStatus = null;
	}
	
	public SearchParamsBean(int id, String email, String name, String nameKana, Timestamp birthStart, Timestamp birthEnd,
			int prefectureId, Gender[] gender, MemberStatus[] memberStatus) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.nameKana = nameKana;
		this.birthStart = birthStart;
		this.birthEnd = birthEnd;
		this.prefectureId = prefectureId;
		this.gender = gender;
		this.memberStatus = memberStatus;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getNameKana() {
		return nameKana;
	}

	public Timestamp getBirthStart() {
		return birthStart;
	}

	public Timestamp getBirthEnd() {
		return birthEnd;
	}

	public int getPrefectureId() {
		return prefectureId;
	}

	public Gender[] getGender() {
		return gender;
	}

	public MemberStatus[] getMemberStatus() {
		return memberStatus;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}

	public void setBirthStart(Timestamp birthStart) {
		this.birthStart = birthStart;
	}

	public void setBirthEnd(Timestamp birthEnd) {
		this.birthEnd = birthEnd;
	}

	public void setPrefectureId(int prefectureId) {
		this.prefectureId = prefectureId;
	}

	public void setGender(Gender[] gender) {
		this.gender = gender;
	}

	public void setMemberStatus(MemberStatus[] memberStatus) {
		this.memberStatus = memberStatus;
	}
}
