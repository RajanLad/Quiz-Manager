;             
CREATE USER IF NOT EXISTS SA SALT '39c68e84975a879c' HASH 'd52ee62b14898b9fed0929dfe442b6cf37847266fd6254d707543defaf25536e' ADMIN;           
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_296DF9C6_CE7E_459D_AB64_C8D0CFC74FD3 START WITH 37 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_3357D42B_9415_41EE_8C67_00110FE57C57 START WITH 33 BELONGS_TO_TABLE;   
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_FE2D587D_710E_4BB1_A9FB_D88B55D324D2 START WITH 37 BELONGS_TO_TABLE;   
CREATE CACHED TABLE PUBLIC.MCQQUESTIONS(
    MCQ_ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_FE2D587D_710E_4BB1_A9FB_D88B55D324D2) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_FE2D587D_710E_4BB1_A9FB_D88B55D324D2,
    OPTION_A VARCHAR(255),
    OPTION_B VARCHAR(255),
    OPTION_C VARCHAR(255),
    OPTION_D VARCHAR(255)
);               
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.MCQQUESTIONS;            
INSERT INTO PUBLIC.MCQQUESTIONS(MCQ_ID, OPTION_A, OPTION_B, OPTION_C, OPTION_D) VALUES
(1, 'fd', 'Hello', 'ff', 'ff'),
(33, 'abc', 'pqr', 'xyz', 'def'),
(34, 'A java array is always an object', 'Length of array can be changed after creation of array', 'Arrays in Java are always allocated on heap', 'none'),
(35, 'test1', 'test2', 'test3', 'test4'),
(36, 'one class inheriting from more super classes', 'more classes inheriting from one super class', 'more classes inheriting from more super classes', 'None of the above');              
CREATE CACHED TABLE PUBLIC.QUIZ(
    QUIZ_ID INT,
    QUESTION_ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_296DF9C6_CE7E_459D_AB64_C8D0CFC74FD3) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_296DF9C6_CE7E_459D_AB64_C8D0CFC74FD3,
    MCQ_ID INT,
    QUESTION VARCHAR(255),
    ANSWER VARCHAR(255),
    TOPIC VARCHAR(255),
    DIFFCULTY VARCHAR(255)
);   
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.QUIZ;    
INSERT INTO PUBLIC.QUIZ(QUIZ_ID, QUESTION_ID, MCQ_ID, QUESTION, ANSWER, TOPIC, DIFFCULTY) VALUES
(1, 1, NULL, 'What is local variable?', 'Variables defined inside methods, constructors or blocks are called local variables. The variable will be declared and initialized within the method and it will be destroyed when the method has completed.', 'Java Basics', '4'),
(1, 33, NULL, 'What is singleton class and how can we make a class singleton?', 'Singleton class is a class whose only one instance can be created at any given time, in one JVM. A class can be made singleton by making its constructor private.', 'Theory', '4'),
(2, 34, NULL, 'Which of the following is FALSE about arrays on Java', 'Length of array can be changed after creation of array', 'arrays', '4'),
(1, 35, NULL, 'testQuest', 'testans', 'test', '1'),
(1, 36, 36, 'Multiple inheritance means,', 'one class inheriting from more super classes', 'Inheritance', '4');   
CREATE CACHED TABLE PUBLIC.STUDENT(
    ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_3357D42B_9415_41EE_8C67_00110FE57C57) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_3357D42B_9415_41EE_8C67_00110FE57C57,
    STUDENT_EMAIL_ID VARCHAR(255),
    HASBEENCHECKED BOOLEAN
);       
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.STUDENT; 
INSERT INTO PUBLIC.STUDENT(ID, STUDENT_EMAIL_ID, HASBEENCHECKED) VALUES
(1, 'rajanrlad71293@gmail.com', NULL),
(2, 'rajanrlad71293@gmail.com', NULL),
(3, 'rajanrlad71293@gmail.com', NULL);               
CREATE CACHED TABLE PUBLIC.QUIZ_TAKEN_BY_STUDENT(
    QUIZ_ID INT,
    QUESTION_ID INT,
    QUESTION VARCHAR(255),
    QUESTION_ANSWERS VARCHAR(255),
    STUDENT_ID INT,
    ISRIGHT BOOLEAN
);       
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.QUIZ_TAKEN_BY_STUDENT;   
CREATE CACHED TABLE PUBLIC.ADMIN(
    ADMIN_ID INT,
    PASSWORD VARCHAR2(200)
);          
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.ADMIN;   
INSERT INTO PUBLIC.ADMIN(ADMIN_ID, PASSWORD) VALUES
(1234, '1234');          
ALTER TABLE PUBLIC.QUIZ ADD CONSTRAINT PUBLIC.CONSTRAINT_2 UNIQUE(MCQ_ID);    
ALTER TABLE PUBLIC.QUIZ ADD CONSTRAINT PUBLIC.CONSTRAINT_26 FOREIGN KEY(MCQ_ID) REFERENCES PUBLIC.MCQQUESTIONS(MCQ_ID) NOCHECK;               
