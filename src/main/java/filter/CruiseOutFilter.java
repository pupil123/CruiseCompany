package filter;

import dao.impl.CruiseDAOImpl;
import domain.Cruise;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;
import service.impl.CruiseServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.FILTER_CRUISE_OUT;

@WebFilter(urlPatterns = FILTER_CRUISE_OUT)
public class CruiseOutFilter implements Filter {

    private final CruiseService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(CruiseOutFilter.class);

    public CruiseOutFilter() {
        super();
        SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        int newCruiseId = Integer.parseInt(request.getParameter("newCruiseId"));
        String dataMatch = request.getParameter("dataMatch");
        String lang = request.getParameter("Lang");

        if (dataMatch.equals("true")) {
            if (newCruiseId > 0) {
                Cruise cruise1 = null;
                try {
                    cruise1 = SERVICE.getCruiseById(newCruiseId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                LOGGER.info("Cruise N " + newCruiseId + " is added");
                String cruiseInfo = "Cruise N" + cruise1.getId() + "\n" +
                        "Route : " + cruise1.getRoute() + "\n" +
                        "Start Date : " + cruise1.getStartDate() + "\n" +
                        "Ship N : " + cruise1.getShipId() + "\n";

                byte[] barr = cruiseInfo.getBytes();
                ServletOutputStream out = response.getOutputStream();
                out.write(barr);
                return;
            }
            response.getWriter().println("Cruise can't be added");
            return;
        }

        LOGGER.info("Cruise can't be added because date mismatch");
        if (lang.equals("Ukr")) {
            String s1 = "Будь ласка введіть дату початку , яка раніше дати завершення круїзу." + "\n" +
                    " Також почток після поточної дати.";
            byte[] barr = s1.getBytes();
            ServletOutputStream out = res.getOutputStream();
            out.write(barr);

        } else if (lang.equals("Eng")) {
            res.getWriter().println("Please, input start date before finish date" + "\n" +
                    " and start date after now");
        }
    }
}
