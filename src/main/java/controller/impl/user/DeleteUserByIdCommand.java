package controller.impl.user;

import controller.Command;
import controller.impl.cruise.DeleteCruiseByIdCommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ShipService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteUserByIdCommand implements Command {
    private final UserService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserByIdCommand.class);

    public DeleteUserByIdCommand(UserService service) {
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

            int userId = getUserIdFromRequest(request);

            SERVICE.deleteUserById(userId);
            LOGGER.info("User N " + userId + " is deleted");
        } catch (SQLException e) {
            LOGGER.error("User can't be deleted");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return user's Id {@code int} from request
     */
    private int getUserIdFromRequest(HttpServletRequest request) {

        String userId = request.getParameter("user_id");

        return Integer.parseInt(userId);
    }
}
