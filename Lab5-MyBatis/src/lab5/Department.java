package lab5;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int deptNo;			// 부서번호
	private String deptName;	// 부서명
	private int mgrNo;			// 관리자 사번
	private int numOfEmps;		// 소속사원 수
	public List<Employee> empList; // 소속 사원
	
	public List<Employee> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Employee> empList) {
        this.empList = empList;
    }

    public Department() {}		// 기본 생성자
	
	public Department(int deptNo, String deptName, int mgrNo, int numOfEmps) {
		super();
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.mgrNo = mgrNo;
		this.numOfEmps = numOfEmps;
	}
	
	// getters & setters	
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getMgrNo() {
		return mgrNo;
	}
	public void setMgrNo(int mgrNo) {
		this.mgrNo = mgrNo;
	}
	public int getNumOfEmps() {
		return numOfEmps;
	}
	public void setNumOfEmps(int numOfEmps) {
		this.numOfEmps = numOfEmps;
	}
}
