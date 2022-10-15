package controller;

import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import domain.Order;
import domain.OrderStatus;
import service.CruiseService;
import service.impl.CruiseServiceImpl;
import service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static util.Constants.*;
import static util.UtilData.COMMANDS_MAP;

/**
 * The {@code FrontControllerServlet} class is front controller command,
 * that is responsible for processing requests by using needed controller
 */
@WebServlet(urlPatterns = MAIN_CONTROLLER_SERVLET)
public class MainPageControllerServlet extends HttpServlet {

    /**
     * Processes get-request
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderServiceImpl orderService = new OrderServiceImpl(new OrderDAOImpl());
        CruiseServiceImpl cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
        //  List<Integer> orderIdsList=orderService.getOrderIdListByCruiseFinishDate(LocalDate.now());
        List<Order> ordersList = orderService.getAllOrders();
        List<Integer> ordersIdsList = new ArrayList<>();
        for (Order o : ordersList) {
            ordersIdsList.add(o.getId());
        }
        for (Integer orderId : ordersIdsList) {
            if (cruiseService.getCruiseById(orderService.getOrderById(orderId).getCruiseId())
                    .getFinishDate().isBefore(LocalDate.now())) {
                Order o = orderService.getOrderById(orderId);
                o.setStatus(OrderStatus.valueOf("COMPLETED"));
                try {
                    orderService.updateOrder(o);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
