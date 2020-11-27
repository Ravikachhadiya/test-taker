package service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import dao.TestDAO;
import model.Test;
import model.TestResult;
import model.User;
import utility.ConnectionManager;

public class UpdateTest {
	Scanner sc = new Scanner(System.in);
	
	String id;
	String testTitle;
	int marks;
	List<String> question = new ArrayList<String>();
	List<List<String>> options = new ArrayList<List<String>>();
	List<Integer> answer = new ArrayList<Integer>();
	
	Test test = null;
	
	public void update(User user, int userid) throws ClassNotFoundException, SQLException, IOException {
		
		boolean exitFlag = false;
		
		
		
		System.out.println("\n------------------------- MODIFY YOUR TEST -------------------------");
		ShowTestDetails showTestDetails = new ShowTestDetails();
		showTestDetails.show(user, userid);
		
		System.out.print("Enter test code : ");
		String code = sc.next();
		
		TestDAO testDAO = new TestDAO();

		
		while(!exitFlag) {
//			Iterator<Test> testIterator = user.getTestMake().iterator();
//			while(testIterator.hasNext()) {
//				 test = testIterator.next();
//				 if(test.getId().equalsIgnoreCase(code)) {
//					break;
//				 }
//			}
			test = testDAO.getTest(Integer.parseInt(code));
			
			if(test != null) {
				id = test.getId();
				
				testTitle = test.getTestTitle();
				marks =test.getMarks();
				question = test.getQuestion();
				options = test.getOptions();
				answer = test.getAnswer();
			
			
				System.out.println("\n	ID : " + id + "		Title : " + testTitle +"	 Marks : " + marks);
				
				Iterator questions = question.iterator();
				Iterator<List<String>> optionsIterator = options.iterator();
				Iterator answers = answer.iterator();		
				
				int i = 1;
				while(questions.hasNext()) {
					System.out.println("\n" +  (i++) + ") " + questions.next());
					int j = 1;
					int answerString = 0;
					
					
					ConnectionManager connectionManager = new ConnectionManager();
					String sql = "SELECT options FROM options WHERE id = ?";
					
					PreparedStatement st = connectionManager.getConnection().prepareStatement(sql);
					
					st.setInt(1 , (int) answers.next());
					
					ResultSet rsOption = st.executeQuery();
					connectionManager.getConnection().close();
					
					rsOption.next();
					
					String rightAnswer = rsOption.getString("options");
					
					Iterator option = optionsIterator.next().iterator();
					while(option.hasNext()) {
						String optionString = option.next().toString();
						if(optionString.equalsIgnoreCase(rightAnswer)) {
							answerString = j;
						}
						System.out.println("" +  (j++) + "] " + optionString);
					}
					
					System.out.println("\nAnswer : " + answerString);
				}
				
				System.out.println("\n1] Title\n2] Marks per question\n3] Add question\n4] Modify Question \n5] Delete Question\n6] Exit");
				System.out.print("What you have to change : ");
				int choise = sc.nextInt();
				sc.nextLine();
					
				if(choise == 6)
					exitFlag = true;
				else if(choise > 0)
					testDAO.testUpdate(Integer.parseInt(code), choise, test);
				else
					System.out.println("Plase enter correct choice!");
			}
			else {
				exitFlag = true;
			}
		}
	}
	
}