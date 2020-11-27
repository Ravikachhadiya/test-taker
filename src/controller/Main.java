package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.TestDAO;
import dao.UserDAO;
import model.Test;
import model.User;
import utility.SignupValidation;
import service.DefaultData;
import service.ShowTestDetails;
import service.TestGiver;
import service.TestMaker;
import service.UpdateTest;

public class Main {
	public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException {
		UserDAO userDAO = new UserDAO();
		int userid = 0;
		
		SignupController signupController = new SignupController();
		LoginController loginController = new LoginController();
		List<User> userData = new ArrayList<User>();
		User user = new User();
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
					
					int checkUserData = 0;
					while(true) {
						if(signupController.SignUp()) {	
							checkUserData = userDAO.addUser(signupController.getUserList());
							System.out.println("checkUserData : " + checkUserData);
						}
						if(checkUserData == 1) {
							System.out.println("\n              ********   SUCCESSFULLY SIGNUP   ********\n");
							break;
						}
						else {
							System.out.println("\n       --- X -- Invalid Inputs! Please Try Again -- X ---\n");
							break;
						}
					}
					break;
				case 2:
						userid = loginController.login(userData);
						
						if(userid != 0) {
							user.setEmailId(userDAO.userInfo(userid).getEmailId());
							user.setUserName(userDAO.userInfo(userid).getUserName());
							user.setUserType(userDAO.userInfo(userid).isUserType());
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
					System.out.println("------------->  HELLO " + user.getUserName() + ", WELCOME TO THE TEST TAKER  <-------------");
					while(true) {
						System.out.print("\n1] Menu							2] Logout \n\nEnter Here : ");
						int loginChoise = sc.nextInt();
						switch(loginChoise){
							case 1 :
								if(userDAO.userInfo(userid).isUserType()) {
									// Test Maker
									System.out.print("\n1] Profile\n2] Make A Test\n3] Tests Details\n"
											+ "4] Test Results\n5] Delete Test\n"
											+ "6] Modify Test\n\nEnter Here : ");
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
											TestDAO testDAO = new TestDAO();
											testDAO.addTest(test, userid);
											testList.add(test);
//											user.setTestMake(test);
											break;
										case 3:
											ShowTestDetails showTestDetails = new ShowTestDetails();
											showTestDetails.show(user, userid);
											break;
										case 4:
											showTestDetails = new ShowTestDetails();
											showTestDetails.showTestReults(user, userid);
											break;
										case 5:
											TestMaker testMaker1 = new TestMaker();
											Test testDeletion = testMaker1.deleteTest(testList, user, userid);
											testList.remove(testDeletion);
											user.getTestMake().remove(testDeletion);
											
											System.out.println("\n                ********   TEST SUCCESSFULLY DELETED   ********\n");
											break;
										case 6:
											UpdateTest updateTest = new UpdateTest();
											updateTest.update(user, userid);
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
												Test test = testGiver.getTest();
												if(test != null) {
													testGiver.giveATest(test, user, userid);
													break;
												}
												else {
													System.out.println("\n       --- X -- Invalid Code! Please Try Again -- X ---\n");
												}
											}
											break;
										case 3:
											ShowTestDetails showTestDetails = new ShowTestDetails();
											showTestDetails.show(user,userid);
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
