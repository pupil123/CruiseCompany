<%@ page import="service.impl.CruiseServiceImpl" %>
<%@ page import="dao.impl.CruiseDAOImpl" %>

<%@ page import="java.util.List" %>
<%@ page import="service.impl.ShipServiceImpl" %>
<%@ page import="dao.impl.ShipDAOImpl" %>
<%@ page import="domain.Ship" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Select route</title>
</head>
<body>

<div align="center">
    <h2>Оберіть маршрут круїзу</h2>

    <form action="http://localhost:9999/filter/order" method="post">
        Оберіть маршрут:&nbsp;
        <select name="cruiseRoute">
            <c:forEach items="${cruiseList}" var="cruise">

                <option value="${cruise.getId()}">${cruise.getRoute()} by ship ${cruise.getShipId()}</option>
            </c:forEach>
        </select>
        <input type="hidden" name="startdate" value="${startdate}">
        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="Lang" value="${Lang}">
        <br/><br/>

        <%-- <%request.setAttribute("startdate",request.getAttribute("startdate"));%>--%>
        <%--<input type="hidden" name="startdate" value=<%request.getAttribute("startdate");%></input>--%>
        <input type="submit" value="Обрати"/>

    </form>

</div>
</body>
</html>