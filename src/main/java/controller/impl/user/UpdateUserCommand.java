package controller.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import domain.Ship;
import domain.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ShipService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserCommand implements Command {
    private final UserService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCommand.class);

    public UpdateUserCommand(UserService service) {
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

            User user = getUserFromRequest(request);

            SERVICE.updateUser(user);
            LOGGER.info("User N " + user.getId() + " is updated");

        } catch (SQLException | IOException e) {
            LOGGER.error("User can't be updated");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets builds User from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @throws IOException when process request or respond fails
     * @return instance of  {@code User} classs from request
     */
    private User getUserFromRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        User user = objectMapper.readValue(reader, User.class);

        return user;
    }
}
