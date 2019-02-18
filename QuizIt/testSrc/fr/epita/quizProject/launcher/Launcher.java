package fr.epita.quizProject.launcher;

import java.sql.Connection;
import java.util.Scanner;

import fr.epita.quizProject.datamodel.Administrator;
import fr.epita.quizProject.datamodel.MCQQuestion;
import fr.epita.quizProject.datamodel.Student;
import fr.epita.quizProject.datamodel.Quiz;

import fr.epita.quizProject.service.QuizJDBC_DAO;
import fr.epita.quizProject.service.StudentJDBC_DAO;

class CallOperations
{
	protected boolean authenticateAdministrator(QuizJDBC_DAO q_jdbc_dao,Scanner sc)
	{
		
		System.out.println("Please authenticate yourself. Enter id and Password");
		System.out.println("Enter id,maintenant");
		String adminid=sc.next();
		System.out.println("Enter password,maintenant");
		String adminpassword=sc.next();
		Administrator admin=new Administrator(adminid,adminpassword);
		admin.setId(adminid);
		admin.setPassword(adminpassword);
		if(q_jdbc_dao.authenticate(admin))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	protected void registerStudent(StudentJDBC_DAO s_jdbc_dao,Scanner sc)
	{
		System.out.println("Enter your email id");
		
		String temp_student_name=sc.next();
		
		Student student=new Student(temp_student_name);
		student.setName(temp_student_name);
		
		int id=s_jdbc_dao.create(student);
		//push to DB
		
		System.out.println("Hi , you are registered and your id is "+id+" , please don't forget");
	}
	
	protected void giveTheQuiz(StudentJDBC_DAO s_jdbc_dao,Scanner sc)
	{
		System.out.println("Enter Student ID");
		
		int enteredID=sc.nextInt();
		
		Student student=new Student();
		Quiz qz=new Quiz();
		
		student.setId(enteredID);
		
		if(s_jdbc_dao.authenticate(student)!=0)
		{
			System.out.println("Enter Quiz Number 1-10");
			int quiz_id=sc.nextInt();
			qz.setQuizid(quiz_id);
			s_jdbc_dao.readQuestions(qz);
		}
		else
		{
			System.out.println("You need to register");
		}
	}
	
 	protected void addQuestions(QuizJDBC_DAO q_jdbc_dao,Scanner sc)
	{
		System.out.println("Type of question 1) Open Question 2) MCQ Question");
		if(sc.nextInt()==1) {
			sc.nextLine();
			System.out.println("Enter quiz id from 1 to 10");
			int quizid=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Question");
			String question=sc.nextLine();
			System.out.println("Enter Answer");
			String answer=sc.nextLine();
			System.out.println("Enter Topic of question");
			String topic=sc.nextLine();
			System.out.println("Enter difficulty of question");
			int difficulty=sc.nextInt();
			Quiz qz=new Quiz();
			MCQQuestion mcq=new MCQQuestion();
			qz.setQuizid(quizid);
			qz.setQuestion(question);
			qz.setAnswer(answer);
			qz.setTopic(topic);
			qz.setDifficulty(difficulty);
			boolean id=q_jdbc_dao.addQuestiontoDB(qz,false);
		}
		else
		{
			sc.nextLine();
			System.out.println("Enter quiz id from 1 to 10");
			int quizid=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter MCQ Question");
			String question=sc.nextLine();
			System.out.println("Enter Option A");
			String option_a=sc.nextLine();
			System.out.println("Enter Option B");
			String option_b=sc.nextLine();
			System.out.println("Enter Option C");
			String option_c=sc.nextLine();
			System.out.println("Enter Option D");
			String option_d=sc.nextLine();
			System.out.println("Enter Answer");
			String answer=sc.nextLine();
			System.out.println("Enter Topic of question");
			String topic=sc.nextLine();
			System.out.println("Enter difficulty of question");
			int difficulty=sc.nextInt();
			
			MCQQuestion mcq=new MCQQuestion();
			
			mcq.setOption_a(option_a);
			mcq.setOption_b(option_b);
			mcq.setOption_c(option_c);
			mcq.setOption_d(option_d);
			
			int mcq_id=q_jdbc_dao.addMCQQuestiontoDB(mcq);
			System.out.println(mcq_id);
			
			Quiz qz=new Quiz();
			qz.setQuizid(quizid);
			qz.setMcq_id(mcq_id);
			qz.setQuestion(question);
			qz.setAnswer(answer);
			qz.setTopic(topic);
			qz.setDifficulty(difficulty);
			boolean id=q_jdbc_dao.addQuestiontoDB(qz,true);
		}
	}

}

public class Launcher {
	public static void main(String args[])
	{
		//initialization
		Scanner sc=new Scanner(System.in);
		CallOperations co=new CallOperations();
		StudentJDBC_DAO s_jdbc_dao=new StudentJDBC_DAO();
		QuizJDBC_DAO q_jdbc_dao=new QuizJDBC_DAO();
		
		System.out.println("Are you Admin, press 1. If not press 2 for etudiant");
		
		int temp_selection_type_user=sc.nextInt();
		
		if(temp_selection_type_user==1)
		{
			if(co.authenticateAdministrator(q_jdbc_dao,sc))
			{
				String choice = null;
		        do {
		        	
					System.out.println("Choose the choice number");
		            choice = sc.nextLine();
		            switch (choice) {
		            case "1":
		            	co.addQuestions(q_jdbc_dao, sc);
		            	break;
		            case "2":

		            	System.out.println("2) kugjku");
		            	break;
		            case "3":

		            	System.out.println("3) fkh");
		            	break;
		            case "4":
		            	System.out.println("4)vmht");
		            	break;
		            default:
		            	System.out.println("5)acdvfvfs");
		            	break;
		            } // end of switch
		            
					System.out.println("1) Add Question");
					System.out.println("2) See Questions");
					System.out.println("3) Check Answers");
					System.out.println("4) Update Questions");
					System.out.println("5) Quit");

		        } while (!choice.equals("4")); 
			}
			else
			{
				System.out.println("sfvsdgfvf");
			}
		}
		else if(temp_selection_type_user==2)
		{
			String choice = null;
	        do {
				System.out.println("Choose the choice number");
	            choice = sc.nextLine();
	            switch (choice) {
	            case "1":
	    	        //co.registerStudent(s_jdbc_dao);
	            	co.registerStudent(s_jdbc_dao,sc);
	            	break;
	            case "2":
	    	        //co.registerStudent(s_jdbc_dao);
	            	co.giveTheQuiz(s_jdbc_dao, sc);
	            	break;
	            case "3":
	    	        //co.registerStudent(s_jdbc_dao);
	            	System.out.println("3) fkh");
	            	break;
	            case "4":
	    	        //co.registerStudent(s_jdbc_dao);
	            	System.out.println("4)vmht");
	            	break;
	            default:
	    	        //co.registerStudent(s_jdbc_dao);
	            	System.out.println("5)bffd");
	            	break;
	            } // end of switch
	            
				System.out.println("1) Create User Account");
				System.out.println("2) Start Quiz");
				System.out.println("3) See Results");
				System.out.println("4) Quit");
				
	        } while (!choice.equals("4"));   
		}
	}
	

	
}
