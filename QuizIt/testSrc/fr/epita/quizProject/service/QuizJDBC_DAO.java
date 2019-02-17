package fr.epita.quizProject.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quizProject.datamodel.Administrator;
import fr.epita.quizProject.datamodel.Student;
import fr.epita.quizProject.datamodel.Quiz;
import fr.epita.quizProject.datamodel.MCQQuestion;

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
		
		public boolean addQuestiontoDB(Quiz quiz,boolean isAMCQQuestion) {
			String sqlCommand = "INSERT INTO QUIZ(QUIZ_ID, MCQ_ID, QUESTION, ANSWER, TOPIC, DIFFCULTY) VALUES (?,?,?,?,?,?)";
			
			try (Connection connection = getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);) {
				
				insertStatement.setInt(1,quiz.getQuizid());
				if(isAMCQQuestion)
					insertStatement.setInt(2, quiz.getMcq_id());
				else if(!isAMCQQuestion)
					insertStatement.setNull(2, 0);
				insertStatement.setString(3,quiz.getQuestion());
				insertStatement.setString(4,quiz.getAnswer());
				insertStatement.setString(5,quiz.getTopic());
				insertStatement.setInt(6,quiz.getDifficulty());
				
				return insertStatement.execute();
				

			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		}
		
		public int addMCQQuestiontoDB(MCQQuestion mcq) {
			String sqlCommand = "INSERT INTO MCQQUESTIONS(option_a, option_b, option_c, option_d) VALUES (?,?,?,?)";
			String sqlCommandToGetMCQId = "select MCQ_ID from MCQQUESTIONS where option_a=(?)";
			
			int id=0;
			
			try (Connection connection = getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);
					PreparedStatement selectStatement = connection.prepareStatement(sqlCommandToGetMCQId);) {
				
				insertStatement.setString(1,mcq.getOption_a());
				insertStatement.setString(2,mcq.getOption_b());
				insertStatement.setString(3,mcq.getOption_c());
				insertStatement.setString(4,mcq.getOption_d());
				
				insertStatement.execute();
				
				selectStatement.setString(1,mcq.getOption_a());
				ResultSet rs = selectStatement.executeQuery();
				
				while (rs.next()) {
					id = rs.getInt("MCQ_ID");
				}
				
				return id;
				

			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
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
