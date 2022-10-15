package filter;

import dao.impl.UserDAOImpl;
import domain.User;
import domain.builder.UserBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


import static util.Constants.FILTER_REGIST;


@WebFilter(urlPatterns = FILTER_REGIST)
public class RegistFilter implements Filter {

    private final UserService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(RegistFilter.class);

    public RegistFilter() {
        super();
        SERVICE = new UserServiceImpl(new UserDAOImpl());
    }


    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        // filterChain.doFilter(request, response);

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

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

        boolean lengthMatch = login.length() >= 5 && password.length() >= 5;
        int newUserId = 0;
        try {
            if (lengthMatch) {
                newUserId = SERVICE.addNewUser(user);
            }
                /*if (newUserId == 0) {
                    //  SERVICE.deleteUserById(newUserId);
                    try {
                        if (lang.equals("Ukr")) {
                            String s1 = "Такий логін уже існує," + "\n" +
                                    " пройдіть реєстрацію або авторизацію знову";
                            byte[] barr = s1.getBytes();
                            ServletOutputStream out = response.getOutputStream();
                            out.write(barr);
                            return;
                        } else if (lang.equals("Eng"))
                            response.getWriter().println("Such login already exists, go to registration or authorise again");
                        return;
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }*/
            res.sendRedirect("/filter/regist/out?newUserId=" + newUserId +
                    "&lengthMatch=" + lengthMatch + "&Lang=" + lang);

            //  } else {
                /*try {
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
                }*/
            // }
        } catch (SQLException e) {
           /* if (lang.equals("Ukr")) {
                String s1 = "Такий логін уже існує," + "\n" +
                        " пройдіть реєстрацію або авторизацію знову";
                byte[] barr = s1.getBytes();
                ServletOutputStream out = response.getOutputStream();
                out.write(barr);
            } else {
            response.getWriter().println("Such login already exists, go to registration or authorise again");}*/
            LOGGER.info("User can't be added");
            res.sendRedirect("/filter/regist/out?newUserId=" + 0 +
                    "&lengthMatch=" + lengthMatch + "&Lang=" + lang);

            res.setStatus(406);

        }
        res.setStatus(200);
    }
}
