package model.dao.test;

import java.sql.*;
import java.util.*;

import model.dao.UserDAO;
import model.domain.*;
import model.service.*;
import model.service.UserNotFoundException;

public class Test {
	
	//User부분 테스트
	private static UserDAO userDao = new UserDAO();
	
	public static void main(String[] args) {
		System.out.println("Test용 코드입니다.");
		Scanner sc = new Scanner(System.in);
		
		try {
			//create
	        System.out.println("새로운 사용자정보를 생성합니다.");
	        User user = new User("id_new", "pw_new", "코드닌자_new", "010-0000-0000", "newUser@dongduk.ac.kr", "동덕여자대학교");
	        int result = userDao.create(user);
	        System.out.println("결과(0: 실패, 1: 성공): " + result);
	        System.out.println();
	        
			//findUser
	        System.out.println("생성한 사용자 정보를 조회합니다.");
	        System.out.println();
	        user = userDao.findUser(user.getId());
	        if(user != null) {
	            System.out.println("user정보");
	            System.out.println("id: " + user.getId());
	            System.out.println("password: " + user.getPassword());
	            System.out.println("name: " + user.getName());
	            System.out.println("phone: " + user.getPhone());
	            System.out.println("email: " + user.getEmail());
	            System.out.println("address: " + user.getAddress());
	        } else {
	            System.out.println("회원정보가 없습니다.");
	        }
	        System.out.println();
	        
			//update
	        System.out.println("사용자 정보를 수정합니다.");
	        user = userDao.findUser("id_new");
	        user.setPhone("010-1111-1111");
	        result = userDao.update(user);
	        System.out.println("결과(0: 실패, 1: 성공): " + result);
	        if(user != null) {
	            System.out.println("user정보");
	            System.out.println("id: " + user.getId());
	            System.out.println("password: " + user.getPassword());
	            System.out.println("name: " + user.getName());
	            System.out.println("phone: " + user.getPhone());
	            System.out.println("email: " + user.getEmail());
	            System.out.println("address: " + user.getAddress());
	        }
	        System.out.println();
	        
	        //delete
	        System.out.println("사용자 정보를 삭제합니다.");
	        result = userDao.remove(user.getId());
	        System.out.println("결과(0: 실패, 1: 성공): " + result);
	        System.out.println();
	        
	        //findUserList
	        System.out.println("사용자 리스트를 출력합니다.");
	        List<User> userList = userDao.findUserList();
	        System.out.println("아이디 비밀번호 이름 전화번호 주소 이메일");
	        System.out.println("----------------------------------------------------");
	        for (int i = 0; i < userList.size(); i++) {
	        	User tempUser = userList.get(i);
	        	if(tempUser != null) {
	        		System.out.println(tempUser.getId() + " " +tempUser.getPassword() + " " 
	        				+ tempUser.getName() + " " + tempUser.getPhone() + " " 
	        				+tempUser.getAddress() + " " + tempUser.getEmail());
	        	}
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("데이터베이스 오류: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        sc.close();  // Scanner 리소스 반환
	    }
		
		//User부분 테스트 완료

	}
}