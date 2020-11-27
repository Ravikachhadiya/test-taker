package service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import dao.TestDAO;
import model.Test;
import model.User;

public class TestMaker {
	Test test;
	Scanner sc = new Scanner(System.in);
	
	
	public Test makeATest(User user) {
		
		String id;
		String testTitle;
		int marks;
		List<String> questions = new ArrayList<String>();
		List<List<String>> options = new ArrayList<List<String>>();
		List<Integer> answers = new ArrayList<Integer>();
				
		System.out.println("\n--------------------- Make Your Test ---------------------\n");
		
		System.out.print("Enter Test Title : ");
		testTitle = sc.nextLine();
		
		System.out.print("Enter marks of per question : ");
		marks = sc.nextInt();
		
		System.out.print("Enter number of questions : ");
		int numberOfQuestions = sc.nextInt();
		sc.nextLine();
		
		marks *= numberOfQuestions;
		
		for(int i = 0; i < numberOfQuestions; i++) {
			System.out.print("\nQuestion " + (i+1) + " : ");
			String question = sc.nextLine();
			
			questions.add(question);
			
			System.out.print("Enter number of options : ");
			int numberOfOptions = sc.nextInt();
			sc.nextLine();
			
			List<String> optionList = new ArrayList<String>();
			for(int j = 0; j < numberOfOptions; j++) {
				System.out.print("Option " + (j+1) + " : ");
				String option = sc.nextLine();
				
				optionList.add(option);
			}
			
			options.add(optionList);
			
			System.out.print("\nEnter option number which is right answer : ");
			int rightAnswer = sc.nextInt();
			sc.nextLine();
			
			answers.add(rightAnswer);
		}
		
		
		id = uniqueId();
		
		System.out.println("\n----------- Your Test Was Created Successfully -----------\n");
		
		test = new Test(user, id, testTitle, marks, questions, options, answers);
		return test;
	}
	
	public String uniqueId() {
		LocalDateTime d = LocalDateTime.now();
		String idString = d.toString();
		
		StringBuilder id = new StringBuilder();
		for(int i = 0; i < idString.length(); i++){
			char c = idString.charAt(i);
			if(c !=  '-' && c !=  ':' && c !=  '.')
				id.append(c);
		}
		
		return id.toString();
	}
	
	public Test deleteTest(List<Test> tests, User user, int userid) throws ClassNotFoundException, SQLException, IOException {
		
		System.out.println("--------------------------- DELETE TEST ---------------------------");
		
		ShowTestDetails showTestDetails = new ShowTestDetails();
		showTestDetails.show(user, userid);
		
		System.out.print("Enter test code : ");
		String code = sc.next();
		
		
		TestDAO testDAO = new TestDAO();
		testDAO.testDelete(Integer.parseInt(code), userid);
		
		
		Iterator<Test> testIterator = tests.iterator();
		while(testIterator.hasNext()) {
			Test test = testIterator.next();
			if(test.getId().equalsIgnoreCase(code)) {
				return test;
			}
		}
		
		return null;
	}
}

