package com.library_management.services.implementation;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import com.library_management.dao.implementation.BookDAOImplementation;
import com.library_management.dao.implementation.MemberDAOImplementation;
import com.library_management.domain.Book;
import com.library_management.domain.Member;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.MemberServiceInterface;
import com.library_management.services.implementation.*;

public class MemberServiceImplementation implements MemberServiceInterface{

    private MemberDAOImplementation memberDAO = new MemberDAOImplementation();
    public void addMember(Member member) throws SQLException {
        if (member.getMemberName()==null || member.getMemberName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (member.getMemberMail()==null || !isValidEmail(member.getMemberMail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (member.getMobileNo()==null || !member.getMobileNo().matches("\\d{10}")) {
            throw new IllegalArgumentException("Mobile number must be exactly 10 digits");
        }

        if (member.getGender()==null || !(member.getGender().equalsIgnoreCase("Male")||
        		member.getGender().equalsIgnoreCase("Female"))) {
            throw new IllegalArgumentException("Please select your gender");
        }

        if (member.getMemberAddress()==null || member.getMemberAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        String genderCode = member.getGender().equalsIgnoreCase("Male") ? "M" : "F";
        member.setGender(genderCode);

        memberDAO.insertMember(member);
    }
    
    public void validateUpdateMemberDetails(Member member) throws SQLException, DatabaseException, InvalidDetailsException
    {
    	if(member.getMemberName().trim().equals("") || member.getMemberAddress().trim().equals("") || member.getMemberMail().trim().equals("")|| member.getMobileNo().trim().equals("")) {
			throw new InvalidDetailsException("All Details Must Be Given...");
		}
		else if(member.getMemberName().length()>255) {
			throw new InvalidDetailsException("Lengths Of Fields Exceeded Max Length...");
		}
		else {
			Member currentMember=null;
			try {
				currentMember=new MemberDAOImplementation().selectMemberById(member.getMemberId());
			}catch(DatabaseException e) {
				throw new DatabaseException("No Member found");
			}
			if(currentMember==null) {
				throw new InvalidDetailsException("Member Details Not found");
			}
			
//			Checking if atleast one detail is changed or not
			if(currentMember.equals(member)) {
				throw new InvalidDetailsException("Atleast One Detail Should Be Updated...");
			}
			
//			Creating a new Book obj
			Member newMember=new Member(member.getMemberId(),member.getMemberName(),member.getMemberMail(),member.getMobileNo(),member.getGender(),member.getMemberAddress());
			new MemberDAOImplementation().updateMember(currentMember, newMember);
		}
    	
    }
    
    @Override
	public List<Member> validateViewAllMembers() throws DatabaseException, SQLException {
		List<Member> memberList=new MemberDAOImplementation().getAllMembers();
		return memberList;
		
	}

   public boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }
   public boolean deleteMemberById(int memberId) throws DatabaseException {
	    return new MemberDAOImplementation().deleteMember(memberId);
	}

}
