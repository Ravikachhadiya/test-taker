package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.Test;
import model.User;
import utility.ConnectionManager;

public class TestDAO {

	public void addTest(Test test, int userid) throws ClassNotFoundException, SQLException, IOException {
		
		String testTitle;
		int marks;
		
		ConnectionManager cm = new ConnectionManager();
		// insert all the details into database
		
		String sql = "insert into TEST(id, title, marks, user_id) VALUES(seq_test.nextval,?,?,?)";
		
		//CREATE STATEMENT OBJECT
		
		PreparedStatement st = cm.getConnection().prepareStatement(sql);
		
		st.setString(1 , test.getTestTitle());
		st.setInt(2 , test.getMarks());
		st.setInt(3 , userid);
		
		st.executeUpdate();
		cm.getConnection().close();
		
		Statement statement = cm.getConnection().createStatement();
		
		ResultSet rs = statement.executeQuery("SELECT id FROM test where id=(SELECT MAX(id) FROM test)");
		rs.next();
		int testid = rs.getInt("id");
		
		List<String> questions = new ArrayList<String>();
		List<List<String>> options = new ArrayList<List<String>>();
		List<Integer> answers = new ArrayList<Integer>();
		
		questions = test.getQuestion();
		options = test.getOptions();
		answers = test.getAnswer();
		
		addQuestion(questions, options, answers, testid);
	}
	
	public void addQuestion(List<String> questions, List<List<String>> options, List<Integer> answers, int testid) throws ClassNotFoundException, SQLException, IOException {
		
		int optionid = 0;
		String testTitle;
		int marks;
		Iterator questionsList = questions.iterator();
		Iterator<List<String>> optionsList = options.iterator();
		Iterator answersList = answers.iterator();
		
		while(questionsList.hasNext()) {
			int ans = (int) answersList.next();
			ConnectionManager cm = new ConnectionManager();
			// insert all the details into database
			
			String sql = "insert into question(id, question, test_id) VALUES(seq_que.nextval,?,?)";
			
			//CREATE STATEMENT OBJECT
			
			PreparedStatement st = cm.getConnection().prepareStatement(sql);
			
			st.setString(1 , questionsList.next().toString());
			st.setInt(2 , testid);
			
			st.executeUpdate();
			cm.getConnection().close();
			
			Statement statement = cm.getConnection().createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT id FROM question where id=(SELECT MAX(id) FROM question)");
			rs.next();
			int questionId = rs.getInt("id");
						
			Iterator option = optionsList.next().iterator();
			int i = 1;
			while(option.hasNext()) {
				
				// insert all the details into database
				
				sql = "insert into options(id, options, que_id) VALUES(seq_option.nextval,?,?)";
				
				//CREATE STATEMENT OBJECT
				
				st = cm.getConnection().prepareStatement(sql);
				
				st.setString(1 , option.next().toString());
				st.setInt(2 , questionId);
				
				st.executeUpdate();
				cm.getConnection().close();
				
				
				if(ans == i) {
					statement = cm.getConnection().createStatement();
					
					rs = statement.executeQuery("SELECT id FROM options where id=(SELECT MAX(id) FROM options)");
					rs.next();
					optionid = rs.getInt("id");
					
					cm.getConnection();
				}
				
				i++;
			}
			
			sql = "insert into answer(que_id, option_id) VALUES(?,?)";
			
			//CREATE STATEMENT OBJECT
			
			st = cm.getConnection().prepareStatement(sql);
			
			st.setInt(1 , questionId);
			st.setInt(2 , optionid);
			
			st.executeUpdate();
			cm.getConnection().close();
			
		}
	}
	
	public Test getTest(int testid) throws SQLException, ClassNotFoundException, IOException {
		Test test = new Test();
		ConnectionManager cm = new ConnectionManager();
		String sql = "SELECT * FROM test WHERE id = ?";
		
		//CREATE STATEMENT OBJECT
		
		PreparedStatement st = cm.getConnection().prepareStatement(sql);
		
		st.setInt(1 , testid);
		
		ResultSet rs = st.executeQuery();
		cm.getConnection().close();
		
		if(rs.next()) {
			test.setId(String.valueOf(rs.getInt("id")));
			test.setMarks(rs.getInt("marks"));
			test.setTestTitle(rs.getString("title"));
			
			// ---- Questions ------
			
			sql = "SELECT * FROM question WHERE test_id = ? order by id";
					
			st = cm.getConnection().prepareStatement(sql);
			
			st.setInt(1 , testid);
			
			ResultSet rsQuestions = st.executeQuery();
			cm.getConnection().close();
			
			List<String> questionList = new ArrayList<String>();
			List<List<String>> options = new ArrayList<List<String>>();
			List<Integer> answer = new ArrayList<Integer>();
			while(rsQuestions.next()) {
				questionList.add(rsQuestions.getString("question"));
				
				int queId = rsQuestions.getInt("id");
				
				// ---- Option ------
				
				sql = "SELECT * FROM options WHERE que_id = ? order by id";
						
				st = cm.getConnection().prepareStatement(sql);
				
				st.setInt(1 , queId);
				
				ResultSet rsOption = st.executeQuery();
				cm.getConnection().close();
				
				
				List<String> optionList = new ArrayList<String>();
				while(rsOption.next()) {
					optionList.add(rsOption.getNString("options"));
				}
				
				options.add(optionList);
				
				// ---- Answer Id ------
				
				sql = "SELECT * FROM answer WHERE que_id = ?";
									
				st = cm.getConnection().prepareStatement(sql);
							
				st.setInt(1 , queId);
							
				ResultSet rsAnswer = st.executeQuery();
				cm.getConnection().close();
				
				rsAnswer.next(); 
				
				answer.add(rsAnswer.getInt("option_id"));
			}
			test.setQuestion(questionList);
			test.setOptions(options);
			test.setAnswer(answer);		
			
			return test;
		}
		else {
			System.out.println("\nPlease enter valid code!");
			return null;
		}

	}
	
	public void testUpdate(int testid, int operationNumber, Test test) throws SQLException, ClassNotFoundException, IOException {
		ConnectionManager connectionManager = new ConnectionManager();
		String sql;
		PreparedStatement st;
		ResultSet result;
		
		String testTitle;
		int marks;
		List<String> question = new ArrayList<String>();
		List<List<String>> options = new ArrayList<List<String>>();
		List<Integer> answer = new ArrayList<Integer>();
		
		testTitle = test.getTestTitle();
		marks =test.getMarks();
		question = test.getQuestion();
		options = test.getOptions();
		answer = test.getAnswer();
				
		Iterator questions = question.iterator();
		Iterator<List<String>> optionsIterator = options.iterator();
		Iterator answers = answer.iterator();	
		
		Scanner sc = new Scanner(System.in);
		
		switch(operationNumber) {
			case 1:
				System.out.print("Enter new title : ");
				testTitle = sc.nextLine();
				
				sql = "UPDATE test SET title = ? WHERE id = ?";
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setString(1, testTitle);
				st.setInt(2, testid);
				
				st.executeUpdate();
				connectionManager.getConnection().close();
				break;
				
			case 2:
				System.out.print("Enter new marks per question : ");
				marks = sc.nextInt();
				sc.nextLine();
				marks = marks * question.size();
				
				sql = "UPDATE test SET marks = ? WHERE id = ?";
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1, marks);
				st.setInt(2, testid);
				
				st.executeUpdate();
				connectionManager.getConnection().close();
				break;
			case 3:
				System.out.print("\nQuestion " + (question.size() + 1) + " : ");
				String que = sc.nextLine();
								
				System.out.print("Enter number of options : ");
				int numberOfOptions = sc.nextInt();
				sc.nextLine();
				
				List<String> optionList = new ArrayList<String>();
				for(int j = 0; j < numberOfOptions; j++) {
					System.out.print("Option " + (j+1) + " : ");
					String option = sc.nextLine();
					
					optionList.add(option);
				}
								
				System.out.print("\nEnter option number which is right answer : ");
				int rightAnswer = sc.nextInt();
				sc.nextLine();
				
				int ans = rightAnswer;
				ConnectionManager cm = new ConnectionManager();
				// insert all the details into database
				
				String sqlQuestion = "insert into question(id, question, test_id) VALUES(seq_que.nextval,?,?)";
				
				//CREATE STATEMENT OBJECT
				
				PreparedStatement stQuestion = cm.getConnection().prepareStatement(sqlQuestion);
				
				stQuestion.setString(1 , que.toString());
				stQuestion.setInt(2 , testid);
				
				stQuestion.executeUpdate();
				cm.getConnection().close();
				
				Statement statement = cm.getConnection().createStatement();
				
				ResultSet rs = statement.executeQuery("SELECT id FROM question where id=(SELECT MAX(id) FROM question)");
				rs.next();
				int questionId = rs.getInt("id");
				int optionid = 0;
							
				Iterator option = optionList.iterator();
				int i = 1;
				while(option.hasNext()) {
					
					// insert all the details into database
					
					sql = "insert into options(id, options, que_id) VALUES(seq_option.nextval,?,?)";
					
					//CREATE STATEMENT OBJECT
					
					st = cm.getConnection().prepareStatement(sql);
					
					st.setString(1 , option.next().toString());
					st.setInt(2 , questionId);
					
					st.executeUpdate();
					cm.getConnection().close();
					
					
					if(ans == i) {
						statement = cm.getConnection().createStatement();
						
						rs = statement.executeQuery("SELECT id FROM options where id=(SELECT MAX(id) FROM options)");
						rs.next();
						optionid = rs.getInt("id");
						
						cm.getConnection();
					}
					
					i++;
				}
				
				sql = "insert into answer(que_id, option_id) VALUES(?,?)";
				
				//CREATE STATEMENT OBJECT
				
				st = cm.getConnection().prepareStatement(sql);
				
				st.setInt(1 , questionId);
				st.setInt(2 , optionid);
				
				st.executeUpdate();
				cm.getConnection().close();
				
				// Update marks
				sql = "SELECT marks FROM test WHERE id = ?";
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1, testid);
				
				result = st.executeQuery();
				result.next();
				
				marks = (result.getInt("marks")/question.size()) * (question.size() + 1);
				
				connectionManager.getConnection().close();
				
				sql = "UPDATE test SET marks = ? WHERE id = ?";
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1, marks);
				st.setInt(2, testid);
				
				st.executeUpdate();
				connectionManager.getConnection().close();
				break;
			case 4:
				System.out.print("\nEnter question number : ");
				int queNo = sc.nextInt();
				sc.nextLine();
				
				int queIndex = 1;
				
				ConnectionManager cmModify = new ConnectionManager();
				sql = "SELECT id FROM question WHERE test_id = ? order by id";
				
				st = cmModify.getConnection().prepareStatement(sql);
				
				st.setInt(1, testid);
				
				result = st.executeQuery();
				cmModify.getConnection().close();
				
				if(queNo > question.size() || queNo < 1) {
					System.out.print("Please enter valid question number!");
					return;
				}
				
				int index = 1;
				int que_id = 0;

				while(result.next()) {
					if(index == queNo) {
						que_id = result.getInt("id");
						break;
					}
					index++;
				}
				
				String newQue;
				int oldAnswer;
				
				System.out.print("\nQuestion " + (queNo) + " : ");
				newQue = sc.nextLine();
								
				System.out.print("Enter number of options : ");
				numberOfOptions = sc.nextInt();
				sc.nextLine();
				
				optionList = new ArrayList<String>();
				for(int j = 0; j < numberOfOptions; j++) {
					System.out.print("Option " + (j+1) + " : ");
					String newOption = sc.nextLine();
					
					optionList.add(newOption);
				}
								
				System.out.print("\nEnter option number which is right answer : ");
				int newRightAnswer = sc.nextInt();
				sc.nextLine();
				
				// Delete old answer from database
				String sqlAnswer = "DELETE FROM answer WHERE que_id = ?";
				PreparedStatement stAnswer = connectionManager.getConnection().prepareStatement(sqlAnswer);
				
				stAnswer.setInt(1, que_id);
				
				stAnswer.executeUpdate();
				connectionManager.getConnection().close();
				
				// Delete old options from database
				String sqlOption = "DELETE FROM options WHERE que_id = ?";
				PreparedStatement stque = connectionManager.getConnection().prepareStatement(sqlOption);
				
				stque.setInt(1, que_id);
				
				stque.executeUpdate();
				connectionManager.getConnection().close();
				
				// Update new question				
				sqlQuestion = "UPDATE question SET question = ? WHERE id = ?";
								
				stQuestion = connectionManager.getConnection().prepareStatement(sqlQuestion);
				
				stQuestion.setString(1 , newQue);
				stQuestion.setInt(2 , que_id);
				
				stQuestion.executeUpdate();
				connectionManager.getConnection().close();
				
				optionid = 0;
							
				option = optionList.iterator();
				int newAnswerIndex = 1;
				while(option.hasNext()) {
					
					// insert all the details into database
					
					sql = "insert into options(id, options, que_id) VALUES(seq_option.nextval,?,?)";
					
					//CREATE STATEMENT OBJECT
					
					st = connectionManager.getConnection().prepareStatement(sql);
					
					st.setString(1 , option.next().toString());
					st.setInt(2 , que_id);
					
					st.executeUpdate();
					connectionManager.getConnection().close();
					
					
					if(newRightAnswer == newAnswerIndex) {
						statement = connectionManager.getConnection().createStatement();
						
						rs = statement.executeQuery("SELECT id FROM options where id=(SELECT MAX(id) FROM options)");
						rs.next();
						optionid = rs.getInt("id");
						
						connectionManager.getConnection();
					}
					
					newAnswerIndex++;
				}
				
				sql = "insert into answer(que_id, option_id) VALUES(?,?)";
				
				//CREATE STATEMENT OBJECT
				
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1 , que_id);
				st.setInt(2 , optionid);
				
				st.executeUpdate();
				connectionManager.getConnection().close();
				break;
			case 5:
				System.out.print("\nEnter question number : ");
				queNo = sc.nextInt();
				sc.nextLine();
				
				queIndex = 1;
				
				sql = "SELECT id FROM question WHERE test_id = ?";
				
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1, testid);
				
				result = st.executeQuery();
				connectionManager.getConnection().close();
				
				if(queNo > question.size() || queNo < 1) {
					System.out.print("Please enter valid question number!");
					return;
				}
				
				index = 1;
				que_id = 0;

				while(result.next()) {
					if(index == queNo) {
						que_id = result.getInt("id");
						break;
					}
					index++;
				}
				
				sqlAnswer = "DELETE FROM answer WHERE que_id = ?";
				stAnswer = connectionManager.getConnection().prepareStatement(sqlAnswer);
				
				stAnswer.setInt(1, que_id);
				
				stAnswer.executeUpdate();
				connectionManager.getConnection().close();
				
				
				sqlOption = "DELETE FROM options WHERE que_id = ?";
				stque = connectionManager.getConnection().prepareStatement(sqlOption);
				
				stque.setInt(1, que_id);
				
				stque.executeUpdate();
				connectionManager.getConnection().close();
				
				String sqlDeleteQuestion = "DELETE FROM question WHERE id = ?";
				PreparedStatement setDeleteQuestion = connectionManager.getConnection().prepareStatement(sqlDeleteQuestion);
				
				setDeleteQuestion.setInt(1, que_id);
				
				setDeleteQuestion.executeUpdate();
				connectionManager.getConnection().close();
				
				// Update marks
				sql = "SELECT marks FROM test WHERE id = ?";
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1, testid);
				
				result = st.executeQuery();
				result.next();
				
				marks = (result.getInt("marks")/question.size()) * (question.size() - 1);
				
				connectionManager.getConnection().close();
				
				sql = "UPDATE test SET marks = ? WHERE id = ?";
				st = connectionManager.getConnection().prepareStatement(sql);
				
				st.setInt(1, marks);
				st.setInt(2, testid);
				
				st.executeUpdate();
				connectionManager.getConnection().close();
				break;
		}
		
	}
	
	public void testDelete(int testid, int userid) throws ClassNotFoundException, SQLException, IOException {
		ConnectionManager connectionManager = new ConnectionManager();
		
		String sql = "SELECT * FROM question WHERE test_id = ?";
		PreparedStatement st = connectionManager.getConnection().prepareStatement(sql);
		
		st.setInt(1, testid);
		
		ResultSet questionSet = st.executeQuery();
		connectionManager.getConnection().close();
		
		while(questionSet.next()) {
			String sqlAnswer = "DELETE FROM answer WHERE que_id = ?";
			PreparedStatement stAnswer = connectionManager.getConnection().prepareStatement(sqlAnswer);
			
			stAnswer.setInt(1, questionSet.getInt("id"));
			
			stAnswer.executeUpdate();
			connectionManager.getConnection().close();
			
			
			String sqlOption = "DELETE FROM options WHERE que_id = ?";
			PreparedStatement stque = connectionManager.getConnection().prepareStatement(sqlOption);
			
			stque.setInt(1, questionSet.getInt("id"));
			
			stque.executeUpdate();
			connectionManager.getConnection().close();
			
			String sqlDeleteQuestion = "DELETE FROM question WHERE id = ?";
			PreparedStatement setDeleteQuestion = connectionManager.getConnection().prepareStatement(sqlDeleteQuestion);
			
			setDeleteQuestion.setInt(1, questionSet.getInt("id"));
			
			setDeleteQuestion.executeUpdate();
			connectionManager.getConnection().close();
		}
		
		String sqlDeleteTestresult = "DELETE FROM testresult WHERE test_id = ?";
		PreparedStatement setDeleteTestresult = connectionManager.getConnection().prepareStatement(sqlDeleteTestresult);
		
		setDeleteTestresult.setInt(1, testid);
		
		setDeleteTestresult.executeUpdate();
		connectionManager.getConnection().close();
		
		String sqlDeleteTest = "DELETE FROM test WHERE id = ?";
		PreparedStatement setDeleteTest = connectionManager.getConnection().prepareStatement(sqlDeleteTest);
		
		setDeleteTest.setInt(1, testid);
		
		setDeleteTest.executeUpdate();
		connectionManager.getConnection().close();
	}
				
}
