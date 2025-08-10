package com.library_management.services.implementation;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import com.library_management.dao.implementation.MemberDaoImplementation;
import com.library_management.domain.Member;
import com.library_management.exceptions.DatabaseException;
import com.library_management.exceptions.InvalidDetailsException;
import com.library_management.services.MemberServiceInterface;

public class MemberServiceImplementation implements MemberServiceInterface {

	private MemberDaoImplementation memberDAO = new MemberDaoImplementation();

	@Override
	public void addMember(Member member) throws SQLException, DatabaseException, InvalidDetailsException {
		if (member.getMemberName() == null || member.getMemberName().trim().isEmpty()) {
			throw new InvalidDetailsException("Name is required");
		}

		if (member.getMemberMail() == null || !isValidEmail(member.getMemberMail())) {
			throw new InvalidDetailsException("Invalid email format");
		}

		if (member.getMobileNo() == null || !member.getMobileNo().matches("\\d{10}")) {
			throw new InvalidDetailsException("Mobile number must be exactly 10 digits");
		}
		if (member.getMobileNo() == null || !member.getMobileNo().matches("^[6-9]\\d{9}$")) {
			throw new InvalidDetailsException("Invalid mobile number");
		}

		if (member.getGender() == null) {
			throw new InvalidDetailsException("Please select your gender");
		}

		if (member.getMemberAddress() == null || member.getMemberAddress().trim().isEmpty()) {
			throw new InvalidDetailsException("Address is required");
		}

		if (member.getMemberName().length() > 60 || member.getMemberName().length() < 3) {
			throw new InvalidDetailsException("Length Of Member Must Be greater Than 3 And Less Than 60...");
		}

		memberDAO.insertMember(member);
	}

	public void validateUpdateMemberDetails(Member member)
			throws SQLException, DatabaseException, InvalidDetailsException {
		if (member.getMemberName() == null || member.getMemberName().trim().isEmpty()) {
			throw new InvalidDetailsException("Name is required");
		}

		else if (member.getMemberMail() == null || !isValidEmail(member.getMemberMail())) {
			throw new InvalidDetailsException("Invalid email format");
		}

		else if (member.getMobileNo() == null || !member.getMobileNo().matches("\\d{10}")) {
			throw new InvalidDetailsException("Mobile number must be exactly 10 digits");
		}

		else if (member.getGender() == null) {
			throw new InvalidDetailsException("Please select your gender");
		}

		else if (member.getMemberAddress() == null || member.getMemberAddress().trim().isEmpty()) {
			throw new InvalidDetailsException("Address is required");
		}

		else if (member.getMemberName().length() > 60 || member.getMemberName().length() < 3) {
			throw new InvalidDetailsException("Length Of Member Must Be greater Than 3 And Less Than 60...");
		} else {
			Member currentMember = null;
			try {
				currentMember = new MemberDaoImplementation().selectMemberById(member.getMemberId());
			} catch (DatabaseException e) {
				throw new DatabaseException("No Member found");
			}
			if (currentMember == null) {
				throw new InvalidDetailsException("Member Details Not found");
			}

			if (currentMember.equals(member)) {
				throw new InvalidDetailsException("Atleast One Detail Should Be Updated...");
			}

			Member newMember = new Member(member.getMemberId(), member.getMemberName(), member.getMemberMail(),
					member.getMobileNo(), member.getGender(), member.getMemberAddress());
			new MemberDaoImplementation().updateMember(currentMember, newMember);
		}

	}

	@Override
	public List<Member> validateViewAllMembers() throws DatabaseException, SQLException {
		List<Member> memberList = new MemberDaoImplementation().getAllMembers();
		return memberList;

	}

	@Override
	public boolean isValidEmail(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return Pattern.matches(regex, email);
	}

	public boolean deleteMemberById(int memberId) throws DatabaseException {
		return new MemberDaoImplementation().deleteMember(memberId);
	}

}
