package fr.epita.quizProject.datamodel;

public class Quiz 
{
	int quizid;
	
	int mcq_id;



	String question;

	String answer;

	String topic;

	int difficulty;
	
	int questionId;
	
	public int getMcq_id() {
		return mcq_id;
	}

	public void setMcq_id(int mcq_id) {
		this.mcq_id = mcq_id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public Quiz()
	{
		
	}
	
	public Quiz(int quizid, String question, String answer, String topic, int difficulty) {
		super();
		this.quizid = quizid;
		this.question = question;
		this.answer = answer;
		this.topic = topic;
		this.difficulty = difficulty;
	}

	public int getQuizid() {
		return quizid;
	}

	public void setQuizid(int quizid) {
		this.quizid = quizid;
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	
}
