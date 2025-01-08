package jstl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

/**
 * Servlet implementation class 
 */
@WebServlet("/array1")
public class ArrayServlet1 extends HttpServlet {	
	private static final long serialVersionUID = 1L;

	public ArrayServlet1() {
        super();
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	String[] words = {"foo", "bar", "baz"};
    	// List<String> words = Arrays.asList("foo", "bar", “baz”);
    	
    	request.setAttribute("words", words);
    	String address = "/WEB-INF/result/array-loop.jsp";
    	RequestDispatcher dispatcher =
    			request.getRequestDispatcher(address);
    	dispatcher.forward(request, response);
    }
}
