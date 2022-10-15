package controller.impl.user;

import controller.Command;
import domain.User;
import service.ShipService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static util.Constants.*;

public class GetAllUsersCommand implements Command {
    private final UserService SERVICE;

    public GetAllUsersCommand(UserService service) {
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

            List<User> allUsers = SERVICE.getAllUsers();

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            String value = OBJECT_MAPPER.writeValueAsString(allUsers);

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
     */
    private int getUserIdFromRequest(HttpServletRequest request) throws IOException {

        String userId = request.getParameter("user_id");

        return Integer.parseInt(userId);
    }
}
