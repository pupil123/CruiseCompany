package controller.impl.user;

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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.DISPLAY_ADD_USER_SERVLET;


@WebServlet(urlPatterns = DISPLAY_ADD_USER_SERVLET)
public class DisplayAddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService USER_SERVICE;

    public DisplayAddUserServlet() {
        super();
        USER_SERVICE = new UserServiceImpl(new UserDAOImpl());
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        int newUserId = Integer.parseInt(request.getParameter("newUserId"));

        if (newUserId > 0) {

            User user = null;
            try {
                user = USER_SERVICE.getUserById(newUserId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            response.getWriter().println("User N" + newUserId + "\n" +
                    "First Name : " + user.getFirstName() + "\n" +
                    "Last Name : " + user.getLastName() + "\n");
        } else {
            response.getWriter().println("User is not added");
        }

    }
}
