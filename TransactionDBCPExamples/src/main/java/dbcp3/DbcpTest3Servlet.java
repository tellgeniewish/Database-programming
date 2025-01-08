package dbcp3;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/dbcpTest3")
public class DbcpTest3Servlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		EmpDAO dao = new EmpDAO();
		List<Employee> empList = dao.findEmpList();
		
		request.setAttribute("empList", empList);
		
		RequestDispatcher rd = request.getRequestDispatcher("printList.jsp");
		rd.forward(request, response);				
	}
}
