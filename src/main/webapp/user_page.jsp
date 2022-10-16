<%@ page import="service.CruiseService" %>
<%@ page import="service.impl.CruiseServiceImpl" %>
<%@ page import="dao.impl.CruiseDAOImpl" %>
<%@ page import="domain.Cruise" %>
<%@ page import="java.util.List" %>
<%@ page import="service.impl.ShipServiceImpl" %>
<%@ page import="dao.impl.ShipDAOImpl" %>
<%@ page import="domain.Ship" %><%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Select StartDate and Duration of Cruise - CodeJava.net</title>
</head>
<body>

<div align="center">
    <h2>Select StartDate and Duration of Cruise</h2>
    <form action="http://localhost:9999/user" method="post">
        Select a startdate:&nbsp;
        <select name="startdate">
            <%--<c:forEach items="${listCategory}" var="category">
                <option&lt;%&ndash; value="${category.id}"
                            <c:if test="${category.id eq selectedCatId}">selected="selected"</c:if>
                    >${category.id}&ndash;%&gt;
                    value="${category}"><c:out value="${category}" />
                </option>
            </c:forEach>--%>
            <%-- <%
                 CruiseServiceImpl cruiseDao = new CruiseServiceImpl(new CruiseDAOImpl());
                 // ShipServiceImpl shipDao = new ShipServiceImpl(new ShipDAOImpl());
                 List<Cruise> cruiseList = cruiseDao.getAllCruises();
                 for (int i = 0; i < cruiseList.size(); i++) {
                     // if (shipDao.getShipById(i).getRoutes() != null) {
             %>--%>
            <c:forEach items="${cruiseList}" var="cruise">
                <option value="${cruise.getStartDate()}">${cruise.getStartDate()}</option>
            </c:forEach>

            <%-- <option><%=cruiseList.get(i).getStartDate()  %>
             </option>
             &lt;%&ndash;<option value="<%=listCategory.get(i)%>">listCategory.get(i)</option>&ndash;%&gt;
             <%

                 }
             %>--%>
        </select>

        <br/><br/>
        Select a Duration:&nbsp;
        <select name="cruiseDuration">
            <c:forEach items="${cruiseList}" var="cruise">
                <option value="${cruise.getDuration()}">${cruise.getDuration()} days</option>
            </c:forEach>
        </select>
        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="Lang" value="${Lang}">
        <br/><br/>
        <input type="submit" value="Submit"/>
    </form>
</div>
</body>
</html>