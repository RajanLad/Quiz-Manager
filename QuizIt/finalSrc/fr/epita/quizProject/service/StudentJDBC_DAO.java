package fr.epita.quizProject.service;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

import fr.epita.quizProject.datamodel.Student;
import fr.epita.quizProject.datamodel.Quiz;
import fr.epita.quizProject.datamodel.Quiz_Result;

public class StudentJDBC_DAO 
{
	public int authenticate(Student student) {
		String sqlCommand = "SELECT id FROM STUDENT where id=(?)";
		int id=0;
		try (Connection connection = getConnection();
			PreparedStatement selectStatement = connection.prepareStatement(sqlCommand);) {
			
			selectStatement.setInt(1,student.getStudentId());
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
	
	public HashMap<Integer,HashMap<String,List<String>>> readQuestions(Quiz quiz) {
//		List<HashMap<String,List<String>>>
		String sqlCommand = "SELECT QUESTION_ID,QUESTION,MCQ_ID FROM QUIZ where QUIZ_ID=(?) ORDER by QUESTION_ID";
		String sqlCommandForMCQQuestions = "SELECT * FROM MCQQUESTIONS where MCQ_ID=(?)";
		
		HashMap<Integer,HashMap<String,List<String>>> questionSet=new HashMap<>();
		
		HashMap<String,List<String>> questionHashMap=new HashMap<>();
		
		List<String> mcq_options=new ArrayList<>();
		
		try (Connection connection = getConnection();
			PreparedStatement selectStatement = connection.prepareStatement(sqlCommand);
			PreparedStatement selectStatementMCQ = connection.prepareStatement(sqlCommandForMCQQuestions);) {
			selectStatement.setInt(1,quiz.getQuizid());
			ResultSet rs = selectStatement.executeQuery();
			while (rs.next()) {
					selectStatementMCQ.setInt(1,rs.getInt("MCQ_ID"));
					ResultSet rsMCQ = selectStatementMCQ.executeQuery();
					if(!rs.isBeforeFirst())
					{	
					while (rsMCQ.next()) {
							mcq_options=new ArrayList<>();
							mcq_options.add(rsMCQ.getString("OPTION_A"));
							mcq_options.add(rsMCQ.getString("OPTION_B"));
							mcq_options.add(rsMCQ.getString("OPTION_C"));
							mcq_options.add(rsMCQ.getString("OPTION_D"));
					}

					}
					else
					{
						mcq_options=new ArrayList<>();
						mcq_options.add("0");
						mcq_options.add("0");
						mcq_options.add("0");
						mcq_options.add("0");
					}

				questionHashMap.put(rs.getString("QUESTION"), mcq_options);
				for (Entry<String, List<String>> entry : questionHashMap.entrySet()) {
				    questionSet.put(rs.getInt("QUESTION_ID"), questionHashMap);
				}
				questionSet.put(rs.getInt("QUESTION_ID"), questionHashMap);
				questionHashMap=new HashMap<>();
			}
			return questionSet;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return questionSet;
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
	
	public void setResults(Quiz qz,Student st,Quiz_Result qr)
	{
		String sqlCommand = "INSERT INTO QUIZ_TAKEN_BY_STUDENT(QUIZ_ID,QUESTION_ID,QUESTION,QUESTION_ANSWERS,STUDENT_ID) VALUES (?,?,?,?,?)";
		
		int id=0;
		
		try (Connection connection = getConnection();
			PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);
				) {
			
			insertStatement.setInt(1,qz.getQuizid());
			insertStatement.setInt(2,qr.getQuestion_id());
			insertStatement.setString(3,qr.getQuestion());
			insertStatement.setString(4,qr.getAnswer());
			insertStatement.setInt(5,st.getStudentId());
			
			insertStatement.execute();
			
//			selectStatement.setString(1,mcq.getOption_a());
//			ResultSet rs = selectStatement.executeQuery();
//			
//			while (rs.next()) {
//				id = rs.getInt("MCQ_ID");
//			}
//			
//			return id;
			

		} catch (SQLException e) {
			e.printStackTrace();
			//return 0;
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
