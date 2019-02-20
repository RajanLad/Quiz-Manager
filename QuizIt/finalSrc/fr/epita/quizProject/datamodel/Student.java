package fr.epita.quizProject.datamodel;

public class Student 
{
	private String name;
	private int id;
	
	public Student()
	{
		
	}
	
	public Student(String name)
	{
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return name;
	}
	
	public int getStudentId() {
		return id;
	}

	public void setStudentId(int id) {
		this.id = id;
	}
}
