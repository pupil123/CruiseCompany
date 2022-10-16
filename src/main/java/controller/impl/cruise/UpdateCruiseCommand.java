package controller.impl.cruise;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import domain.Cruise;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateCruiseCommand implements Command {
    private final CruiseService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(UpdateCruiseCommand.class);

    public UpdateCruiseCommand(CruiseService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response gets Cruise from request,
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

            Cruise cruise = getCruiseFromRequest(request);

            SERVICE.updateCruise(cruise);
            LOGGER.info("Cruise " + cruise.getId() + " is updated");

        } catch (SQLException | IOException e) {
            LOGGER.error("Cruise can't be updated");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets builds Cruise from it.
     *
     * @return instance of class {@code Cruise}
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @throws IOException when process request fails
     */
    private Cruise getCruiseFromRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(reader, Cruise.class);

    }
}
