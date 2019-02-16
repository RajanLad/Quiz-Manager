package fr.epita.quizProject.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quizProject.datamodel.Administrator;
import fr.epita.quizProject.datamodel.Student;
import fr.epita.quizProject.datamodel.Quiz;

public class QuizJDBC_DAO 
{
		public boolean authenticate(Administrator admin) {
			String sqlCommand = "SELECT * FROM ADMIN where ADMIN_ID=(?) and PASSWORD=(?)";
			try (Connection connection = getConnection();
				PreparedStatement selectStatement = connection.prepareStatement(sqlCommand);) {
				
				selectStatement.setString(1,admin.getId());
				selectStatement.setString(2,admin.getPassword());
				
				ResultSet rs = selectStatement.executeQuery();
				
				return rs.next();
	
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	
		}
		
//		INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)

		
		public boolean addQuestiontoDB(Quiz quiz) {
			String sqlCommand = "INSERT INTO QUIZ(QUIZ_ID, QUESTION, ANSWER, TOPIC, DIFFCULTY) VALUES (?,?,?,?,?)";
			
			try (Connection connection = getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);) {
				
				insertStatement.setInt(1,quiz.getQuizid());
				insertStatement.setString(2,quiz.getQuestion());
				insertStatement.setString(3,quiz.getAnswer());
				insertStatement.setString(4,quiz.getTopic());
				insertStatement.setInt(5,quiz.getDifficulty());
				
				return insertStatement.execute();
				

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
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
