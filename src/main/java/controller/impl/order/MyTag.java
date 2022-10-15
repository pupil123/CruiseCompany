package controller.impl.order;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class MyTag extends SimpleTagSupport {

    public void doTag() throws  IOException {
        JspWriter out = getJspContext().getOut();
    //    int newOrderId = new AddNewOrderCommand();

        out.println("Cruise Company ");
    }
}
