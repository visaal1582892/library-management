package com.library_management.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library_management.dao.MemberDAOInterface;
import com.library_management.domain.Member;
import com.library_management.utilities.DBConnection;



public class MemberDAOImplementation implements MemberDAOInterface {

	@Override

	public void insertMember(Member member) throws SQLException {
		String query = "insert into members (name, email, mobile, gender, address) VALUES (?, ?, ?, ?, ?)";
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
	    List<Member> members = new ArrayList<>();
	    String query = "SELECT * FROM members";
	    Connection con = DBConnection.getConn();
	    PreparedStatement ps = con.prepareStatement(query);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        int id = rs.getInt("member_id"); // Adjust based on DB column name
	        String name = rs.getString("member_name");
	        String email = rs.getString("member_email");
	        String mobile = rs.getString("mobile_no");
	        String gender = rs.getString("gender");
	        String address = rs.getString("member_address");

	        Member member = new Member(id, name, email, mobile, gender, address);
	        members.add(member);
	    }

	    return members;
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
