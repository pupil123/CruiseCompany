package controller.impl.cruise;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import domain.Cruise;
import domain.builder.CruiseBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;

public class AddNewCruiseCommand implements Command {
    private final CruiseService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(AddNewCruiseCommand.class);

    public AddNewCruiseCommand(CruiseService service) {
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
    public void process(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        try {

            // Cruise cruise = getCruiseFromRequest(request);
            String startDate = request.getParameter("start date");
            String finishDate = request.getParameter("finish date");
            String shipNum = request.getParameter("ship num");

            String lang = request.getParameter("Lang");

           /* Charset.defaultCharset().displayName();

            String encoding = request.getCharacterEncoding();
            if (encoding == null) {
                request.setCharacterEncoding("UTF-8");
            }*/

            String route = request.getParameter("route");
            //   byte[] decode = Base64.getDecoder().decode(request.getParameter("route"));
            //   String s2=decode.toString();



           /* byte bytes[] = route.getBytes("windows-1251");

            String value = URLEncoder.encode(new String(bytes, "UTF-8"), "UTF-8");

            byte[] b1 = route.getBytes("ISO-8859-1");
            System.out.println(b1.toString());
            byte[] b2 = route.getBytes("UTF-8");
            b2.toString();
            byte[] b3 = route.getBytes("Windows-1251");
            byte[] b4 = route.getBytes("Windows-1252");
            request.setCharacterEncoding("ISO-8859-1");
            String decodedString = URLDecoder.decode(route, "ISO-8859-1");
            request.setCharacterEncoding("Cp1251");
            String decodedString2 = URLDecoder.decode(route, "Cp1251");
            request.setCharacterEncoding("Windows-1251");
            String decodedString3 = URLDecoder.decode(route, "Windows-1251");
            request.setCharacterEncoding("Windows-1252");
            String decodedString4 = URLDecoder.decode(route, "Windows-1252");
            request.setCharacterEncoding("UTF-8");
            String decode = URLDecoder.decode(route, "UTF-8");
            String transportString = URLEncoder.encode(route, "UTF-8");
            byte[] barr4 = route.getBytes();


            response.getCharacterEncoding();
            request.setCharacterEncoding("UTF-8");
            response.getCharacterEncoding();
            byte[] barr5 = route.getBytes();
            String corrected8String = new String(transportString.getBytes("UTF-8"), "Cp1251");
*/

            if (LocalDate.parse(startDate).isBefore(LocalDate.parse(finishDate)) &&
                    LocalDate.parse(startDate).isAfter(LocalDate.now())) {

                Cruise cruise = new CruiseBuilder()
                        .buildStartDate(LocalDate.parse(startDate))
                        .buildFinishDate(LocalDate.parse(finishDate))
                        .buildShipId(Integer.parseInt(shipNum))
                        .buildRoute(route)
                        .build();

                int newCruiseId = SERVICE.addNewCruise(cruise);
                LOGGER.info("Cruise " + newCruiseId + " is added successfully");
                response.sendRedirect("/display/addcruise?newCruiseId=" + newCruiseId);
            } else {
                LOGGER.info("Cruise can't be added because date mismatch");
                if (lang.equals("Ukr")) {
                    String s1 = "Будь ласка введіть дату початку , яка раніше дати завершення круїзу." +
                            " Також почток після поточної дати.";
                    byte[] barr = s1.getBytes();
                    ServletOutputStream out = response.getOutputStream();
                    out.write(barr);

                } else if (lang.equals("Eng")) {
                    response.getWriter().println("Please, input start date before finish date" +
                            " and start date after now");
                }
            }


        } catch (SQLException | IOException e) {
            LOGGER.error("Cruise cannot be added");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets builds Cruise from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @throws java.io.IOException when process request or response fails
     * @return instance of {@code Cruise}class from request.
     */
    private Cruise getCruiseFromRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        Cruise cruise = objectMapper.readValue(reader, Cruise.class);

        return cruise;
    }


}