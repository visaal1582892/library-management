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
	public void addMember(Member member) {
//		String query="insert into members values (?,?,?)";
//		Connection con=DBConnection.getConn();
//		PreparedStatement ps=con.prepareStatement(query);
//		ps.setString(1,member.getMemberId());
//		ps.setString(2, member.getName());
//		ps.setInt(3, member.getAge());
//		ps.execute();	
		
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

}
