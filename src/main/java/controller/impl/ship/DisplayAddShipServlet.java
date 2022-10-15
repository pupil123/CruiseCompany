package controller.impl.ship;

import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;
import domain.Order;
import domain.Ship;
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

import static util.Constants.DISPLAY_ADD_SHIP_SERVLET;

@WebServlet(urlPatterns = DISPLAY_ADD_SHIP_SERVLET)
public class DisplayAddShipServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService ORDER_SERVICE;
    private final CruiseService CRUISE_SERVICE;
    private final ShipService SHIP_SERVICE;
    private final UserService USER_SERVICE;

    public DisplayAddShipServlet() {
        super();
        ORDER_SERVICE = new OrderServiceImpl(new OrderDAOImpl());
        CRUISE_SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
        SHIP_SERVICE = new ShipServiceImpl(new ShipDAOImpl());
        USER_SERVICE = new UserServiceImpl(new UserDAOImpl());
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        int newShipId = Integer.parseInt(request.getParameter("newShipId"));
        try {
            if (newShipId > 0) {
                Ship ship = SHIP_SERVICE.getShipById(newShipId);
                response.getWriter().println("Ship N" + ship.getId() + "\n" +
                        "The ship has " + ship.getCapacity() + " seats");
            }
        } catch (SQLException throwables) {
            response.getWriter().println("Cruise is not added");
        }
    }
}

