package fr.epita.quizProject.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quizProject.datamodel.Administrator;
import fr.epita.quizProject.datamodel.Student;
import fr.epita.quizProject.datamodel.Quiz;
import fr.epita.quizProject.datamodel.Quiz_Result;
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

		public void displayAllQuestions(int quizid) {
			// TODO Auto-generated method stub
			String sqlCommand = "SELECT QUIZ_ID, MCQ_ID, QUESTION, TOPIC, DIFFCULTY FROM QUIZ WHERE QUIZ_ID = ?";
			int quizId;
			int mcqId;
			String question;
			String topic;
			int difficulty;
			if(quizid == 0)		
				 sqlCommand = "SELECT QUIZ_ID, MCQ_ID, QUESTION, TOPIC, DIFFCULTY FROM QUIZ";
			
			try (Connection connection = getConnection();
				PreparedStatement displayQuery = connection.prepareStatement(sqlCommand);) {
				if(quizid!=0)
					displayQuery.setInt(1,quizid);				
				ResultSet displayQuestions = displayQuery.executeQuery();
				
				while (displayQuestions.next()) {
					quizId = displayQuestions.getInt("QUIZ_ID");
					mcqId = displayQuestions.getInt("MCQ_ID");
					question = displayQuestions.getString("QUESTION");
					topic = displayQuestions.getString("TOPIC");
					difficulty = displayQuestions.getInt("DIFFCULTY");
					System.out.println(quizId+".\t"+mcqId+".\t"+question+".\t"+topic+".\t"+difficulty+".");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		

		public void displayStudentNameID() {
			// TODO Auto-generated method stub
			String sqlCommand = "SELECT ID,STUDENT_EMAIL_ID FROM STUDENT";
			int studentId;
			String studentName;
					
			try (Connection connection = getConnection();
				PreparedStatement displayQuery = connection.prepareStatement(sqlCommand);) {
				ResultSet displayStudNameID = displayQuery.executeQuery();
				
				while (displayStudNameID.next()) {
					studentId = displayStudNameID.getInt("ID");
					studentName = displayStudNameID.getString("STUDENT_EMAIL_ID");
					System.out.println(studentId+".\t"+studentName+".");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void checkAnswer(int selectedID) {
			// TODO Auto-generated method stub
			String sqlCommand = "SELECT QUIZ_ID, QUESTION_ID, QUESTION_ANSWERS, ISRIGHT FROM QUIZ_TAKEN_BY_STUDENT WHERE STUDENT_ID = ?";
			String checkAns = "SELECT ANSWER FROM QUIZ WHERE QUIZ_ID =? AND QUESTION_ID = ?";
			int quizId;
			int questionId;
			String answer;
			String isRight;
			String corrAnswer;		
			try (Connection connection = getConnection();
				PreparedStatement displayQuery = connection.prepareStatement(sqlCommand);) {
				displayQuery.setInt(1, selectedID);
				ResultSet getDetails = displayQuery.executeQuery();
				
				while (getDetails.next()) {
					quizId = getDetails.getInt("QUIZ_ID");
					questionId = getDetails.getInt("QUESTION_ID");
					answer = getDetails.getString("QUESTION_ANSWERS");
					isRight = getDetails.getString("ISRIGHT");
					if(isRight!=null)
					{
						PreparedStatement checkingFrAnswer = connection.prepareStatement(checkAns);
						checkingFrAnswer.setInt(1, quizId);
						checkingFrAnswer.setInt(2, questionId);
						ResultSet ansResultSet = checkingFrAnswer.executeQuery();
						while (ansResultSet.next()) {
							corrAnswer = ansResultSet.getString("ANSWER");
							System.out.println("the ans"+corrAnswer+" fdsf "+answer);
							if(corrAnswer.equals(answer))
							{
								updateIsRight("Y", selectedID, quizId, questionId);
							}
						}
					}
					System.out.println("Answers corrected!!!!");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		private void updateIsRight(String isRight, int studentId, int quizId, int questionId) {
			// TODO Auto-generated method stub
			String sqlCommand = "UPDATE QUIZ_TAKEN_BY_STUDENT SET ISRIGHT = ? WHERE STUDENT_ID = ? AND QUIZ_ID = ? AND QUESTION_ID = ?";
			String isRightValue = "N";
					
			try (Connection connection = getConnection();
				PreparedStatement displayQuery = connection.prepareStatement(sqlCommand);) {
				if(isRight!=null && "Y".equals(isRight))
				{
					isRightValue = "Y";
				}
				displayQuery.setString(1, isRightValue);
				displayQuery.setInt(2, studentId);
				displayQuery.setInt(3, quizId);
				displayQuery.setInt(4, questionId);
				displayQuery.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void updateQuestiontoDB(Quiz quiz, boolean isAMCQQuestion) {
			// TODO Auto-generated method stub
			
			String sqlCommand = "UPDATE QUIZ SET QUESTION = ?, ANSWER = ?, TOPIC= ? ,DIFFCULTY = ? WHERE QUIZ_ID=? AND QUESTION_ID = ?";
			if(isAMCQQuestion)
				sqlCommand = "UPDATE QUIZ SET QUESTION = ?, ANSWER = ?, TOPIC= ? ,DIFFCULTY = ? WHERE QUIZ_ID=? AND QUESTION_ID = ? AND MCQ_ID = ?";
		
			
			try (Connection connection = getConnection();
				PreparedStatement updateStatement = connection.prepareStatement(sqlCommand);) {
				
				updateStatement.setString(1,quiz.getQuestion());
				updateStatement.setString(2,quiz.getAnswer());
				updateStatement.setString(3,quiz.getTopic());
				updateStatement.setInt(4,quiz.getDifficulty());
				updateStatement.setInt(5,quiz.getQuizid());
				updateStatement.setInt(6,quiz.getQuestionId());				
				if(isAMCQQuestion)
					updateStatement.setInt(7, quiz.getMcq_id());
				
				updateStatement.executeUpdate();
				connection.commit();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void updateMCQQuestiontoDB(MCQQuestion mcq, int mcqId) {
			// TODO Auto-generated method stub
			String sqlCommand = "UPDATE MCQQUESTIONS SET option_a = ?, option_b = ?, option_c = ?, option_d= ? WHERE MCQ_ID = ?";
			
			try (Connection connection = getConnection();
				PreparedStatement updateStatement = connection.prepareStatement(sqlCommand);) {
				
				updateStatement.setString(1,mcq.getOption_a());
				updateStatement.setString(2,mcq.getOption_b());
				updateStatement.setString(3,mcq.getOption_c());
				updateStatement.setString(4,mcq.getOption_d());
				updateStatement.setInt(5,mcqId);				
				updateStatement.execute();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void deleteQuestion(int quizId, int questionId) {
			// TODO Auto-generated method stub
			String checkfrMcqQn = "SELECT MCQ_ID FROM QUIZ WHERE QUIZ_ID=? AND QUESTION_ID = ?";
			String deleteMcqOptions = "DELETE FROM MCQQUESTIONS WHERE MCQ_ID=?";
			String sqlCommand = "DELETE FROM QUIZ WHERE QUIZ_ID=? AND QUESTION_ID = ?";
			boolean isMcq = false;
			int mcqId = 0;
			try (Connection connection = getConnection();
				PreparedStatement selectStatement = connection.prepareStatement(checkfrMcqQn);) {
				selectStatement.setInt(1,quizId);
				selectStatement.setInt(2,questionId);
				ResultSet rs = selectStatement.executeQuery();
				while(rs.next())
				{
					mcqId = rs.getInt("MCQ_ID");
					if(mcqId!=0)
					{
						isMcq = true;
					}
						
				}
				if(isMcq)
				{
					PreparedStatement deleteMCQQn = connection.prepareStatement(deleteMcqOptions);
					deleteMCQQn.setInt(1,mcqId);
					deleteMCQQn.execute();
					connection.commit();
				
				}
				PreparedStatement deleteQuestion = connection.prepareStatement(sqlCommand);
				deleteQuestion.setInt(1,quizId);
				deleteQuestion.setInt(2,questionId);
				deleteQuestion.execute();
				connection.commit();
				
				System.out.println("Question is deleted...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

}
