package com.moon.pf.dto;

import org.apache.ibatis.type.Alias;

@Alias("Member")
public class Member {
	private int memberIdx;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberNick;
	private String email; 
	private String birthDate;
	private String createDate;
	private int typeSeq;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Member [memberIdx=" + memberIdx + ", memberId=" + memberId + ", memberPw=" + memberPw + ", memberName="
				+ memberName + ", memberNick=" + memberNick + ", email=" + email + ", birthDate=" + birthDate
				+ ", createDate=" + createDate + ", typeSeq=" + typeSeq + "]";
	}

	/**
	 * @return the memberIdx
	 */
	public int getMemberIdx() {
		return memberIdx;
	}

	/**
	 * @param memberIdx the memberIdx to set
	 */
	public void setMemberIdx(int memberIdx) {
		this.memberIdx = memberIdx;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberPw
	 */
	public String getMemberPw() {
		return memberPw;
	}

	/**
	 * @param memberPw the memberPw to set
	 */
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the memberNick
	 */
	public String getMemberNick() {
		return memberNick;
	}

	/**
	 * @param memberNick the memberNick to set
	 */
	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the typeSeq
	 */
	public int getTypeSeq() {
		return typeSeq;
	}

	/**
	 * @param typeSeq the typeSeq to set
	 */
	public void setTypeSeq(int typeSeq) {
		this.typeSeq = typeSeq;
	}
}
