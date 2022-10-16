package controller.impl.cruise;

import controller.Command;
import domain.Cruise;
import service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.*;

public class GetCruiseByIdCommand implements Command {
    private final CruiseService SERVICE;

    public GetCruiseByIdCommand(CruiseService service) {
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

            int cruiseId = getCruiseIdFromRequest(request);

            Cruise cruise = SERVICE.getCruiseById(cruiseId);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            String value = OBJECT_MAPPER.writeValueAsString(cruise);

            response.getWriter().println(value);

        } catch (SQLException | IOException e) {

            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return {@code int} with cruise's Id from request.
     */
    private int getCruiseIdFromRequest(HttpServletRequest request) {

        String cruiseId = request.getParameter("cruise_id");

        return Integer.parseInt(cruiseId);
    }
}
