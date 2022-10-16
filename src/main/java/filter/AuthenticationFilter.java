package filter;

import dao.impl.CruiseDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;


import domain.Ship;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ShipService;
import service.UserService;
import service.impl.CruiseServiceImpl;
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@WebFilter(urlPatterns = "/auth")
public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    private final ShipService SHIP_SERVICE;

    private UserService service;

    public AuthenticationFilter() {
        service = new UserServiceImpl(new UserDAOImpl());
        SHIP_SERVICE = new ShipServiceImpl(new ShipDAOImpl());
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final String lang = req.getParameter("Lang");

        boolean isInDB = service.verificateUser(login, password);
        boolean isAdmin = service.isAdmin(login, password);
        int idByLogPas = service.idByLogPas(login, password);

        CruiseServiceImpl cruiseDao = new CruiseServiceImpl(new CruiseDAOImpl());
        List<Cruise> cruiseList = cruiseDao.getAllCruises();
        request.setAttribute("cruiseList", cruiseList);
        List<Ship> shipList = null;
        try {
            shipList = SHIP_SERVICE.getAllShips();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       // request.setAttribute("ships", shipList);
        req.getSession().setAttribute("ships", shipList);

        if (lang.equals("Eng")) {
            if (login.length() >= 5 && password.length() >= 5) {
                if (isInDB) {
                    if (isAdmin) {
                        LOGGER.info("Hello, Admin");
                        /*CruiseServiceImpl cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
                        List<Cruise> allCruises = cruiseService.getAllCruises();
                        request.setAttribute("allCruises", allCruises);*/
                        request.setAttribute("Lang", lang);



                       /* List<Ship> ships = null;
                        try {
                            ships = SHIP_SERVICE.getAllShips();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        req.getSession().setAttribute("ships", ships);*/

                        req.getServletContext().getRequestDispatcher("/admin_page.jsp").forward(request, response);
                    } else {
                        LOGGER.info("Hello, User");
                        request.setAttribute("userId", idByLogPas);
                        request.setAttribute("Lang", lang);


                        req.getServletContext().getRequestDispatcher("/user_page.jsp").forward(request, response);
                    }

                } else {
                    LOGGER.info("Hello, Guest");
                    request.setAttribute("Lang", lang);
                    req.getServletContext().getRequestDispatcher("/regist.jsp").forward(request, response);

                }
            } else {
                LOGGER.info("Login or password is too short by entering");
                boolean lengthMatch = false;
                res.sendRedirect("/filter/index/out?lengthMatch=" + lengthMatch + "&Lang=" + lang);
            }

        } else {
            if (login.length() >= 5 && password.length() >= 5) {
                if (isInDB) {
                    if (isAdmin) {
                        LOGGER.info("Привіт, адмін");
                        // CruiseServiceImpl cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
                        // List<Cruise> allCruises = cruiseService.getAllCruises();
                        request.setAttribute("allCruises", cruiseList);
                        request.setAttribute("Lang", lang);
                        req.getServletContext().getRequestDispatcher("/admin_page_ukr.jsp").forward(request, response);
                    } else {
                        LOGGER.info("Привіт, користувач");
                        request.setAttribute("Lang", lang);
                        request.setAttribute("userId", idByLogPas);
                        req.getServletContext().getRequestDispatcher("/user_page_ukr.jsp").forward(request, response);
                    }

                } else {
                    LOGGER.info("Привіт, гість");
                    request.setAttribute("Lang", lang);
                    req.getServletContext().getRequestDispatcher("/regist_ukr.jsp").forward(request, response);
                }
            } else {
                LOGGER.info("Логін або пароль на вході занадто короткі");
                boolean lengthMatch = false;
                res.sendRedirect("/filter/index/out?lengthMatch=" + lengthMatch + "&Lang=" + lang);
            }

        }

    }

/**
 * Move user to menu.
 * If access 'admin' move to admin menu.
 * If access 'user' move to user menu.
 *//*
    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final User.ROLE role)
            throws ServletException, IOException {


        if (role.equals(User.ROLE.ADMIN)) {

            req.getRequestDispatcher("/WEB-INF/view/admin_menu.jsp").forward(req, res);

        } else if (role.equals(User.ROLE.USER)) {

            req.getRequestDispatcher("/WEB-INF/view/user_menu.jsp").forward(req, res);

        } else {

            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }*/

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return {@code LocalDate} from request
     */
    private LocalDate getLocalDateFromRequest(HttpServletRequest request)  {

        String localDateString = request.getParameter("startdate");
        LocalDate localDate = LocalDate.parse(localDateString);
        return localDate;
    }

    private Period getPeriodFromRequest(HttpServletRequest request)  {
        //example "P1Y2M21D"
        String period = request.getParameter("duration");
        Period p = Period.parse(period);

        return p;
    }
}
