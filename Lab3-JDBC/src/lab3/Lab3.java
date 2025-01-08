package lab3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Lab3 {
    public Lab3() {     // 생성자
        // JDBC 드라이버 로딩
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");   
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }   
    }
    
    private static Connection getConnection() {
        String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";        
        //String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
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
        String query = 
                "SELECT deptno, manager, COUNT(empno) AS numOfEmps "
                + "FROM EMP0000 JOIN DEPT0000 USING (deptno) "
                + "WHERE dname = ? "
                + "GROUP BY deptno, manager";
        
        conn = getConnection();
        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setString(1, deptName);
            rs = pStmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("<부서정보>");
                int deptno = rs.getInt("deptno");
                System.out.println("부서명: " + deptName);
                System.out.println("관리자사번: " + rs.getInt("manager"));
                System.out.println("사원 수: " + rs.getInt("numOfEmps"));
                System.out.println();
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) 
                try {
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (pStmt != null) 
                try {
                    pStmt.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    public static void printAllEmpsInDept(String deptName) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null; 
        StringBuilder query = new StringBuilder();

        query.append("SELECT empno, ename, job, sal, comm ");
        query.append("FROM EMP0000 JOIN DEPT0000 USING (deptno) ");
        query.append("WHERE dname = ?");
           
        conn = getConnection();
        
        try {
            conn = getConnection(); 
            pStmt = conn.prepareStatement(query.toString());    
            pStmt.setString(1, deptName);  
            rs = pStmt.executeQuery();
            
            System.out.println("사번    이름    직무    급여    수당");
            System.out.println("------------------------------------------------------");

            while (rs.next()) {
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
            if (rs != null) 
                try {
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (pStmt != null) 
                try {
                    pStmt.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
    
    public static int replaceManagerOfDept(String deptName, int newMgrNo, double newMgrComm) 
            throws EmpNotFoundException {
        Connection conn = null;
        int oldMgrNo = 0;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
        conn = getConnection();

        // 기존 부서 관리자 사번 조회 (SELECT문 실행)
        String query1 = "SELECT manager FROM DEPT0000 WHERE dname = ?";
        pStmt = conn.prepareStatement(query1);  
        pStmt.setString(1, deptName); 
        rs = pStmt.executeQuery();
        if (rs.next())
            oldMgrNo = rs.getInt("manager");
        else
            return oldMgrNo;  
        pStmt.close();

        // 기존 관리자 사원의 이름에서 "(M)" 삭제 (UPDATE문 실행)
        StringBuilder update2 = new StringBuilder();
        update2.append("UPDATE EMP0000 ");
        update2.append("SET job = SUBSTR(job, 0, LENGTH(job)-3) ");
        update2.append("WHERE empno = ?"); 
        pStmt = conn.prepareStatement(update2.toString());
        pStmt.setInt(1, oldMgrNo);
        pStmt.executeUpdate();
        pStmt.close();
        // 새 관리자 사원의 이름과 수당 변경 (UPDATE문 실행)
        StringBuilder update3 = new StringBuilder();
        update3.append("UPDATE EMP0000 ");
        update3.append("SET job = job || '(M)', comm = NVL(comm,0) + ? "); // comm은 null이 가능 -> 0으로 바꿔서 계산
        update3.append("WHERE empno = ?"); 
        pStmt = conn.prepareStatement(update3.toString());
        pStmt.setDouble(1, newMgrComm); 
        pStmt.setInt(2, newMgrNo);
        pStmt.executeUpdate();

        // 부서의 관리자 변경 (UPDATE문 실행)
        String update1 = "UPDATE DEPT0000 SET manager = ? WHERE dname = ?"; 
        pStmt = conn.prepareStatement(update1); 
        pStmt.setInt(1, newMgrNo);
        pStmt.setString(2, deptName);
        pStmt.executeUpdate(); 
        pStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) 
                try {
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (pStmt != null) 
                try {
                    pStmt.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return oldMgrNo; 
    }
    
    public static void printEmpInfo(int empNo) {           
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        StringBuilder query = new StringBuilder();
        query.append("SELECT ename, job, hiredate, sal, comm, deptno "); 
        query.append("FROM EMP0000 WHERE empno = ?");

        try {
            conn = getConnection();

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

                LocalDate localDate = sqlDate.toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String hireDate = localDate.format(formatter);
                
                System.out.printf("%d %s %s %s %.2f %.2f %d\n", empNo, empName, job, hireDate, sal, comm, deptNo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) 
                try {
                    rs.close(); 
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (pStmt != null) 
                try {
                    pStmt.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
        }
        
        // 참고: DB 검색 결과 중 DATE 타입의 컬럼 값을 읽어서 String 객체를 생성하는 방법
        /*  
            Date sqlDate = rs.getDate("...");   // import java.sql.Date 필요
            LocalDate localDate = sqlDate.toLocalDate();    // java.sql.Date --> java.time.LocalDate로 변환. import java.time.LocalDate 필요 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");   // 문자열 형식 지정. import java.time.format.DateTimeFormatter 필요
            String DateStr = localDate.format(formatter);  // 변환 실행
        */  
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);   
        
        System.out.print("부서명을 입력하시오: ");
        String deptName = scanner.next();
        System.out.println();
     
        try {
            // 입력된 부서명 이용 printDeptInfo 호출
            printDeptInfo(deptName);
            // 입력된 부서명 이용 printAllEmpsInDept 호출
            printAllEmpsInDept(deptName);
            System.out.print("새 관리자 사번과 보직수당을 입력하시오: ");
            int managerNo  = scanner.nextInt();
            double commission = scanner.nextDouble();
                
            // 입력된 값들을 이용 replaceManagerOfDept 호출 (기존 관리자 사번 반환)
    
            System.out.println("<기존 관리자>");     
            // 기존 관리자 사번에 대해 printEmpInfo 호출      
    
            System.out.println("<새 관리자>");
            // 새 관리자 사번에 대해 printEmpInfo 호출      
            
        } catch (Exception ex) {  // (DeptNotFoundException | EmpNotFoundException ex) {
            ex.printStackTrace();
        }    
        scanner.close();
    }
}
