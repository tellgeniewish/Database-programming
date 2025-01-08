package lab4;

import java.util.Scanner;
import java.util.Iterator;
import java.util.List;

public class Lab4 {
   
    private static CompanyDao compDao = new CompanyDao();
    
    public static void main(String[] args) {        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("부서명을 입력하시오: ");
        String deptName = scanner.next();
        System.out.println();
        
        Department dept = compDao.findDeptByName(deptName);
        // dept 객체의 필드 값 출력 (주의: dept 객체가 반환되었는지 확인 필요)
        
        // List<Employee> empList = compDao.getAllEmpsInDept( /*부서번호*/ );
        // empList에 포함된 모든 Employee 객체들의 필드 값을 출력: 
        //   Employee 객체들을 하나씩 접근하기 위해 empList로부터 Iterator를 구해서 활용
        if (dept != null) {  // 질의 결과 존재 (결과는 최대 하나의 행)
            System.out.println("<부서정보>");
            System.out.println("부서번호: " + dept.getDeptNo());
            System.out.println("부서명: " + deptName);
            System.out.println("관리자사번: " + dept.getMgrNo());
            System.out.println("사원 수: " + dept.getNumOfEmps());
            System.out.println();
            
            List<Employee> empList = compDao.getAllEmpsInDept(dept.getDeptNo());
            Iterator<Employee> empIterator = empList.iterator();  // Iterator 사용
            while (empIterator.hasNext()) {
                Employee emp = empIterator.next();
                System.out.println("사원번호: " + emp.getEmpNo() + ", 이름: " + emp.getEmpName() + ", 직무: " + emp.getJob());
                System.out.println("입사일: " + emp.getHiredate() + ", 월급: " + emp.getSal() + ", 수당: " + emp.getComm());
                System.out.println("소속부서: " + emp.getDeptName());
                System.out.println();
            }
        }
        else {
            System.out.println("부서 정보가 없습니다.");
        }

        System.out.print("새 관리자 사번과 보직수당을 입력하시오: ");
        int managerNo  = scanner.nextInt();
        double commission = scanner.nextDouble();
        System.out.println();
        
        // 관리자 교체를 위한 DTO 객체 생성
        Appointment appo = new Appointment(deptName, managerNo, commission); // Appointment 객체 생성
        Employee oldMgr = compDao.replaceManagerOfDept(appo);  // 관리자 교체 메소드 호출

        System.out.println("<기존 관리자>");     
        // 반환된 oldMgr 객체의 필드 값 출력 
        if (oldMgr != null) {
            System.out.println("사원번호: " + oldMgr.getEmpNo());
            System.out.println("이름: " + oldMgr.getEmpName());
            System.out.println("직무: " + oldMgr.getJob());
            System.out.println("입사일: " + oldMgr.getHiredate());
            System.out.println("월급: " + oldMgr.getSal());
            System.out.println("수당: " + oldMgr.getComm());
            System.out.println("소속부서: " + oldMgr.getDeptName());
        } else {
            System.out.println("기존 관리자 정보가 없습니다.");
        }
        // Employee newMgr = compDao.findEmployee( /*새 관리자 사번*/ );
        Employee newMgr = compDao.findEmpInfo(managerNo);
        
        System.out.println("<새 관리자>");         
        // 반환된 newMgr 객체의 필드 값 출력 
        if (newMgr != null) {
            System.out.println("사원번호: " + newMgr.getEmpNo());
            System.out.println("이름: " + newMgr.getEmpName());
            System.out.println("직무: " + newMgr.getJob());
            System.out.println("입사일: " + newMgr.getHiredate());
            System.out.println("월급: " + newMgr.getSal());
            System.out.println("수당: " + newMgr.getComm());
            System.out.println("소속부서: " + newMgr.getDeptName());
        } else {
            System.out.println("새 관리자 정보가 없습니다.");
        }
        
        scanner.close();
    }
}