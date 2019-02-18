package fr.epita.quizProject.service;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import fr.epita.quizProject.datamodel.Student;
import fr.epita.quizProject.datamodel.Quiz;

public class StudentJDBC_DAO 
{
	public int authenticate(Student student) {
		String sqlCommand = "SELECT id FROM STUDENT where id=(?)";
		int id=0;
		try (Connection connection = getConnection();
			PreparedStatement selectStatement = connection.prepareStatement(sqlCommand);) {
			
			selectStatement.setInt(1,student.getId());
			ResultSet rs = selectStatement.executeQuery();
			
			while (rs.next()) {
				id = rs.getInt("ID");
			}
			
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}
	
	public int create(Student student) {
		String sqlCommand = "INSERT INTO Student(student_email_id) values (?)";
		
		String sqlCommandToGiveId = "SELECT ID FROM STUDENT where student_email_id=(?)";
		
		int id=0;
		
		try (Connection connection = getConnection();
			PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);
				PreparedStatement selectStatement = connection.prepareStatement(sqlCommandToGiveId);) {
			
			insertStatement.setString(1,student.getName());
			insertStatement.execute();
			
			selectStatement.setString(1,student.getName());
			ResultSet rs = selectStatement.executeQuery();
			
			while (rs.next()) {
				id = rs.getInt("ID");
			}
			
			return id;

		} catch (SQLException e) {
			e.printStackTrace();
			return id;
		}

	}
	
	public void readQuestions(Quiz quiz) {
//		List<HashMap<String,List<String>>>
		String sqlCommand = "SELECT QUESTION,MCQ_ID FROM QUIZ where QUIZ_ID=(?)";
		
		List<HashMap<String,List<String>>> questionGroup=new ArrayList<>();
		
		HashMap<String,List<String>> question=new HashMap<>();
		
		List<String> mcq_options=new ArrayList<>();
		
		try (Connection connection = getConnection();
			PreparedStatement selectStatement = connection.prepareStatement(sqlCommand);) {
			selectStatement.setInt(1,quiz.getQuizid());
			ResultSet rs = selectStatement.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("MCQ_ID"));
				System.out.println(rs.getString("QUESTION"));
			}
			
//			return id;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void update(Student student) {
		String sqlCommand = "INSERT INTO Student(student_email_id) values (?)";
		try (Connection connection = getConnection();
			PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);) {
			
			insertStatement.setString(1,student.getName());
			
			insertStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public Connection getConnection() throws SQLException {
		Configuration conf = Configuration.getInstance();
		String jdbcUrl =conf.getConfigurationValue("jdbc.url");
		String user = conf.getConfigurationValue("jdbc.user");
		String password = conf.getConfigurationValue("jdbc.password");
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		return connection;
	}
}
