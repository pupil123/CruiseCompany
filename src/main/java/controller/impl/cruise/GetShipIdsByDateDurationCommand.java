package controller.impl.cruise;

import controller.Command;
import domain.Cruise;
import service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static util.Constants.*;

public class GetShipIdsByDateDurationCommand implements Command {
    private final CruiseService SERVICE;

    public GetShipIdsByDateDurationCommand(CruiseService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response gets CruiseId from request,
     * checks Cruise for existing and persists new Cruise to data base.
     * <p>
     * if Cruise exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        try {

            LocalDate locDat = getLocalDateFromRequest(request);

            Period per = getPeriodFromRequest(request);
            List<Integer> shipIds = SERVICE.getShipIdListByDateDuration(locDat, per.getDays());

            //  List<Category> listCatagory = dao.list();
            //request.setAttribute("shipsIds", shipIds);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            String value = OBJECT_MAPPER.writeValueAsString(shipIds);

            response.getWriter().println(value);

        } catch (SQLException | IOException e) {

            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets  LocalDate from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return {@code LocalDate} from request.
     */
    private LocalDate getLocalDateFromRequest(HttpServletRequest request) {

        String localDateString = request.getParameter("startdate");
        LocalDate localDate = LocalDate.parse(localDateString);
        return localDate;
    }

    /**
     * Receives request gets  {@code Period} from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return {@code Period} from request.
     */

    private Period getPeriodFromRequest(HttpServletRequest request) {
        //example "P1Y2M21D"
        String period = request.getParameter("duration");
        Period p = Period.parse(period);

        return p;
    }
}
