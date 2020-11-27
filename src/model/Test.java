package model;

import java.util.ArrayList;
import java.util.List;

public class Test {
	private User user;
	private String id;
	private String testTitle;
	private int marks;
	private List<String> question = new ArrayList<String>();
	private List<List<String>> options = new ArrayList<List<String>>();
	private List<Integer> answer = new ArrayList<Integer>();
	private List<TestResult> testResults = new ArrayList<TestResult>();
	

	public Test() {
		super();
	}
	public Test(User user, String id, String testTitle, int marks, List<String> question, List<List<String>> options,
			List<Integer> answer) {
		super();
		this.user = user;
		this.id = id;
		this.testTitle = testTitle;
		this.marks = marks;
		this.question = question;
		this.options = options;
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public List<String> getQuestion() {
		return question;
	}

	public void setQuestion(List<String> question) {
		this.question = question;
	}

	public List<List<String>> getOptions() {
		return options;
	}

	public void setOptions(List<List<String>> options) {
		this.options = options;
	}

	public List<Integer> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Integer> answer) {
		this.answer = answer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<TestResult> getTestResults() {
		return testResults;
	}

	public void setTestResults(TestResult testResult) {
		this.testResults.add(testResult);
	}

	
}
