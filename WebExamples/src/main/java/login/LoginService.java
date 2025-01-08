package login;

import beans.User;

public class LoginService {
	public User login(String id, String pw) {
		System.out.println(id + ", " + pw);
		
		// process login... 
		// ex: User user = userDao.findUserById(id);
		//     if (user != null && user.getPassword().equals(pw)) 
		//			return user;
		//	   return null;
		
		if (id.equals(pw))
			return new User(id, pw, "Jain", 22, "010-3333-4444"); // login succeeds
		return null;
	}
}
