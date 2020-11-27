package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class User {
	private String emailId;
	private String userName;
	private String password;
	private boolean userType;
	private List<Test> testMake = new ArrayList<Test>();
	private Map<Test, List<Integer>> testMarks = new LinkedHashMap<Test, List<Integer>>();

	
	public User() {
		super();
	}
	public User(String emailId, String userName, String password, boolean userType) {
		super();
		this.emailId = emailId;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}
		
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isUserType() {
		return userType;
	}
	public void setUserType(boolean userType) {
		this.userType = userType;
	}

	public List<Test> getTestMake() {
		return testMake;
	}


	public void setTestMake(Test testMake) {
		this.testMake.add(testMake);
	}


	public Map<Test, List<Integer>> getTestMarks() {
		if(!this.userType)
			return testMarks;
		else
			return null;
	}

	public void setTestMarks(Test test, List<Integer> marks) {
		if(!this.userType)
		this.testMarks.put(test, marks);
	}
}
