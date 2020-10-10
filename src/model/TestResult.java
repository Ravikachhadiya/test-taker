package model;

import java.time.LocalDateTime;

public class TestResult {
	private String userName;
	private String userEmail;
	private int obtainnMarks;
	private int testMarks;
	private LocalDateTime date;
	
	public TestResult(String userName, String userEmail, int obtainnMarks, int testMarks, LocalDateTime date) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.obtainnMarks = obtainnMarks;
		this.testMarks = testMarks;
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getObtainnMarks() {
		return obtainnMarks;
	}

	public void setObtainnMarks(int obtainnMarks) {
		this.obtainnMarks = obtainnMarks;
	}

	public int getTestMarks() {
		return testMarks;
	}

	public void setTestMarks(int testMarks) {
		this.testMarks = testMarks;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	
}
