package service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.Test;
import model.TestResult;
import model.User;

public class UpdateTest {
	Scanner sc = new Scanner(System.in);
	
	String id;
	String testTitle;
	int marks;
	List<String> question = new ArrayList<String>();
	List<List<String>> options = new ArrayList<List<String>>();
	List<Integer> answer = new ArrayList<Integer>();
	
	Test test = null;
	
	public void update(User user) {
		
		boolean exitFlag = false;
		
		
		
		System.out.println("\n------------------------- MODIFY YOUR TEST -------------------------");
		ShowTestDetails showTestDetails = new ShowTestDetails();
		showTestDetails.show(user);
		
		System.out.print("Enter test code : ");
		String code = sc.next();
		
		while(!exitFlag) {
			Iterator<Test> testIterator = user.getTestMake().iterator();
			while(testIterator.hasNext()) {
				 test = testIterator.next();
				 if(test.getId().equalsIgnoreCase(code)) {
					break;
				 }
			}
			
			id = test.getId();
			
			testTitle = test.getTestTitle();
			marks =test.getMarks();
			question = test.getQuestion();
			options = test.getOptions();
			answer = test.getAnswer();
			
			List<TestResult> testResults = new ArrayList<TestResult>();
			testResults = test.getTestResults();
			
			System.out.println("\n	ID : " + id + "		Title : " + testTitle +"	 Marks : " + marks);
			
			Iterator questions = question.iterator();
			Iterator<List<String>> optionsIterator = options.iterator();
			Iterator answers = answer.iterator();		
			
			int i = 1;
			while(questions.hasNext()) {
				System.out.println("\n" +  (i++) + ") " + questions.next());
				int j = 1;
				
				Iterator option = optionsIterator.next().iterator();
				while(option.hasNext()) {
					System.out.println("" +  (j++) + "] " + option.next());
				}
				
				System.out.println("\nAnswer : " + answers.next());
			}
			
			System.out.println("\n1] Title\n2] Marks per question\n3] Add question\n4] Modify Question \n5] Delete Question\n6] Exit");
			System.out.print("What you have to change : ");
			int choise = sc.nextInt();
			sc.nextLine();
			
			switch(choise){
			case 1:
				System.out.print("Enter new title : ");
				testTitle = sc.nextLine();
				test.setTestTitle(testTitle);
				break;
			case 2:
				System.out.print("Enter new marks per question : ");
				marks = sc.nextInt();
				sc.nextLine();
				test.setMarks(marks * question.size());
				break;
			case 3:
				addQuestion();
				break;
			case 4:
				modifyQuestion();
				break;
			case 5:
				deleteQuestion();
				break;
			case 6:
				exitFlag = true;
				break;
			default:
				System.out.println("Plase enter correct choice!");
				break;	
			}
		}
	}
	
	void addQuestion() {
		System.out.print("\nQuestion " + (question.size() + 1) + " : ");
		String que = sc.nextLine();
		
		question.add(que);
		
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
		
		answer.add(rightAnswer);
		
		test.setQuestion(question);
		test.setOptions(options);
		test.setAnswer(answer);
	}
	
	void modifyQuestion() {
		System.out.print("\nEnter question number : ");
		int queNo = sc.nextInt();
		sc.nextLine();
		
		if(queNo > question.size() || queNo < 1) {
			System.out.print("Please enter valid question number!");
			return;
		}
		
		String newQue = question.get(queNo - 1);
		
		List<String> newOptions = new ArrayList<String>();
		newOptions = options.get(queNo - 1);
		
		int oldAnswer = answer.get(queNo - 1);
		
		
		System.out.print("\nQuestion " + (queNo) + " : ");
		newQue = sc.nextLine();
		
		question.set(queNo - 1, newQue);
		
		System.out.print("Enter number of options : ");
		int numberOfOptions = sc.nextInt();
		sc.nextLine();
		
		List<String> optionList = new ArrayList<String>();
		for(int j = 0; j < numberOfOptions; j++) {
			System.out.print("Option " + (j+1) + " : ");
			String option = sc.nextLine();
			
			optionList.add(option);
		}
		
		options.set(queNo - 1, optionList);
		
		System.out.print("\nEnter option number which is right answer : ");
		int rightAnswer = sc.nextInt();
		sc.nextLine();
		
		answer.set(queNo - 1, rightAnswer);
		
		test.setQuestion(question);
		test.setOptions(options);
		test.setAnswer(answer);
	}
	
	void deleteQuestion() {
		System.out.print("\nEnter question number : ");
		int queNo = sc.nextInt();
		sc.nextLine();
		
		question.remove(queNo - 1);
		options.remove(queNo - 1);
		answer.remove(queNo - 1);
		
		test.setQuestion(question);
		test.setOptions(options);
		test.setAnswer(answer);
		
		test.setMarks((test.getMarks() / (question.size() + 1)) * question.size());
	}
	
	
	
}
