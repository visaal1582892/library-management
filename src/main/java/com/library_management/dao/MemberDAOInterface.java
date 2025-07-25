package com.library_management.dao;

import java.sql.SQLException;
import java.util.List;

import com.library_management.domain.Member;

public interface MemberDAOInterface {
   public void insertMember(Member member) throws SQLException;
   List<Member> getAllMembers() throws SQLException;
   public void updateMember();
   public void deleteBook();
   
}
