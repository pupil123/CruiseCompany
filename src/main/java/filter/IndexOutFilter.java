package filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static util.Constants.FILTER_INDEX_OUT;

@WebFilter(urlPatterns = FILTER_INDEX_OUT)
public class IndexOutFilter implements Filter {

    // private final CruiseService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(IndexOutFilter.class);

    public IndexOutFilter() {
        super();
        //  SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
    }


    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        // filterChain.doFilter(request, response);

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;


        String lengthMatch = req.getParameter("lengthMatch");
        String lang = req.getParameter("Lang");

        if (lengthMatch.equals("false")) {
            if (lang.equals("Ukr")) {
                String s1 = "Ваш логін та пароль повинні мати мінімум 5 знаків." +
                        " Поверніться та спробуйте знову";
                byte[] barr = s1.getBytes();
                ServletOutputStream out = response.getOutputStream();
                out.write(barr);
            }
            response.getWriter().println("Your login and password should be min 5 characters long." +
                    " Roll back and try again");
        }
        return;
    }
}
