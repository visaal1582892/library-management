package com.library_management.domain;

import java.util.Objects;

import com.library_management.constants.MemberGender;

public class Member {
	private int memberId;
	private String memberName;
	private String memberMail;
	private String mobileNo;
	private MemberGender gender;
	private String memberAddress;

	public Member() {
	}

	public Member(int memberId, String memberName, String memberMail, String mobileNo, MemberGender gender,
			String memberAddress) {
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberMail = memberMail;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.memberAddress = memberAddress;
	}

	public Member(String memberName, String memberMail, String mobileNo, MemberGender gender, String memberAddress) {
		this.memberName = memberName;
		this.memberMail = memberMail;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.memberAddress = memberAddress;
	}

	public Member(String string, String string2, String string3, String string4, String string5) {
		// TODO Auto-generated constructor stub
	}

	public Member(int generatedId, String string, String string2, String string3, String string4, String string5) {
		// TODO Auto-generated constructor stub
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberMail() {
		return memberMail;
	}

	public void setMemberMail(String memberMail) {
		this.memberMail = memberMail;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public MemberGender getGender() {
		return gender;
	}

	public void setGender(MemberGender gender) {
		this.gender = gender;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberName=" + memberName + ", memberMail=" + memberMail
				+ ", mobileNo=" + mobileNo + ", gender=" + gender + ", memberAddress=" + memberAddress + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Member other = (Member) obj;
		return memberId == other.memberId && Objects.equals(memberName, other.memberName)
				&& Objects.equals(memberMail, other.memberMail) && Objects.equals(mobileNo, other.mobileNo)
				&& gender == other.gender && Objects.equals(memberAddress, other.memberAddress);
	}

}
