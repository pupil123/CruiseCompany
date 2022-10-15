package filter;

import dao.impl.UserDAOImpl;
import domain.User;
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

import static util.Constants.FILTER_REGIST_OUT;

@WebFilter(urlPatterns = FILTER_REGIST_OUT)
public class RegistOutFilter implements Filter {

    private final UserService USER_SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(RegistOutFilter.class);

    public RegistOutFilter() {
        super();
        USER_SERVICE = new UserServiceImpl(new UserDAOImpl());
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        int newUserId = Integer.parseInt(request.getParameter("newUserId"));


        String lengthMatch = req.getParameter("lengthMatch");

        String lang = req.getParameter("Lang");

        if (newUserId > 0 && lengthMatch.equals("true")) {

            User user = null;
            try {
                user = USER_SERVICE.getUserById(newUserId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            LOGGER.info("User N " + newUserId + " is added");
            String userInfo =
                    "User N" + newUserId + "\n" +
                            "First Name : " + user.getFirstName() + "\n" +
                            "Last Name : " + user.getLastName() + "\n";

            byte[] barr = userInfo.getBytes();
            ServletOutputStream out = response.getOutputStream();
            out.write(barr);
            return;
        } else {
            //response.getWriter().println("User is not added");
            // try {
            if (lengthMatch.equals("false")) {
                if (lang.equals("Ukr")) {
                    String s1 = "Ваш логін та пароль повинні мати мінімум 5 знаків " + "\n" +
                            " пройдіть реєстрацію або авторизацію знову";
                    byte[] barr = s1.getBytes();
                    ServletOutputStream out = response.getOutputStream();
                    out.write(barr);
                    return;
                }
                response.getWriter().println("Your login and password should be min 5 characters long," + "\n" +
                        "go to registration or authorise again");
                return;
            }
        }
        if (lang.equals("Ukr")) {
            String s1 = "Такий логін уже існує," + "\n" +
                    " пройдіть реєстрацію або авторизацію знову";
            byte[] barr = s1.getBytes();
            ServletOutputStream out = response.getOutputStream();
            out.write(barr);
        }
        response.getWriter().println("Such login already exists, go to registration or authorise again");

    }
}



