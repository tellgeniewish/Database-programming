package lab4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lab3Sol.DeptNotFoundException;
import util.JDBCUtil;

public class CompanyDao {
	private JDBCUtil jdbcUtil = null;	// JDBCUtil 필드 선언
		
	public CompanyDao() {				// 생성자 
		jdbcUtil = new JDBCUtil();		// JDBCUtil 객체 생성
		
/*		// JDBCUtil 이용 시 불필요: DBCP 활용		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
*/
	}

/*	// JDBCUtil 이용 시 불필요: 내부적으로 connection 생성 및 사용 
	private Connection getConnection() {
		String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";		
		String user = "scott2";
		String passwd = "TIGER";

		// DBMS와의 연결 획득
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}	 
		return conn;
	}
*/
	
    public Department findDeptByName(String deptName) {
    	/* 실습 #3의 printDeptInfo()를 변형. 
    	 * 부서정보 검색 후 Department DTO를 생성하고 검색 결과를 저장해서 return함
    	 * (검색 결과가 없을 경우 null을 반환) 
    	 */
            ResultSet rs = null;
            
            StringBuffer query = new StringBuffer();
            query.append("SELECT deptno, manager, COUNT(empno) AS numOfEmps ");
            query.append("FROM EMP0000 JOIN DEPT0000 USING (deptno) ");
            query.append("WHERE dname = ? ");
            query.append("GROUP BY deptno, manager");
            
            jdbcUtil.setSqlAndParameters(query.toString(), new Object[]{deptName}); // JDBCUtil에 질의문과 파라미터 설정   
            
            try {
                rs = jdbcUtil.executeQuery();  // 질의 실행 // JDBCUtil에 질의문과 파라미터 설정   
                
                if (rs.next()) {  // 질의 결과 존재 (결과는 최대 하나의 행)
                    Department dept = new Department();
                    dept.setDeptName(deptName);
                    dept.setDeptNo(rs.getInt("deptno"));
                    dept.setMgrNo(rs.getInt("manager"));
                    dept.setNumOfEmps(rs.getInt("numOfEmps"));
                    return dept;
                }
                return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 등 해제
		}
    	return null;
    } 

    public List<Employee> getAllEmpsInDept(int deptNo) {
    	/* 실습 #3의 printAllEmpsInDept()를 변형.
    	 * ArrayList<Employee> 타입 객체를 생성한 후, 검색된 각 사원 정보에 대해
    	 * Employee DTO를 생성 및 저장하고 List 객체에 추가함. List 객체를 반환함
    	 */
    	List<Employee> empList = new ArrayList<Employee>();    	
    	
    	ResultSet rs = null;

        String query = "SELECT empno, ename, job, hiredate, sal, comm, dname " +
                       "FROM EMP0000 JOIN DEPT0000 USING (deptno) " +
                       "WHERE deptno = ?";
        
        jdbcUtil.setSqlAndParameters(query, new Object[]{deptNo});

        try {
            rs = jdbcUtil.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpNo(rs.getInt("empno"));
                emp.setEmpName(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setHiredate(rs.getDate("hiredate").toLocalDate());
                emp.setSal(rs.getDouble("sal"));
                emp.setComm(rs.getDouble("comm"));
                emp.setDeptName(rs.getString("dname"));
                empList.add(emp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
    	
    	return empList;
    }

    public Employee replaceManagerOfDept(Appointment appo) {
    	/* 실습 #3의 replaceManagerOfDept()와 유사하나, 매개변수 전달을 위해 Appointment
    	 * DTO를 이용하고, 실행 결과로 기존 관리자 사원 정보를 포함하는 Employee DTO를 반환함
    	 */    	
        ResultSet rs = null;
        
        try {
            
        	Employee oldMgr = new Employee();
        	
        	String deptName = appo.getDeptName();
            int newMgrNo = appo.getNewMgrNo();
            double newMgrComm = appo.getNewMgrComm();

            // 1. 기존 관리자 사번 조회
            String findCurrentMgrQuery = "SELECT manager FROM DEPT0000 WHERE dname = ?";
            jdbcUtil.setSqlAndParameters(findCurrentMgrQuery, new Object[]{deptName});

            rs = jdbcUtil.executeQuery();
            int oldMgrNo = 0;
            if (rs.next()) {
                oldMgrNo = rs.getInt("manager");
            }

            // 2. 기존 관리자 정보 가져와 oldMgr에 저장
            oldMgr = findEmpInfo(oldMgrNo);
            if (oldMgr == null) {
                System.out.println("기존 관리자를 찾을 수 없습니다.");
                return null;
            }

            // 3. 새로운 관리자로 부서의 관리자 정보 업데이트
            String updateManagerQuery = "UPDATE DEPT0000 SET manager = ? WHERE dname = ?";
            jdbcUtil.setSqlAndParameters(updateManagerQuery, new Object[]{newMgrNo, deptName});
            jdbcUtil.executeUpdate();

            // 4. 새로운 관리자 보직 수당 업데이트
            String updateEmployeeQuery = "UPDATE EMP0000 SET comm = NVL(comm, 0) + ? WHERE empno = ?";
            jdbcUtil.setSqlAndParameters(updateEmployeeQuery, new Object[]{newMgrComm, newMgrNo});
            jdbcUtil.executeUpdate();
        	
        	return oldMgr;
        } catch (Exception ex) {
        	jdbcUtil.rollback();	// 트랜잭션 rollback 실행
        	ex.printStackTrace();
        } finally {
            jdbcUtil.commit();		// 트랜잭션 commit 실행
            jdbcUtil.close();
        }
    	return null;
    }
    
    public Employee findEmpInfo(int empNo) {
    	/* 실습 #3의 printEmpInfo()를 변형
    	 * 주어진 사번에 해당하는 사원 정보를 검색 후 Employee DTO를 생성 및 저장하고 반환함
         * (검색 결과가 없을 경우 null을 반환) 
         */ 
        ResultSet rs = null;
        String query = "SELECT empno, ename, job, hiredate, sal, comm, dname " +
                       "FROM EMP0000 JOIN DEPT0000 USING (deptno) " +
                       "WHERE empno = ?";

        jdbcUtil.setSqlAndParameters(query, new Object[]{empNo});

        try {
            rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpNo(rs.getInt("empno"));
                emp.setEmpName(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setHiredate(rs.getDate("hiredate").toLocalDate());
                emp.setSal(rs.getDouble("sal"));
                emp.setComm(rs.getDouble("comm"));
                emp.setDeptName(rs.getString("dname"));
                return emp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        
    	return null;        
    }
} 

