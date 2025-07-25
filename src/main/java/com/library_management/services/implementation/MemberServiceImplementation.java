package com.library_management.services.implementation;

import java.sql.SQLException;
import java.util.regex.Pattern;

import com.library_management.dao.implementation.MemberDAOImplementation;
import com.library_management.domain.Member;
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

   public boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }
}
