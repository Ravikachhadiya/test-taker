package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.Test;
import model.TestResult;
import model.User;

public class TestGiver {

	public void giveATest(Test test,User user) {
		
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
				int ans = sc.nextInt();
				
				if(String.valueOf(ans).equals(answers.next().toString())) {
					sum += (test.getMarks() / test.getAnswer().size());
				}
			}
			
			TestResult testResult = new TestResult(user.getUserName(), user.getEmailId(), sum, test.getMarks(), LocalDateTime.now());
			test.setTestResults(testResult);
			
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
	
	public Test getTest(List<Test> test) {
		
		System.out.println("\n--------------- Select your test ---------------\n");
		System.out.print("Enter your test code : ");
		Scanner sc = new Scanner(System.in);
		String id = sc.nextLine();
		
		Iterator<Test> testList = test.iterator();
		while(testList.hasNext()) {
			Test tst = testList.next();
			if(id.equalsIgnoreCase(tst.getId())){
				return tst;
			}
		}
		
		return null;
	}
}
