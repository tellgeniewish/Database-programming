package lab5;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Lab5 {
    private static CompanyDao compDao = new CompanyDao();
    
    public static void main(String[] args) {        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("부서명을 입력하시오: ");
        String deptName = scanner.next();
        System.out.println();
        
//        Department dept = compDao.findDeptByName(deptName);  
        Department dept = compDao.findDeptAndEmpsByDeptName(deptName);  
        
        // dept 객체의 필드 값 출력
        if (dept != null) {     // 질의 결과 존재 확인 필요! (최대 하나의 행)
            System.out.println("<부서정보>");
            System.out.println("부서번호: " + dept.getDeptNo());
            System.out.println("부서명: " + deptName);
            System.out.println("관리자사번: " + dept.getMgrNo());
            System.out.println("사원 수: " + dept.getNumOfEmps());
            System.out.println();
        }          
        
//        List<Employee> empList = compDao.getAllEmpsInDept(dept.getDeptNo());        
        if (dept.getEmpList().isEmpty() == false) {       // Employee 객체 존재           
            // empList에 포함된 모든 Employee 객체들의 필드 값을 출력: 
            //  Employee 객체들을 하나씩 접근하기 위해 empList로부터 Iterator<Employee>를 구해서 활용

            System.out.println("<사원정보>");
            System.out.println("사번        이름           직무          급여         수당");
            System.out.println("------------------------------------------------------");           
            Iterator<Employee> iter = dept.getEmpList().iterator();
            while (iter.hasNext()) {        
                Employee emp = iter.next();
                System.out.printf("%d %10s %15s %10.2f %10.2f\n", 
                    emp.getEmpNo(), emp.getEmpName(), emp.getJob(), emp.getSal(), emp.getComm());
            }
            System.out.println();
        }       
        scanner.close();
    }
}