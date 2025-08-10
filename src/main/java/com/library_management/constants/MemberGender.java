package com.library_management.constants;

import java.util.stream.Stream;

public enum MemberGender {

	MALE("Male", "M"), FEMALE("Female", "F");

	private final String displayName;
	private final String dbValue;

	MemberGender(String displayName, String dbValue) {
		this.displayName = displayName;
		this.dbValue = dbValue;
	}

	public String getStringValue() {
		return this.displayName;
	}

	public String getDbValue() {
		return this.dbValue;
	}

	@Override
	public String toString() {
		return this.displayName;
	}

	public static MemberGender getEnumConstant(String value) {
		if (value == null) {
			return null;
		}
		return Stream.of(MemberGender.values())
				.filter(e -> e.displayName.equalsIgnoreCase(value) || e.dbValue.equalsIgnoreCase(value)).findFirst()
				.orElse(null);
	}
}
