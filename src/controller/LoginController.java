package controller;

import java.util.List;
import java.util.Scanner;

import model.User;
import utility.LoginValidation;

public class LoginController {

	Scanner sc = new Scanner(System.in);
	public User login(List<User> userData) {
		
		System.out.println("\n          --------------- ENTER YOUR LOGIN DETAILS ---------------\n");

		System.out.print("Email address : ");
		String email = sc.next();
		
		System.out.print("Password : ");
		String password = sc.next();
		
		LoginValidation loginValidation = new LoginValidation();
		
		return loginValidation.loginValidation(userData, email, password);
	}
}
