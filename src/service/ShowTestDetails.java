package service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Test;
import model.TestResult;
import model.User;
import utility.ConnectionManager;

public class ShowTestDetails {
	
	public void show(User user, int userid) throws ClassNotFoundException, SQLException, IOException {
		if(user.isUserType()) {
			// Test Taker
			System.out.println("\n------------------------------------------------------------------------------------");
			System.out.printf("%-27s %-27s %s", "	Test Number", "	Subject Title" , "	Marks");
			System.out.println("\n------------------------------------------------------------------------------------");
						
			ConnectionManager cm = new ConnectionManager();
			String sql = "SELECT * FROM test WHERE user_id = ?";
			
			//CREATE STATEMENT OBJECT
			
			PreparedStatement st = cm.getConnection().prepareStatement(sql);
			
			st.setInt(1 , userid);
			
			ResultSet rs = st.executeQuery();
			cm.getConnection().close();
						
			while(rs.next()) {
				System.out.printf("%-40s %-32s %s\n", rs.getInt("id"), rs.getString("title"), rs.getInt("marks"));
			}
			System.out.println("------------------------------------------------------------------------------------\n");
		}
		else {
			// Test Giver
			System.out.println("\n------------------------------------------------------------------------------------");
			System.out.printf("%-27s %-27s %s ", "	Test Number", "	Subject Title" , "Marks");
			System.out.println("\n------------------------------------------------------------------------------------");
			
			ConnectionManager cm = new ConnectionManager();
			String sql = "SELECT * FROM testresult WHERE user_id = ?";
			
			//CREATE STATEMENT OBJECT
			
			PreparedStatement st = cm.getConnection().prepareStatement(sql);
			
			st.setInt(1 , userid);
			
			ResultSet rs = st.executeQuery();
			cm.getConnection().close();
			
			while(rs.next()) {
			// ----- test -----
			sql = "SELECT * FROM test WHERE id = ?";
			
			//CREATE STATEMENT OBJECT
			st = cm.getConnection().prepareStatement(sql);
			
			st.setInt(1 , rs.getInt("test_id"));
			
			ResultSet rsTest = st.executeQuery();
			cm.getConnection().close();
			
			rsTest.next();
			
				System.out.printf("%-40s %-27s %s\n", rs.getInt("test_id"), rsTest.getString("title"),(rs.getInt("marks") + "/ " +rsTest.getInt("marks")));
			}
	            
			System.out.println("------------------------------------------------------------------------------------\n");
		}
	}
	
	public void showTestReults(User user, int userid) throws ClassNotFoundException, SQLException, IOException {
		show(user, userid);
		
		Scanner sc = new Scanner(System.in);
		
			System.out.print("Enter test code : ");
			int code = sc.nextInt();
			
			ConnectionManager cm = new ConnectionManager();
			String sql = "SELECT * FROM testresult WHERE test_id = ? order by testdate";
			
			//CREATE STATEMENT OBJECT
			
			PreparedStatement st = cm.getConnection().prepareStatement(sql);
			
			st.setInt(1 , code);
			
			ResultSet rs = st.executeQuery();
			cm.getConnection().close();
			
			if(rs.next()) {
				
				System.out.println("\n------------------------------------------------------------------------------------------------------------");
				System.out.printf("%-27s %-27s %-27s %s", "	Name", "	Email" , "	Date", "   Marks");
				System.out.println("\n------------------------------------------------------------------------------------------------------------");
				
				
				ConnectionManager connectionManager = new ConnectionManager();
				
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement("select * from test where id = ?");
				
				preparedStatement.setInt(1, rs.getInt("test_id"));
			
				ResultSet rsTest = preparedStatement.executeQuery();
				cm.getConnection().close();
				rsTest.next();
				
				int marks = rsTest.getInt("marks");
				
				while(true) {
					 connectionManager = new ConnectionManager();
					
					// Step 2:Create a statement using connection object
					 preparedStatement = connectionManager.getConnection().prepareStatement("select * from USERDETAILS where userid = ?");
			
					preparedStatement.setInt(1, rs.getInt("user_id"));
				
					ResultSet rsUser = preparedStatement.executeQuery();
					rsUser.next();
					cm.getConnection().close();
					
					System.out.printf("%-35s %-32s %-32s %s\n", rsUser.getString("name"), rsUser.getString("email"),
							rs.getDate("testdate").toLocalDate().toString(), (rs.getInt("marks") + "/" + marks));
					
					if(rs.next())
						continue;
					else
						break;
				}
				
				System.out.println("------------------------------------------------------------------------------------------------------------\n");
			}
			else {
				System.out.println("\nNo result found!");
			}
		
	}
		
		
	
	public String dateFormater(LocalDateTime date) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
        String formatDateTime = date.format(formatter);
		
		return formatDateTime;
	}
	
}
