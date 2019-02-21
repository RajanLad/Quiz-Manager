# Quiz-Manager

## Introduction 
Quiz manager – This project is used to make students take quiz on any topic. This helps the teacher to assess the level of the students. The application has got two modules:
- Admin (Teacher)
- Student

The two modules has got different functionalities. While only teacher module will be able to modify or add questions, the student module will be allowed to take the exam as well as check their scores.


#### It is a quiz manager , wtih three modules 

## 1) Launcher
- Launcher.java
## 2) Data Model ##
- Administrator.java
- MCQQuestion.java
- Quiz_Result.java
- Quiz.java
- Student.java
## 3) Data Access Objects
- QuizJDBC_DAO.java
- StudentJDBC_DAO.java

# Functions for Admin (Teacher) ad Student

The corresponding functions are called from **Launcher.java** in class **CallOperation** who then accesses DataModel and DAO's

## Admin (Teacher)
#### Launcher.java
```
protected boolean authenticateAdministrator(QuizJDBC_DAO q_jdbc_dao,Scanner sc){
    ...
}
```
```
protected void addQuestions(QuizJDBC_DAO q_jdbc_dao,Scanner sc){
    ...
}
```
```
public void viewQuestions(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
    ...
}
```
```
public void checkAnswer(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
    ...
}
```
```
public void updateQuestion(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
    ...
}
```
```
public void deleteQuestion(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
    ...
}
```

#### QuizJDBCDAO.java

```
  public boolean authenticate(Administrator admin) {
    ...
  }

  public boolean addQuestiontoDB(Quiz quiz,boolean isAMCQQuestion) {
    ...

  }

  public int addMCQQuestiontoDB(MCQQuestion mcq) {
    ...
  }


  public void displayAllQuestions(int quizid) {
    ...
  }

  public void displayStudentNameID() {
    ...
  }

  public void checkAnswer(int selectedID) {
    ...

  }

  private void updateIsRight(String isRight, int studentId, int quizId, int questionId) {
    ...
  }

  public void updateQuestiontoDB(Quiz quiz, boolean isAMCQQuestion) {
    ...
  }

  public void updateMCQQuestiontoDB(MCQQuestion mcq, int mcqId) {
    ...
  }

  public void deleteQuestion(int quizId, int questionId) {
    ...
  }

  public int checkResults(int student_id){
    ...
  }
```

## Student
#### Launcher.java

```
protected void registerStudent(StudentJDBC_DAO s_jdbc_dao,Scanner sc){
    ...
}
```
```
protected void giveTheQuiz(StudentJDBC_DAO s_jdbc_dao,Scanner sc){
    ...
}	
```
```
public void checkTheScore(QuizJDBC_DAO q_jdbc_dao, Scanner sc) {
    ...
}

```
#### StudentJDBC_DAO

```
public int authenticate(Student student) {
		...

	}
	
	public int create(Student student) {
		...

	}
	
	public HashMap<Integer,HashMap<String,List<String>>> readQuestions(Quiz quiz) {
		...

	}
	
	public void update(Student student) {
         ...

	}
	
	public void setResults(Quiz qz,Student st,Quiz_Result qr){
        ...
	}
  
  ```

## DB Tables and their fields
```
create table Student 
(
id int auto_increment, 
studentname varchar(255)
);
```
```
CREATE TABLE Quiz_Taken_By_Student
(
Quiz_ID INT, 
Question_id INT,
Question VARCHAR(255),
Question_Answers VARCHAR(255),
Student_ID int,
FOREIGN KEY (Student_ID ) REFERENCES Student(Student_ID)
);
```
```
CREATE TABLE QUIZ
(
QUIZ_ID int, 
Question_id int auto_increment,
MCQ_id int unique null,
Question varchar(255),
Answer varchar(255),
topic varchar(255),
diffculty varchar(255),

FOREIGN KEY (MCQ_id ) REFERENCES MCQQUESTIONS(MCQ_id )
);
```
```
CREATE TABLE MCQQuestions
(
MCQ_id int auto_increment,
option_a varchar(255),
option_b varchar(255),
option_c varchar(255),
option_d varchar(255)
);
```


