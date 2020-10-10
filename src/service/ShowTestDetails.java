package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Test;
import model.TestResult;
import model.User;

public class ShowTestDetails {
	
	public void show(User user) {
		if(user.isUserType()) {
			// Test Taker
			System.out.println("\n------------------------------------------------------------------------------------");
			System.out.printf("%-27s %-27s %s", "	Test Number", "	Subject Title" , "	Marks");
			System.out.println("\n------------------------------------------------------------------------------------");
			
			Iterator<Test> tests = user.getTestMake().iterator();
			
			while(tests.hasNext()) {
				Test test = tests.next();
				System.out.printf("%-40s %-32s %s\n", test.getId(), test.getTestTitle(), test.getMarks());
			}
			System.out.println("------------------------------------------------------------------------------------\n");
		}
		else {
			// Test Giver
			System.out.println("\n------------------------------------------------------------------------------------");
			System.out.printf("%-27s %-27s %s ", "	Test Number", "	Subject Title" , "Marks");
			System.out.println("\n------------------------------------------------------------------------------------");
			
			Map<Test, List<Integer>> testMarks = user.getTestMarks();
			
			for (Map.Entry<Test, List<Integer>> entry : testMarks.entrySet()) {
				System.out.printf("%-40s %-27s %s\n", entry.getKey().getId(), entry.getKey().getTestTitle(),(entry.getValue().get(0) + "/ " +entry.getValue().get(1)));
			}
	            
			System.out.println("------------------------------------------------------------------------------------\n");
		}
	}
	
	public void showTestReults(User user) {
		show(user);
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter test code : ");
		String code = sc.next();
		
		Iterator<Test> tests = user.getTestMake().iterator();
		
		while(tests.hasNext()) {
			Test test = tests.next();
			if(code.equalsIgnoreCase(test.getId())) {
				Iterator<TestResult> testIterator = test.getTestResults().iterator();
				
				System.out.println("\n------------------------------------------------------------------------------------------------------------");
				System.out.printf("%-27s %-27s %-27s %s", "	Name", "	Email" , "	Date", "   Marks");
				System.out.println("\n------------------------------------------------------------------------------------------------------------");
				
				while(testIterator.hasNext()) {
					TestResult testResult = testIterator.next();
					System.out.printf("%-35s %-32s %-27s %s\n", testResult.getUserName(), testResult.getUserEmail(), dateFormater(testResult.getDate()), (testResult.getObtainnMarks() + "/" + testResult.getTestMarks()));
				}
				System.out.println("------------------------------------------------------------------------------------------------------------\n");
			}
		}
		
		
	}
	
	public String dateFormater(LocalDateTime date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
        String formatDateTime = date.format(formatter);
		
		return formatDateTime;
	}
	
}
