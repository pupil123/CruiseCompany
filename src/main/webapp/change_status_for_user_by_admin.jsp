<%@ page import="java.util.ArrayList" %>
<%@ page import="domain.Cruise" %>
<%@ page import="service.impl.CruiseServiceImpl" %>
<%@ page import="dao.impl.CruiseDAOImpl" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="service.impl.OrderServiceImpl" %>
<%@ page import="dao.impl.OrderDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="service.impl.UserServiceImpl" %>
<%@ page import="dao.impl.UserDAOImpl" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ taglib uri="https://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Cruise company</title>
</head>
<body>

<div align="center">
    <h2>Select user and status</h2>


    <form action="http://localhost:9999/filter/order/status" method="post">
        Select a User:&nbsp;
        <select name="userLogPass">

            <%
                request.setCharacterEncoding("UTF-8");

                UserServiceImpl userService = new UserServiceImpl(new UserDAOImpl());

                Integer cruiseId = Integer.valueOf(request.getParameter("cruiseRoute"));

                OrderServiceImpl orderService = new OrderServiceImpl(new OrderDAOImpl());

                List<Integer> userIds = orderService.getUserListByCruiseId(cruiseId);

                for (Integer userId : userIds) {
            %>
            <option><%=userService.getUserById(userId).getLogin()%> <%=userService.getUserById(userId).getPassword()%>
                <%=orderService.getOrderById(orderService.getOrderIdByCruiseIdUserId(cruiseId, userId)).getStatus()%>
            </option>

            <%
                }
            %>


            <%--<c:forEach items="${mapOrderUser}" var="mapOrder">
                &lt;%&ndash;<option value="${cruise.getStartDate()}">${cruise.getStartDate()}</option>&ndash;%&gt;
                &lt;%&ndash;<option value="${cruise.getRoute()}">${cruise.getRoute()}</option> by ship <option value="${cruise.getShipId()}">${cruise.getShipId()}</option>
&ndash;%&gt;
                <option value="${mapOrder.getId()}">${mapOrderUser.get(mapOrder).getLogin()} ${mapOrderUser.get(mapOrder).getPassword()} ${mapOrder.getStatus} </option>
            </c:forEach>--%>
        </select>

        <br/><br/>
        Select Status:
        <select name="userStatus">
            <option value="UNPAID">UNPAID</option>
            <option value="PAID">PAID</option>
            <option value="COMPLETED">COMPLETED</option>
        </select>

        <input type="hidden" name="cruiseId"  value=<%=cruiseId%>>
        <br/><br/>

        <input type="submit" value="Submit"/>

    </form>

</div>
</body>
</html>