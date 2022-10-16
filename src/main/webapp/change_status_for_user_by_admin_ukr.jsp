<%@ page import="service.impl.OrderServiceImpl" %>
<%@ page import="dao.impl.OrderDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="service.impl.UserServiceImpl" %>
<%@ page import="dao.impl.UserDAOImpl" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <h2>Оберіть користувача та його статус</h2>

    <form action="http://localhost:9999/filter/order/status" method="post">
        Оберіть користувача:&nbsp;
        <select name="userLogPass">
            <c:forEach items="${mapOrderUser_EntrySet}" var="mapEntry">
                <%--<option value="${cruise.getStartDate()}">${cruise.getStartDate()}</option>--%>
                <%--<option value="${cruise.getRoute()}">${cruise.getRoute()}</option> by ship <option value="${cruise.getShipId()}">${cruise.getShipId()}</option>
--%>
                <option value="${mapEntry.key.id}">${mapEntry.value.login} ${mapEntry.value.password} ${mapEntry.key.status}</option>
            </c:forEach>
        </select>
        <%-- <input type="hidden" name="startdate" value=<%=request.getAttribute("startdate")%>>
         <input type="hidden" name="userId" value=<%=request.getAttribute("userId")%>>--%>
        <br/><br/>
        Оберіть статус:
        <select name="userStatus">
            <option value="UNPAID">UNPAID</option>
            <option value="PAID">PAID</option>
            <option value="COMPLETED">COMPLETED</option>
        </select>
        <%--<option value="<%=listCategory.get(i)%>">listCategory.get(i)</option>--%>
        <%--<input type="hidden" name="cruiseId" value=<%=cruiseId%>>--%>
        <br/><br/>
        <%-- <%request.setAttribute("startdate",request.getAttribute("startdate"));%>--%>
        <%--<input type="hidden" name="startdate" value=<%request.getAttribute("startdate");%></input>--%>
        <input type="submit" value="Обрати"/>

    </form>

</div>
</body>
</html>