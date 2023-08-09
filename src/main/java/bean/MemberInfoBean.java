package bean;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import enums.ParamEnums.Gender;
import enums.ParamEnums.MemberStatus;
import model.json.serializer.MemberGenderSerializer;
import model.json.serializer.MemberStatusSerializer;

public class MemberInfoBean {
	private int id;
	private String name;
	private String nameKana;
	private String mail;
	
	@JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
	private Timestamp birthday;
	
	@JsonSerialize(using = MemberGenderSerializer.class)
	private Gender gender;
	
	private String prefecture;
	
	@JsonSerialize(using = MemberStatusSerializer.class)
	private MemberStatus memberStatus;
	
	@JsonIgnore
	private Timestamp createdAt;
	
	@JsonIgnore
	private Timestamp updatedAt;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNameKana() {
		return nameKana;
	}

	public String getMail() {
		return mail;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public Gender getGender() {
		return gender;
	}
	
	public String getPrefecture() {
		return prefecture;
	}

	public MemberStatus getMemberStatus() {
		return memberStatus;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public void setGender(boolean gender) {
		// falseが男性、trueが女性を表す
		
		if(!gender) {
			this.gender = Gender.MALE;
		} else {
			this.gender = Gender.FEMALE;
		}
	}
	
	public void setGender(int gender) {
		// 0が男性、1が女性を表す
		
		if(gender == Gender.MALE.getBitNum()) {
			this.gender = Gender.MALE;
		} else if (gender == Gender.FEMALE.getBitNum()) {
			this.gender = Gender.FEMALE;
		} else {
			this.gender = null;
		}
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public void setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}
	
	public void setMemberStatus(boolean memberStatus) {
		// trueが有効、falseが退会済みを表す
		
		if(memberStatus) {
			this.memberStatus = MemberStatus.ACTIVE;
		} else {
			this.memberStatus = MemberStatus.INACTIVE;
		}
	}
	
	public void setMemberStatus(int memberStatus) {
		// 1が有効、0が退会済みを表す
		
		if(memberStatus == MemberStatus.ACTIVE.getBitNum()) {
			this.memberStatus = MemberStatus.ACTIVE;
		} else if (memberStatus == MemberStatus.INACTIVE.getBitNum()) {
			this.memberStatus = MemberStatus.INACTIVE;
		} else {
			this.memberStatus = null;
		}
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
