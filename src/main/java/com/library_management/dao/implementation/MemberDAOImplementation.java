package com.library_management.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.library_management.dao.MemberDAOInterface;
import com.library_management.domain.Member;
import com.library_management.utilities.DBConnection;



public class MemberDAOImplementation implements MemberDAOInterface {

	@Override

	public void insertMember(Member member) throws SQLException {
		String query="insert into members values (?,?,?,?,?)";
		Connection con=DBConnection.getConn();
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1,member.getMemberName());
		ps.setString(2,member.getMemberMail());
		ps.setString(3, member.getMobileNo());
		ps.setString(4,member.getGender());
		ps.setString(5, member.getMemberAddress());
		ps.execute();	

	}

	@Override
	public List<Member> getAllMembers() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMember() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBook() {
		// TODO Auto-generated method stub
		
	}

}
