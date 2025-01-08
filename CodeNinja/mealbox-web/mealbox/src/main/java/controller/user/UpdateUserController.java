package controller.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.domain.User;
import model.service.UserManager;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
 
    	UserManager manager = UserManager.getInstance();

    	if (request.getMethod().equals("GET")) {	
    		//나중에 adminUser에서 수정할 때를 고려하여, 현재 접속한 id를 loginId로 저장함.
			HttpSession session = request.getSession();
			String loginId = (String)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
			User user = manager.findUser(loginId);
			request.setAttribute("user", user);
			
			String phone = user.getPhone();
			String[] phoneParts = phone.split("-");
			request.setAttribute("phoneParts", phoneParts);
			
			String email = user.getEmail();
			String[] emailParts = email.split("@");
			request.setAttribute("emailParts", emailParts);
			
			return "/user/updatePage.jsp";
	    }	
    	
    	// POST request (회원정보가 parameter로 전송됨)
    	User updateUser = new User(
    			request.getParameter("id"),
    			request.getParameter("password"),
    			request.getParameter("name"),
    			request.getParameter("phone_part1") + "-" +request.getParameter("phone_part2") + "-" +request.getParameter("phone_part3"),
    			request.getParameter("email_id") + "@" +request.getParameter("email_domain"),
    			request.getParameter("address"));
		
    	log.debug("Update User : {}", updateUser);

		manager.update(updateUser);			
        return "redirect:/user/readUser";			
    }
}