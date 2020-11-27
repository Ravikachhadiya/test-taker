package service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.Test;
import model.TestResult;
import model.User;
import dao.TestDAO;
import dao.TestResultDAO;
import utility.ConnectionManager;

public class TestGiver {

	private static final String TestDAO = null;

	public void giveATest(Test test,User user, int userid) throws ClassNotFoundException, SQLException, IOException {
		
		Scanner sc = new Scanner(System.in);
		int sum = 0;
		
		System.out.println("\n" +test.getTestTitle() + "               Total Marks : " + test.getMarks() ); 
		System.out.println("\nInstruction : \n-> All questions are MCQs.\n-> Enter option number of the answer instead of answer."
				+ "\n-> Each question gives you " + (test.getMarks() / test.getAnswer().size()) + " marks for correct answer." );
		
		System.out.print("\nFor test start enter 1 : ");
		int start = sc.nextInt();
		
		if(start == 1) {
			System.out.println("\n------------------ Test Started Now ------------------\n");
			
			Iterator questions = test.getQuestion().iterator();
			Iterator<List<String>> options = test.getOptions().iterator();
			Iterator<List<String>> optionsAnswerCheck = test.getOptions().iterator();
			Iterator answers = test.getAnswer().iterator();		
			
			int i = 1;
			while(questions.hasNext()) {
				System.out.println("\n" +  (i++) + ") " + questions.next());
				int j = 1;
				
				Iterator option = options.next().iterator();
				while(option.hasNext()) {
					System.out.println("" +  (j++) + "] " + option.next());
				}
				
				System.out.print("\nAnswer : ");
				int userAnswer = sc.nextInt();
				
				ConnectionManager connectionManager = new ConnectionManager();
				String sql = "SELECT options FROM options WHERE id = ?";
				
				PreparedStatement st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1 , (int) answers.next());
				
				ResultSet rsOption = st.executeQuery();
				connectionManager.getConnection().close();
				
				rsOption.next();
				Iterator optionAnswer = optionsAnswerCheck.next().iterator();
				int index = 0;
				while(optionAnswer.hasNext()) {
					index++;
					if(rsOption.getString("options").toString().equalsIgnoreCase(optionAnswer.next().toString())){
						break;
					}
				}
				
				System.out.println("user answer : " + userAnswer + "	server answer : " + index);
				if(userAnswer == index) {
					sum += (test.getMarks() / test.getAnswer().size());
				}
			}
			
			TestResult testResult = new TestResult(user.getUserName(), user.getEmailId(), sum, test.getMarks(), LocalDateTime.now());
			test.setTestResults(testResult);
			
			TestResultDAO testResultDAO = new TestResultDAO();
			testResultDAO.addTestResult(Integer.parseInt(test.getId()), sum, userid, LocalDate.now());
			
			List<Integer> marks = new ArrayList<Integer>();
			marks.add(sum);
			marks.add(test.getMarks());
			
			user.setTestMarks(test, marks);
			
			System.out.println("\n ====================> YOUR RESULT : " + sum + "/" + test.getMarks() + " <====================");
		}
		else {
			System.out.println("\n       --- X -- Invalid Inputs! Please Try Again -- X ---\n");
		}
		
	}
	
	public Test getTest() throws NumberFormatException, ClassNotFoundException, SQLException, IOException {
		
		System.out.println("\n--------------- Select your test ---------------\n");
		System.out.print("Enter your test code : ");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();
		
		TestDAO testDAO = new TestDAO();
		Test test = testDAO.getTest(Integer.parseInt(id));
		return test;
	}
}
