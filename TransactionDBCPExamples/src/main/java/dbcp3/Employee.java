package dbcp3;

public class Employee {
	private int empNo;
	private String empName;
	private String job;
	private double salary;
	
	public Employee(int empNo, String empName, String job, double sal) {
		super();
		this.empNo = empNo;
		this.empName = empName;
		this.job = job;
		this.salary = sal;
	}
	
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double sal) {
		this.salary = sal;
	}
}
