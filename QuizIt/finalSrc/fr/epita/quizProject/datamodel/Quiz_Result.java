package fr.epita.quizProject.datamodel;

import java.util.Set;

public class Quiz_Result 
{
//	QUIZ_ID  	QUESTION_ID  	QUESTION  	QUESTION_ANSWERS  	STUDENT_ID  	ISRIGHT  
	
	int quiz_id;
	int question_id;
	String question;
	String answer;
	public int getQuiz_id() {
		return quiz_id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public void setQuiz_id(int quiz_id) {
		this.quiz_id = quiz_id;
	}

	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public boolean isRight() {
		return isRight;
	}
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
	int student_id;
	boolean isRight;

}
