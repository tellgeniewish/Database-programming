package lab3Sol;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Lab3Sol {

    public Lab3Sol() {      // 생성자
        // JDBC 드라이버 로딩
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");   
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    private static Connection getConnection() {
        String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";      
		// String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String user = "dbp2024";
        String passwd = "TIGER";

        // DBMS와의 연결 생성
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }    
        return conn;
    }
    
    public static void printDeptInfo(String deptName) throws DeptNotFoundException {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT deptno, manager, COUNT(empno) AS numOfEmps ");
        query.append("FROM EMP0000 JOIN DEPT0000 USING (deptno) ");
        query.append("WHERE dname = ? ");
        query.append("GROUP BY deptno, manager");
        
        conn = getConnection();     // DBMS와의 연결 획득 
        try {
            pStmt = conn.prepareStatement(query.toString());    // Connection에서 PreparedStatement 객체 생성
            pStmt.setString(1, deptName);   // PreparedStatement에 매개변수 값 설정
            rs = pStmt.executeQuery();  // 질의 실행
            
            if (rs.next()) {  // 질의 결과 존재 (결과는 최대 하나의 행)
                System.out.println("<부서정보>");
                System.out.println("부서번호: " + rs.getInt("deptno"));
                System.out.println("부서명: " + deptName);
                System.out.println("관리자사번: " + rs.getInt("manager"));
                System.out.println("사원 수: " + rs.getInt("numOfEmps"));
                System.out.println();
            }           
            else {
            	throw new DeptNotFoundException(deptName + "은 존재하지 않는 부서입니다.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {     // 자원 반납
            if (rs != null) {
                try { 
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (pStmt != null) { 
                try { 
                    pStmt.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            } 
            if (conn != null) { 
                try { 
                    conn.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }   
    }
    
    public static void printAllEmpsInDept(String deptName) {
        Connection conn = null;
        PreparedStatement pStmt = null;     
        ResultSet rs = null;        
        StringBuffer query = new StringBuffer();
        query.append("SELECT empno, ename, job, sal, comm ");
        query.append("FROM EMP0000 JOIN DEPT0000 USING (deptno) ");
        query.append("WHERE dname = ?");                
        
        conn = getConnection();     // DBMS와의 연결 획득 
        try {
        	pStmt = conn.prepareStatement(query.toString());    
            pStmt.setString(1, deptName);           
            rs = pStmt.executeQuery();
            
            System.out.println("<사원정보>");
            System.out.println("사번        이름           직무          급여         수당");
            System.out.println("------------------------------------------------------");
            while (rs.next()) {     // 커서를 통해 결과 행들을 하나씩 fetch
                int empNo = rs.getInt("empno");
                String empName = rs.getString("ename");
                String job = rs.getString("job");
                double sal = rs.getDouble("sal");
                double comm = rs.getDouble("comm");
                System.out.printf("%d %10s %15s %10.2f %10.2f\n", empNo, empName, job, sal, comm);
            }
            System.out.println();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try { 
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (pStmt != null) { 
                try { 
                    pStmt.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            } 
            if (conn != null) { 
                try { 
                    conn.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }   
    }
    
    public static int replaceManagerOfDept(String deptName, int newMgrNo, double newMgrComm) 
    		throws EmpNotFoundException {
        Connection conn = null;
        PreparedStatement pStmt = null; 
        ResultSet rs = null;        
        int oldMgrNo = 0;   // 기존 관리자 사번        
        
        conn = getConnection();     // DBMS와의 연결 획득 
        try {        	   
            // 기존 부서 관리자 사번 조회
            String query1 = "SELECT manager FROM DEPT0000 WHERE dname = ?";
            pStmt = conn.prepareStatement(query1);  
            pStmt.setString(1, deptName);           
            rs = pStmt.executeQuery();
            if (rs.next())                     
                oldMgrNo = rs.getInt("manager");
            pStmt.close();   // PreparedStatement 객체 해제
            
            // 기존 관리자 사원의 이름에서 "(M)" 삭제
            StringBuffer update1 = new StringBuffer();
            update1.append("UPDATE EMP0000 ");
            //update1.append("SET ename = SUBSTR(ename, 0, LENGTH(ename)-3) "); // 또는 REPLACE(ename, '(M)', '') 이용
            update1.append("SET ename = REPLACE(ename, '(M)', '') "); //   
            update1.append("WHERE empno = ?");              
            pStmt = conn.prepareStatement(update1.toString());  
            pStmt.setInt(1, oldMgrNo);          
            pStmt.executeUpdate();            	
            pStmt.close();    // PreparedStatement 객체 해제
            
            // 새 관리자 사원의 이름과 수당 변경
            StringBuffer update2 = new StringBuffer();
            update2.append("UPDATE EMP0000 ");
            update2.append("SET ename = ename || '(M)', comm = NVL(comm,0) + ? "); // comm은 null이 가능 -> 0으로 바꿔서 계산
            update2.append("WHERE empno = ?");              
            pStmt = conn.prepareStatement(update2.toString());  
            pStmt.setDouble(1, newMgrComm); 
            pStmt.setInt(2, newMgrNo);          
            if (pStmt.executeUpdate() != 1) {
            	throw new EmpNotFoundException(newMgrNo + "은 존재하지 않는 사원번호입니다.");
            };            
            pStmt.close();    // PreparedStatement 객체 해제           

            // 부서의 관리자 변경
            String update3 = "UPDATE DEPT0000 SET manager = ? WHERE dname = ?"; 
            pStmt = conn.prepareStatement(update3);     
            pStmt.setInt(1, newMgrNo);          
            pStmt.setString(2, deptName);           
            pStmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {     // 자원 반납
            if (rs != null) {
                try { 
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (pStmt != null) { 
                try { 
                    pStmt.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            } 
            if (conn != null) { 
                try { 
                    conn.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }   
        return oldMgrNo; 
    }
    
    public static void printEmpInfo(int empNo) {
        Connection conn = null;
        PreparedStatement pStmt = null;     
        ResultSet rs = null;        
        
        StringBuffer query = new StringBuffer();
        query.append("SELECT ename, job, hiredate, sal, comm, deptno "); 
        query.append("FROM EMP0000 WHERE empno = ?");
        
        conn = getConnection();     // DBMS와의 연결 획득 
        try {
            pStmt = conn.prepareStatement(query.toString());    
            pStmt.setInt(1, empNo); 
            rs = pStmt.executeQuery();
            
            if (rs.next()) {
                String empName = rs.getString("ename");
                String job = rs.getString("job");               
                double sal = rs.getDouble("sal");
                double comm = rs.getDouble("comm");
                int deptNo = rs.getInt("deptno");
                Date sqlDate = rs.getDate("hiredate");
                
                // hiredate 값을 String으로 변환
                LocalDate localDate = sqlDate.toLocalDate();   
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String hireDate = localDate.format(formatter);
                // 결과 출력
                System.out.printf("%d %s %s %s %.2f %.2f %d\n", empNo, empName, job, hireDate, sal, comm, deptNo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {     
            if (rs != null) {
                try { 
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (pStmt != null) { 
                try { 
                    pStmt.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
            if (conn != null) { 
                try { 
                    conn.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }       
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("부서명을 입력하시오: ");
        String deptName = scanner.next();
        System.out.println();
        
        try {
        	printDeptInfo(deptName);   // 부서 정보 출력
	    
        	printAllEmpsInDept(deptName);   // 사원들의 정보 출력

	        System.out.print("새 관리자 사번과 보직수당을 입력하시오: ");
	        int newMgrNo  = scanner.nextInt();
	        double commission = scanner.nextDouble();
	        int oldMgrNo = replaceManagerOfDept(deptName, newMgrNo, commission);  // 관리자 변경                                
	        	        
	        System.out.println();
	        System.out.println("<기존 관리자>");     
	        printEmpInfo(oldMgrNo);   // 기존 관리자 사원 정보 출력    
	        
	        System.out.println("<새 관리자>");     
	        printEmpInfo(newMgrNo);  // 새 관리자 사원 정보 출력
	        
        } catch (DeptNotFoundException | EmpNotFoundException ex) {
	        ex.printStackTrace();
	    }        
        scanner.close();
    }
}
