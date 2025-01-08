//loginController 완료
package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.UserManager;

public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userId = request.getParameter("userid");
		String password = request.getParameter("password");
		
		try {
			// 모델에 로그인 처리를 위임
			UserManager manager = UserManager.getInstance();
			manager.login(userId, password);
	
			// 세션에 사용자 이이디 저장
			HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, userId);
            
            if(userId.equals("admin")) {
            	//admin 로그인 시
            	System.out.println("admin으로 로그인했습니다.");
                return "redirect:/admin";
                //return "redirect:/user/listUser"; //listUser시험용
            }else {
            	 //일반회원 로그인 시
            	return "redirect:/product"; //오류 시, redirect:뒤에 띄어쓰기 되어있는지 확인!
            	//return "redirect:/user/readUser"; //readUser시험용
            	//session.setAttribute("page", "cartPage"); //cartProduct시험용
            	//return "redirect:/cart/view"; //cartProduct시험용
            }
            
		} catch (Exception e) {
			/* UserNotFoundException이나 PasswordMismatchException 발생 시
			 * 다시 login form을 사용자에게 전송하고 오류 메세지도 출력
			 */
            request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
            return "redirect:/user/login/form";			
		}	
    }
}
