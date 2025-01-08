package jstl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Name;

import java.io.*;

/**
 * Servlet implementation class 
 */
@WebServlet("/array2")
public class ArrayServlet2 extends HttpServlet {	
	private static final long serialVersionUID = 1L;

	public ArrayServlet2() {
        super();
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	Name[] names = { new Name("Bill", "Gates"),
	    				 new Name("Larry", "Ellison"),
	    				 new Name("Sam", "Palmisano") };
		request.setAttribute("names", names);
		String[][] sales = { {"2005", "12,459", "15,622"},
							 {"2006", "18,123", "17,789"},
							 {"2007", "21,444", "23,555"} };
		request.setAttribute("sales", sales);
		String address = "/WEB-INF/result/array-loop2.jsp";
		request.getRequestDispatcher(address).forward(request, response);
    }
}
