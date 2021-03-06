package aircompany;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TicketDAO;
import dao.UserDAO;
import model.Ticket;
import model.User;


public class LoggedInUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUsername = (String)request.getSession().getAttribute("loggedInUsername");
		if(loggedInUsername == null)
		{
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		User loggedInUser = UserDAO.get(loggedInUsername);
		System.out.println(loggedInUser);
		if(loggedInUser == null)
		{
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		User user = UserDAO.get(loggedInUsername);
		
		List<Ticket> reservedTickets = TicketDAO.getUserReservations(loggedInUser);
		List<Ticket> purchasedTickets = TicketDAO.getUserPurchases(loggedInUser);
		
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("user", user);
		data.put("reservedTickets", reservedTickets);
		data.put("purchasedTickets", purchasedTickets);
		System.out.println(data);
		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
		
		
	}
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
