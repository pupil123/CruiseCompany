package filter;

import dao.impl.ShipDAOImpl;
import domain.Ship;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ShipService;
import service.impl.ShipServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


import static util.Constants.FILTER_SHIP_OUT;

@WebFilter(urlPatterns = FILTER_SHIP_OUT)
public class ShipOutFilter implements Filter {

    private final ShipService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(ShipOutFilter.class);

    public ShipOutFilter() {
        super();
        SERVICE = new ShipServiceImpl(new ShipDAOImpl());
    }


    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException {

        request.setCharacterEncoding("UTF-8");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;


        int newShipId = Integer.parseInt(request.getParameter("newShipId"));
        try {
            if (newShipId > 0) {
                LOGGER.info("Ship N " + newShipId + " is added");
                Ship ship = SERVICE.getShipById(newShipId);

                String shipInfo = "Ship N" + ship.getId() + "\n" +
                        "The ship has " + ship.getCapacity() + " seats" + "\n" +
                        "The ship's staffs are  " + ship.getStaffs() + "\n";
                byte[] barr = shipInfo.getBytes();
                ServletOutputStream out = response.getOutputStream();
                out.write(barr);
            }
        } catch (SQLException throwables) {
            res.getWriter().println("Ship is not added");
        }
    }
}
