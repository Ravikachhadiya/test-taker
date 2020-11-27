package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;
import utility.ConnectionManager;

public class UserDAO {
	
	public int addUser(User user) throws ClassNotFoundException, SQLException, IOException {
		
		int status = 0;
		String username = user.getUserName();
		String email = user.getEmailId();
		String password = user.getPassword();
		int userType = user.isUserType() ? 1 : 0;
		
		ConnectionManager cm = new ConnectionManager();
		// insert all the details into database
		
		String sql = "insert into USERDETAILS(userid, name, email, userpassword, usertype) VALUES(seq_user.nextval,?,?,?,?)";
		
		//CREATE STATEMENT OBJECT
		
		PreparedStatement st = cm.getConnection().prepareStatement(sql);
		
		st.setString(1 , username);
		st.setString(2 , email);
		st.setString(3 , password);
		st.setInt(4 , userType);
		
		status = st.executeUpdate();
		cm.getConnection().close();
		
		return status;
	}
	
	public int loginUser(String email, String password) throws ClassNotFoundException, IOException, SQLException {
		boolean status = false;
		ResultSet rs = null;
		try{
			ConnectionManager connectionManager = new ConnectionManager();
		
				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement("select * from USERDETAILS where email = ? and userpassword = ?");
		
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			
			rs = preparedStatement.executeQuery();
			status = rs.next();

		} catch (SQLException e) {
			// process sql exception
			System.out.println(e);
		}
		if( status) {
			
			return rs.getInt("userid");
		}
		else
			return 0;
	}

	public User userInfo(int id) throws SQLException, ClassNotFoundException, IOException {
		boolean status = false;
		ResultSet rs = null;
		try{
			ConnectionManager connectionManager = new ConnectionManager();
		
				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement("select * from USERDETAILS where userid = ?");
		
			preparedStatement.setInt(1, id);
			
			rs = preparedStatement.executeQuery();
			status = rs.next();

		} catch (SQLException e) {
			// process sql exception
			System.out.println(e);
		}
		if( status) {
			User user = new User();
			user.setUserName(rs.getString("name"));
			user.setEmailId(rs.getString("email"));
			int usertype = rs.getInt("usertype");
			if(usertype == 1) {
				user.setUserType(true);
			}
			else {
				user.setUserType(false);
			}
			
			return user;
		}
		else
			return null;
	}
	
}
