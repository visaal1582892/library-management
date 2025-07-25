package com.library_management.services;

import java.sql.SQLException;

import com.library_management.domain.Member;

public interface MemberServiceInterface {
	public void addMember(Member member) throws SQLException;
	public boolean isValidEmail(String email)throws SQLException;

}
