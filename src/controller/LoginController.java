package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.UserDAO;
import model.User;
import utility.LoginValidation;

public class LoginController {

	Scanner sc = new Scanner(System.in);
	public int login(List<User> userData) throws ClassNotFoundException, IOException, SQLException {
		
		System.out.println("\n          --------------- ENTER YOUR LOGIN DETAILS ---------------\n");

		System.out.print("Email address : ");
		String email = sc.next();
		
		System.out.print("Password : ");
		String password = sc.next();
		
		UserDAO loginDAO = new UserDAO();
		
		
		return loginDAO.loginUser(email.toLowerCase(), password);
	}
}
