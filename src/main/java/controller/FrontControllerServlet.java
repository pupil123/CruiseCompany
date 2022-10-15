package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static util.Constants.FRONT_CONTROLLER_SERVLET;
import static util.Constants.FRONT_CONTROLLER_SERVLET_COMMON;
import static util.UtilData.COMMANDS_MAP;

/**
 * The {@code FrontControllerServlet} class is front controller command,
 * that is responsible for processing requests by using needed controller
 */
@WebServlet(urlPatterns = FRONT_CONTROLLER_SERVLET_COMMON)
public class FrontControllerServlet extends HttpServlet {

    /**
     * Processes get-request
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       commandProcess(request, response);
    }
    /**
     * Processes get-request
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
  /*  @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       commandProcess(request, response);
    }*/

    /**
     * Processes post-request
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        commandProcess(request, response);
    }

   /* @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        commandProcess(request, response);
    }*/

    /**
     * Receives request and response, gets controller needed for this request,
     * and delegates responsibility for the performing action to this controller.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    private void commandProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commandURL = request.getRequestURI()
                .replaceAll(".*" + FRONT_CONTROLLER_SERVLET, "")
                .replaceAll("\\d+", "");

        Command command = COMMANDS_MAP.get(commandURL);

        command.process(request, response);
    }
}
