package controller.impl.order;

import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;
import domain.Order;
import domain.User;
import service.CruiseService;
import service.OrderService;
import service.ShipService;
import service.UserService;
import service.impl.CruiseServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.DISPLAY_ADD_ORDER_SERVLET;
import static util.Constants.USER_CONTROLLER_SERVLET;

@WebServlet(urlPatterns = DISPLAY_ADD_ORDER_SERVLET)
public class DisplayAddOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService ORDER_SERVICE;
    private final CruiseService CRUISE_SERVICE;
    private final ShipService SHIP_SERVICE;
    private final UserService USER_SERVICE;

    public DisplayAddOrderServlet() {
        super();
        ORDER_SERVICE = new OrderServiceImpl(new OrderDAOImpl());
        CRUISE_SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
        SHIP_SERVICE = new ShipServiceImpl(new ShipDAOImpl());
        USER_SERVICE = new UserServiceImpl(new UserDAOImpl());
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
      //  int success = Integer.parseInt(request.getParameter("successAdd"));
        int newOrderId = Integer.parseInt(request.getParameter("newOrderId"));
      //  if (success == 1)
        //    request.setAttribute("result", "Order successfully added");

        {
            Order orderById = null;
            try {
                orderById = ORDER_SERVICE.getOrderById(newOrderId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            User userId1 = null;
            try {
                userId1 = USER_SERVICE.getUserById(orderById.getUserId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Cruise cruiseId1 = null;
            try {
                cruiseId1 = CRUISE_SERVICE.getCruiseById(orderById.getCruiseId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            response.getWriter().println("Order N" + newOrderId + "\n" +
                    "First Name : " + userId1.getFirstName() + "\n" +
                    "Last Name : " + userId1.getLastName() + "\n" +
                    "Start date : " + cruiseId1.getStartDate().toString() + "\n" +
                    "Route : " + cruiseId1.getRoute() + "\n" +
                    "Ship Num : " + cruiseId1.getShipId());
        } /*else {
            response.getWriter().println("Order is not added");
        }*/
     //   request.getServletContext().getRequestDispatcher("/userorder_page.jsp").forward(request, response);
    }

}
