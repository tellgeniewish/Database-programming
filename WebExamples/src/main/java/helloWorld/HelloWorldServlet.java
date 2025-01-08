package helloWorld;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.text.*;
import java.io.*;

/**
 * Servlet implementation class 
 */
@WebServlet(urlPatterns="/helloWorld")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String time;
  	private SimpleDateFormat dateFormat;
  	
	public HelloWorldServlet() {	// constructor
        super();
    }
	
    public void init() {			// life-cycle init method
    	dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 	}

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		time = dateFormat.format(new Date());
		String message = "Hello " + request.getParameter("name");
		
	    response.setContentType("text/html;charset=utf-8");
		
	    PrintWriter out = response.getWriter();
		out.print("<HTML><HEAD><TITLE>"); 
		out.print(message); 
		out.println("</TITLE></HEAD></HTML>");
	    out.println("<BODY>");
	    out.println("<H1>HelloWorldSevlet</H1>");
	    out.println("<B>" + message + "</B><BR><BR>");
	 	for (int i = 1; i <= 5; i++) {
			out.print(i); out.print(" : "); out.print(time); out.println("<br/>"); 
	    }   	
		out.println("</BODY>"); 
		out.println("</HTML>");
	}
}
