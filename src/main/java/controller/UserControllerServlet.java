package controller;

import dao.impl.CruiseDAOImpl;
import dao.impl.ShipDAOImpl;
import domain.Cruise;
import service.CruiseService;
import service.impl.CruiseServiceImpl;
import service.impl.ShipServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static util.Constants.USER_CONTROLLER_SERVLET;
import static util.UtilData.COMMANDS_MAP;

@WebServlet(urlPatterns = USER_CONTROLLER_SERVLET)
public class UserControllerServlet extends HttpServlet {

    //  private UserService userService;
    // private ShipService shipService;
    //private CruiseService cruiseService;

    public UserControllerServlet() {
        // userService = new UserServiceImpl(new UserDAOImpl());
        //shipService = new ShipServiceImpl(new ShipDAOImpl());
        // cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
    }

    /**
     * Processes get-request
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //commandProcess(request, response);
    /*    CruiseServiceImpl dao = new CruiseServiceImpl(new CruiseDAOImpl());
        List<Cruise> listCategory = dao.getAllCruises();
        request.setAttribute("listCategory", listCategory);*/
        request.getServletContext().getRequestDispatcher("/user_page.jsp").forward(request, response);
        //  listCategory(request, response);

    }


   /* private void listCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CruiseServiceImpl dao = new CruiseServiceImpl(new CruiseDAOImpl());
        List<Cruise> listCatagory = dao.getAllCruises();
        request.setAttribute("listCategory", listCatagory);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user_page.jsp");
        dispatcher.forward(request, response);

    }*/

    /**
     * Processes get-request
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        commandProcess(request, response);
    }

    /**
     * Processes post-request
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       // String userId = request.getParameter("userId");
        int userId = getUserIdFromRequest(request);
        LocalDate locDat = getLocalDateFromRequest(request);
        int days = getPeriodFromRequest(request);
        String lang = request.getParameter("Lang");

       /* String period = request.getParameter("cruiseDuration");
        Period per = Period.parse(period);

        String localDateString = request.getParameter("startdate");
        LocalDate locDat = LocalDate.parse(localDateString);*/
        CruiseService cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
        List<Integer> shipIds = null;
        try {
            shipIds = cruiseService.getShipIdListByDateDuration(locDat, days);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        request.setAttribute("duration",days);
        request.setAttribute("shipIds", shipIds);
        request.setAttribute("startdate", locDat);
        request.setAttribute("userId", userId);
        request.setAttribute("Lang",lang);
        //CruiseServiceImpl cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
        ShipServiceImpl shipService = new ShipServiceImpl(new ShipDAOImpl());
        //   List<Cruise> cruiseList = cruiseDao.getAllCruises();
        List<Integer> cruiseIds=null;
        try {
             cruiseIds=cruiseService.getCruiseIdListByStartDateDuration((LocalDate)request.getAttribute("startdate"),
                    (Integer) request.getAttribute("duration"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Cruise> cruiseList=new ArrayList<>();
        for(Integer integer:cruiseIds){
            try {
                cruiseList.add(cruiseService.getCruiseById(integer));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        request.setAttribute("cruiseList",cruiseList);

        if(lang.equals("Ukr")){
            request.getServletContext().getRequestDispatcher("/userorder_page_ukr.jsp").forward(request, response);
        }else{
        request.getServletContext().getRequestDispatcher("/userorder_page.jsp").forward(request, response);}
       /* String route = request.getParameter("cruiseRoute");
        int duration = Integer.parseInt(request.getParameter("cruiseDuration"));
        int categoryId = Integer.parseInt(request.getParameter("category"));

        request.setAttribute("selectedCatId", categoryId);
*/
        //listCategory(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        commandProcess(request, response);
    }


    /**
     * Receives request and response, gets controller needed for this request,
     * and delegates responsibility for the performing action to this controller.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    private void commandProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commandURL = request.getRequestURI()
                .replaceAll(".*" + USER_CONTROLLER_SERVLET, "")
                .replaceAll("\\d+", "");

        Command command = COMMANDS_MAP.get(commandURL);

        command.process(request, response);
    }

    private LocalDate getLocalDateFromRequest(HttpServletRequest request)  {

        String localDateString = request.getParameter("startdate");
        LocalDate localDate = LocalDate.parse(localDateString);
        return localDate;
    }

    private int getPeriodFromRequest(HttpServletRequest request) {
        //example "P1Y2M21D"
        String period = request.getParameter("cruiseDuration");
   /*     period = "P0Y0M" + period + "D";
        Period p = Period.parse(period);*/

        String days = period.split(" ")[0];

        return Integer.parseInt(days);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     */
    private int getUserIdFromRequest(HttpServletRequest request) throws IOException {

        String userId = request.getParameter("userId");

        return Integer.parseInt(userId);
    }
}


