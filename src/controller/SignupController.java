package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.User;
import utility.SignupValidation;

public class SignupController {	
	User user;
	
	public boolean SignUp() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n          --------------- ENTER YOUR DETAILS ---------------\n");
		
		System.out.print("Name : ");
		String name = sc.nextLine();
		
		System.out.print("Email address : ");
		String email = sc.next();
		
		System.out.print("Password : ");
		String password = sc.next();
		
		System.out.print("Confirm password : ");
		String confirmPassword = sc.next();
		
		boolean userType;
		while (true) {
			System.out.print("\n1] Test Maker		2] Test Giver \nEnter your type : ");
			int type = sc.nextInt();
			
			if(type == 1) {
				userType = true;
				break;
			}
			else if(type == 2) {
				userType = false;
				break;
			}
			else {
				System.out.println("\n               --- X -- Invalid input! -- X ---\n\nPlease re-enter your input");
			}
		}
		
		SignupValidation signupValidation = new SignupValidation();
		boolean response = signupValidation.checkUserDetails(email, password, confirmPassword);
		
		if(response) {
			user = new User(email,name, password, userType);
			System.out.println("SignUp : True");
			return true;
		}
		else {
			return false;
		}
		
		
	}
	
	
	public User getUserList(){
		return user;
	}
//	public void displayData() {
//		Iterator<User> userIterator = userData.iterator();
//		
//		while(userIterator.hasNext()) {
//			System.out.println(userIterator.next().getEmailId());
//		}
//		
//	} 
}
