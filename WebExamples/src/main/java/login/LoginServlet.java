package login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

import java.io.*;

/**
 * Servlet implementation class 
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
	
    public void init() {	// life-cycle init method
 	}

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		System.out.println(id + ", " + pw);
		
		LoginService loginSvc = new LoginService();
		User userInfo = loginSvc.login(id, pw);		
		if (userInfo != null) {		// login succeeds
			HttpSession session = request.getSession();		// create a new session object
			session.setAttribute("userInfo", userInfo);
			RequestDispatcher rd = request.getRequestDispatcher("login/loginResult.jsp");
			rd.forward(request, response);					// forwarding
		}
		else {		// login fails
			response.sendRedirect("login/loginForm.jsp");	// redirection
		}
	}
}
