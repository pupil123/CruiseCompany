package controller.impl.cruise;

import dao.impl.CruiseDAOImpl;
import domain.Cruise;
import service.CruiseService;
import service.impl.CruiseServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import static util.Constants.DISPLAY_ADD_CRUISE_SERVLET;

@WebServlet(urlPatterns = DISPLAY_ADD_CRUISE_SERVLET)
public class DisplayAddCruiseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CruiseService CRUISE_SERVICE;

    public DisplayAddCruiseServlet() {
        super();
        CRUISE_SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        int newCruiseId = Integer.parseInt(request.getParameter("newCruiseId"));
        String r=request.getParameter("route");
        String encoding = request.getCharacterEncoding();



           /* try {*/
                if (newCruiseId > 0) {

                    Cruise cruise1 = null;
                    try {
                        cruise1 = CRUISE_SERVICE.getCruiseById(newCruiseId);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                    String cruiseInfo= "Cruise N" + cruise1.getId() + "\n" +
                            "Route : " + cruise1.getRoute()+ "\n" +
                            "Start Date : " + cruise1.getStartDate() + "\n" +
                            "Ship N : " + cruise1.getShipId() + "\n";

                    byte[] barr = cruiseInfo.getBytes();
                    ServletOutputStream out = response.getOutputStream();
                    out.write(barr);

                }
            /* catch (SQLException throwables) {
                response.getWriter().println("Cruise is not added");
            }*/
        }
    /*}*/

   /* protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        String returnPage="default.jsp";
        int newCruiseId = Integer.parseInt(request.getParameter("newCruiseId"));
        int newCruiseId1 = Integer.parseInt(String.valueOf(request.getAttribute("newCruiseId")));
        if(newCruiseId1!=0){
            request.getServletContext().getRequestDispatcher("/display/addcruise").forward(request, response);
        }
        if(newCruiseId1==0){
            returnPage="adm_add_new_cruise.jsp";
        }
        request.getRequestDispatcher(returnPage).forward(request,response); //at last line
    }*/
}
