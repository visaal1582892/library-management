package com.library_management.dao;

import java.sql.SQLException;
import java.util.List;

import com.library_management.domain.Member;

public interface MemberDAOInterface {
   public void addMember(Member member);
   List<Member> getAllMembers() throws SQLException;
   public void updateMember();
   
}
