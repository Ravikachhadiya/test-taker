package dao;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import utility.ConnectionManager;

public class TestResultDAO {

	public void addTestResult(int testid, int marks, int userid, LocalDate date) throws ClassNotFoundException, SQLException, IOException {
		ConnectionManager cm = new ConnectionManager();
		// insert all the details into database
		
		String sql = "insert into TESTRESULT(id, test_id, marks, user_id, testdate) VALUES(seq_test_result.nextval,?,?,?,?)";
		
		//CREATE STATEMENT OBJECT
		
		PreparedStatement st = cm.getConnection().prepareStatement(sql);
		
		st.setInt(1 , testid);
		st.setInt(2 , marks);
		st.setInt(3 , userid);
		st.setDate(4 , Date.valueOf(date));
		
		st.executeUpdate();
		cm.getConnection().close();
	}
}
