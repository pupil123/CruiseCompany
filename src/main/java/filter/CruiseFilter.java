package filter;

import dao.impl.CruiseDAOImpl;
import dao.impl.ShipDAOImpl;
import domain.Cruise;
import domain.Ship;
import domain.builder.CruiseBuilder;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static util.Constants.FILTER_CRUISE;

@WebFilter(urlPatterns = FILTER_CRUISE)
public class CruiseFilter implements Filter {

    private final CruiseService CRUISE_SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(CruiseFilter.class);

    public CruiseFilter() {
        super();
        CRUISE_SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());

    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        String startDate = req.getParameter("start date");
        String finishDate = req.getParameter("finish date");
        String shipNum = req.getParameter("ship num");

        String lang = req.getParameter("Lang");
        String route = req.getParameter("route");

        try {
             LocalDate.parse(startDate);
             LocalDate.parse(finishDate);
        } catch (Exception  e) {
        if (lang.equals("Ukr")) {
            String s1 = "Будь-ласка введіть правильні дати початку та кінця круїзу";
            byte[] barr = s1.getBytes();
            ServletOutputStream out = response.getOutputStream();
            out.write(barr);
        } else {
            response.getWriter().println("Please enter correct start and finish date");
        }
            // res.sendRedirect("/filter/cruise/out?newCruiseId=" + newCruiseId +
            //         "&Lang=" + lang + "&dataMatch=" + dataMatch);
        }


    Cruise cruise = new CruiseBuilder()
            .buildStartDate(LocalDate.parse(startDate))
            .buildFinishDate(LocalDate.parse(finishDate))
            .buildShipId(Integer.parseInt(shipNum))
            .buildRoute(route)
            .build();

    String dataMatch = "";
    int newCruiseId = 0;
        if(LocalDate.parse(startDate).

    isBefore(LocalDate.parse(finishDate))&&
            LocalDate.parse(startDate).

    isAfter(LocalDate.now()))

    {
        dataMatch = "true";
        try {
            newCruiseId = CRUISE_SERVICE.addNewCruise(cruise);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    } else dataMatch ="false";

    //  ShipServiceImpl shipService = new ShipServiceImpl(new ShipDAOImpl());


        res.sendRedirect("/filter/cruise/out?newCruiseId="+newCruiseId +
            "&Lang="+lang +"&dataMatch="+dataMatch);
}
}
