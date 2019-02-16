package fr.epita.quizProject.service;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.epita.quizProject.datamodel.Student;

public class StudentJDBC_DAO 
{
	public void authenticate(Student student) {
		String sqlCommand = "SELECT id FROM STUDENT where id=(?)";
		try (Connection connection = getConnection();
			PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);) {
			
			insertStatement.setString(1,student.getName());
			
			insertStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public void read(Student student) {
		String sqlCommand = "INSERT INTO Student(student_email_id) values (?)";
		try (Connection connection = getConnection();
			PreparedStatement insertStatement = connection.prepareStatement(sqlCommand);) {
			
			insertStatement.setString(1,student.getName());
			
			insertStatement.execute();

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
