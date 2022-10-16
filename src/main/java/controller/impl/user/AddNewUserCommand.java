package controller.impl.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import controller.impl.ship.UpdateShipCommand;
import domain.Order;
import domain.OrderStatus;
import domain.Ship;
import domain.User;
import domain.builder.OrderBuilder;
import domain.builder.UserBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ShipService;
import service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddNewUserCommand implements Command {
    private final UserService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(AddNewUserCommand.class);

    public AddNewUserCommand(UserService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response gets route from request,
     * checks route for existing and persists new route to data base.
     * <p>
     * if route exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String firstName = request.getParameter("first name");
        String lastName = request.getParameter("last name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String lang = request.getParameter("Lang");


        //if (!SERVICE.verificateUser(login, password)) {
        User user = new UserBuilder()
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildAdministrator(false)
                .buildLogin(login)
                .buildPassword(password)
                .build();
        try {
            if (login.length() >= 5 && password.length() >= 5) {
                int newUserId = SERVICE.addNewUser(user);
                if (newUserId == 0) {
                    //  SERVICE.deleteUserById(newUserId);
                    try {
                        if (lang.equals("Ukr")) {
                            String s1 = "Такий логін уже існує," + "\n" +
                                    " пройдіть реєстрацію або авторизацію знову";
                            byte[] barr = s1.getBytes();
                            ServletOutputStream out = response.getOutputStream();
                            out.write(barr);
                        } else if (lang.equals("Eng"))
                            response.getWriter().println("Such login already exists, go to registration or authorise again");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                response.sendRedirect("/display/adduser?newUserId=" + newUserId);
                LOGGER.info("User N " + newUserId + " is added");
            } else {
                try {
                    if (lang.equals("Ukr")) {
                        String s1 = "Ваш логін та пароль повинні мати мінімум 5 знаків " + "\n" +
                                " пройдіть реєстрацію або авторизацію знову";
                        byte[] barr = s1.getBytes();
                        ServletOutputStream out = response.getOutputStream();
                        out.write(barr);
                    } else if (lang.equals("Eng"))
                        response.getWriter().println("Your login and password should be min 5 characters long," + "\n" +
                                "go to registration or authorise again");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            // }
        /*    } catch (Exception e) {
                LOGGER.error("Such login already exists");
                response.getWriter().println("Such login already exists, go to registration or authorise again");
            }*/

        } catch (SQLException e) {
            LOGGER.error("User can't be added");
            response.setStatus(406);
            return;

            //e.printStackTrace();
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets builds user from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @throws IOException when process request or respond fails
     * @return instance {@code User} class from rtequest
     */
    private User getUserFromRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(reader, User.class);

    }
}
