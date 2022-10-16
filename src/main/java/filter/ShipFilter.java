package filter;

import dao.impl.CruiseDAOImpl;
import dao.impl.ShipDAOImpl;
import domain.Ship;
import domain.builder.ShipBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;
import service.ShipService;
import service.impl.CruiseServiceImpl;
import service.impl.ShipServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.FILTER_SHIP;

@WebFilter(urlPatterns = FILTER_SHIP)
public class ShipFilter implements Filter {

    private final ShipService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(ShipFilter.class);

    public ShipFilter() {
        super();
        SERVICE = new ShipServiceImpl(new ShipDAOImpl());
    }


    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        // filterChain.doFilter(request, response);

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;


        String capacity = request.getParameter("capacity");
        String numOfVisitedPorts = request.getParameter("num visited ports");
        String staffs = request.getParameter("staffs");

        try {
            Integer.parseInt(capacity);
            Integer.parseInt(numOfVisitedPorts);
        } catch (Exception e) {
            response.getWriter().println("Please enter correct ship capacity and number of visited ports");
        }

        Ship ship = new ShipBuilder()
                .buildCapacity(Integer.parseInt(capacity))
                .buildNumberPortsVisited(Integer.parseInt(numOfVisitedPorts))
                .buildStuffs(staffs)
                .build();


        int newShipId = 0;
        try {
             newShipId = SERVICE.addNewShip(ship);
        } catch (SQLException throwables) {
            LOGGER.error("Ship can't be added");
            res.setStatus(406);
            return;
        }
        res.setStatus(200);
                res.sendRedirect("/filter/ship/out?newShipId=" + newShipId);
    }
}
