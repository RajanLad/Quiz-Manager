package fr.epita.quizProject.launcher;

import java.sql.Connection;
import java.util.*;
import java.util.Map.Entry;

import fr.epita.quizProject.datamodel.Administrator;
import fr.epita.quizProject.datamodel.MCQQuestion;
import fr.epita.quizProject.datamodel.Student;
import fr.epita.quizProject.datamodel.Quiz;
import fr.epita.quizProject.datamodel.Quiz_Result;
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
		Quiz_Result qr=new Quiz_Result();
		MCQQuestion mcq=new MCQQuestion();
		
		student.setStudentId(enteredID);
		
		if(s_jdbc_dao.authenticate(student)!=0)
		{
			System.out.println("Enter Quiz Number 1-10");
			int quiz_id=sc.nextInt();
			qz.setQuizid(quiz_id);
			HashMap<Integer,HashMap<String,List<String>>> questionSet=s_jdbc_dao.readQuestions(qz);
			
			//System.out.println(questionSet.toString());
			
			for (Entry<Integer, HashMap<String,List<String>>> qSet : questionSet.entrySet()) {
				
			    qz.setQuestionId(qSet.getKey());
			    qr.setQuestion_id(qSet.getKey());
			    
			    for (Entry<String, List<String>> quest : qSet.getValue().entrySet()) {
				    qz.setQuestion(quest.getKey());
					System.out.println("THe Question is : ");
					System.out.println(quest.getKey());
					qr.setQuestion(quest.getKey());
					
				    if(!quest.getValue().isEmpty()) {
			    	System.out.println("THe Options are  : ");
					System.out.println(quest.getValue().get(0));	
					System.out.println(quest.getValue().get(1));	
					System.out.println(quest.getValue().get(2));	
					System.out.println(quest.getValue().get(3));	
				    mcq.setOption_a(quest.getValue().get(0));
				    mcq.setOption_a(quest.getValue().get(1));
				    mcq.setOption_a(quest.getValue().get(2));
				    mcq.setOption_a(quest.getValue().get(3));
				    }
			    
			    }
			    
			    System.out.print("Answer : ");
			    String answer=sc.next();
			    qr.setAnswer(answer);
			    
			    s_jdbc_dao.setResults(qz, student, qr);
			}
			
			

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

	public void viewQuestions(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
				// TODO Auto-generated method stub
		System.out.println("Do you wish to view all questions or a particular question.\n Select 1) To View All Questions \n 2) To View a particular question");
		if(sc.nextInt()==1) {
			sc.nextLine();
			q_jdbc_dao.displayAllQuestions(0);
		}
		else if(sc.nextInt()==2) {
			sc.nextLine();
			System.out.println("Enter quiz id from 1 to 10");
			int quizid=sc.nextInt();
			q_jdbc_dao.displayAllQuestions(quizid);
		}
		else
		{
			System.out.println("Please select a valid option either 1 or 2");
		}
	}

	public void checkAnswer(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
		// TODO Auto-generated method stub
		q_jdbc_dao.displayStudentNameID();
		System.out.println("Please enter the Id of the student");
		int selectedID = sc.nextInt();
		sc.nextLine();
		q_jdbc_dao.checkAnswer(selectedID);
		
	}

	public void updateQuestion(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
		// TODO Auto-generated method stub
		
		System.out.println("Type of question to update 1) Open Question 2) MCQ Question");
		if(sc.nextInt()==1) {
			sc.nextLine();
			System.out.println("Enter quiz id to update");
			int quizid=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Question id to update");
			int questionId = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Question to update");
			String question=sc.nextLine();
			System.out.println("Enter Answer to update");
			String answer=sc.nextLine();
			System.out.println("Enter Topic of question to update");
			String topic=sc.nextLine();
			System.out.println("Enter difficulty of question to update");
			int difficulty=sc.nextInt();
			sc.nextLine();
			Quiz qz=new Quiz();
			qz.setQuizid(quizid);
			qz.setQuestion(question);
			qz.setAnswer(answer);
			qz.setTopic(topic);
			qz.setDifficulty(difficulty);
			qz.setQuestionId(questionId);
			q_jdbc_dao.updateQuestiontoDB(qz,false);
		}
		else
		{
			sc.nextLine();
			System.out.println("Enter quiz id to update");
			int quizid=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Question id to update");
			int questionId=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter MCQ id to update");
			int mcqId=sc.nextInt();
			sc.nextLine();
			System.out.println("Enter MCQ Question to update");
			String question=sc.nextLine();
			System.out.println("Enter Option A");
			String option_a=sc.nextLine();
			System.out.println("Enter Option B");
			String option_b=sc.nextLine();
			System.out.println("Enter Option C");
			String option_c=sc.nextLine();
			System.out.println("Enter Option D");
			String option_d=sc.nextLine();
			System.out.println("Enter Answerto Update");
			String answer=sc.nextLine();
			System.out.println("Enter Topic of questionto update");
			String topic=sc.nextLine();
			System.out.println("Enter difficulty of question to update");
			int difficulty=sc.nextInt();
			sc.nextLine();

			MCQQuestion mcq=new MCQQuestion();
			
			mcq.setOption_a(option_a);
			mcq.setOption_b(option_b);
			mcq.setOption_c(option_c);
			mcq.setOption_d(option_d);
			
			q_jdbc_dao.updateMCQQuestiontoDB(mcq, mcqId);
			
			Quiz qz=new Quiz();
			qz.setQuizid(quizid);
			qz.setMcq_id(mcqId);
			qz.setQuestion(question);
			qz.setAnswer(answer);
			qz.setTopic(topic);
			qz.setDifficulty(difficulty);
			qz.setQuestionId(questionId);
			q_jdbc_dao.updateQuestiontoDB(qz,true);
		}

	}

	public void deleteQuestion(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("Please enter the Quiz id of the question to delete");
		int quizId = sc.nextInt();
		sc.nextLine();
		System.out.println("Please enter the Question id of the question to delete");
		int questionId = sc.nextInt();
		sc.nextLine();
		q_jdbc_dao.deleteQuestion(quizId, questionId);
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
		            	co.checkAnswer(q_jdbc_dao, sc);
		            	System.out.println("3) fkh");
		            	break;
		            case "4":
		            	co.updateQuestion(q_jdbc_dao, sc);
		            	System.out.println("4)vmht");
		            	break;
		            case "5":
		            	co.deleteQuestion(q_jdbc_dao, sc);
		            	System.out.println("4)vmht");
		            	break;
		            default:
		            	System.out.println("5)acdvfvfs");
		            	break;
		            } // end of switch
		            
					System.out.println("1) Add Question");
					System.out.println("2) See Questions");
					System.out.println("3) Check Answers");
					System.out.println("4) Update Question");
					System.out.println("5) Delete Question");

		        } while (!choice.equals("5")); 
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
