package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Test;
import model.User;
import utility.SignupValidation;
import service.DefaultData;
import service.ShowTestDetails;
import service.TestGiver;
import service.TestMaker;
import service.UpdateTest;

public class Main {
	public static void main(String args[]) {
		SignupController signupController = new SignupController();
		LoginController loginController = new LoginController();
		List<User> userData = new ArrayList<User>();
		User user = null;
		List<Test> testList = new ArrayList<Test>();
		Scanner sc = new Scanner(System.in);

		userData.add(new User("ravi@123.com", "Ravi Kachhadiya", "Ravi@123", true));
		userData.add(new User("viral@123.com", "Viral Vekariya", "Viral@123", false));
		
		System.out.println("------------------------------------------------------------------");
		System.out.println("|                                                                |");
		System.out.println("|                    WELCOME TO THE TEST TAKER                   |");
		System.out.println("|                                                                |");
		System.out.println("------------------------------------------------------------------");
		
		boolean loginFlag = false;
		
		while(true) {
			
			System.out.print("\n1] Registration 					2] Login \n\nEnter here : ");
			
			int choiseOfLoginSignup = sc.nextInt();
			
			// System.out.println(choiseOfLoginSignup);
			
			switch(choiseOfLoginSignup){
				case 1:
					
					while(true) {
						if(signupController.SignUp()) {
							System.out.println("\n              ********   SUCCESSFULLY SIGNUP   ********\n");
							userData.add(signupController.getUserList());
							break;
						}
						else {
							System.out.println("\n       --- X -- Invalid Inputs! Please Try Again -- X ---\n");
						}
					}
					break;
				case 2:
						user = loginController.login(userData);
						if(user != null) {
							System.out.println("\n                ********   SUCCESSFULLY LOGGEDIN   ********\n");
							loginFlag = true;
							break;
						}
						else {
							System.out.println("\n       --- X -- Invalid Inputs! Please Try Again -- X ---\n");
							break;
						}
			}
			while(loginFlag) {
		
				if(loginFlag) {
					System.out.println("------------->  HELLO " + user.getUserName().toUpperCase() + ", WELCOME TO THE TEST TAKER  <-------------");
					while(true) {
						System.out.print("\n1] Menu							2] Logout \n\nEnter Here : ");
						int loginChoise = sc.nextInt();
						switch(loginChoise){
							case 1 :
								if(user.isUserType()) {
									// Test Maker
									System.out.print("\n1] Profile\n2] Make A Test\n3] Tests Details\n"
											+ "4] Test Results\n5] Add Default Test\n6] Delete Test\n"
											+ "7] Modify Test\n\nEnter Here : ");
									int menuChoise = sc.nextInt();
									switch(menuChoise) {
										case 1:
											System.out.println("\n---------------------     PROFILE DETAILS     ---------------------\n");
											System.out.println("Name : " + user.getUserName());
											System.out.println("Email : " + user.getEmailId());
											System.out.println("\n------------------------------------------------------------\n");
											
											break;
										case 2:
											// Test
											TestMaker testMaker = new TestMaker();
											Test test = testMaker.makeATest(user);
											testList.add(test);
											user.setTestMake(test);
											break;
										case 3:
											ShowTestDetails showTestDetails = new ShowTestDetails();
											showTestDetails.show(user);
											break;
										case 4:
											showTestDetails = new ShowTestDetails();
											showTestDetails.showTestReults(user);
											break;
										case 5:
											DefaultData defaultData = new DefaultData();
											testList.add(defaultData.defaultTest(user));
											break;
										case 6:
											TestMaker testMaker1 = new TestMaker();
											Test testDeletion = testMaker1.deleteTest(testList, user);
											testList.remove(testDeletion);
											user.getTestMake().remove(testDeletion);
											
											System.out.println("\n                ********   TEST SUCCESSFULLY DELETED   ********\n");
											break;
										case 7:
											UpdateTest updateTest = new UpdateTest();
											updateTest.update(user);
											break;
									}
								
								}
								else {
									// Test Giver
									// ------------------------------
									
									System.out.print("\n1] Profile	2] Give A Test		3] Tests Details\n\nEnter Here : ");
									int menuChoise = sc.nextInt();
									switch(menuChoise) {
										case 1:
											System.out.println("\n---------------------     PROFILE DETAILS     ---------------------\n");
											System.out.println("Name : " + user.getUserName());
											System.out.println("Email : " + user.getEmailId());
											System.out.println("\n------------------------------------------------------------\n");
											
											break;
										case 2:
											// Test
											TestGiver testGiver = new TestGiver();
											while(true) {
												Test test = testGiver.getTest(testList);
												if(test != null) {
													testGiver.giveATest(test, user);
													break;
												}
												else {
													System.out.println("\n       --- X -- Invalid Code! Please Try Again -- X ---\n");
												}
											}
											break;
										case 3:
											ShowTestDetails showTestDetails = new ShowTestDetails();
											showTestDetails.show(user);
											break;
										case 4:
											DefaultData defaultData = new DefaultData();
											testList.add(defaultData.defaultTest(user));
											break;
									}
									
								}
								break;
							case 2:
								loginFlag = false;
								user = null;
								break;	
						}
						
						if(!loginFlag) break;
						
					}
					
				}
			}
		}
			
	}	
}
