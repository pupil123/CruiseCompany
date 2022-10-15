<%@ page import="service.CruiseService" %>
<%@ page import="service.impl.CruiseServiceImpl" %>
<%@ page import="dao.impl.CruiseDAOImpl" %>
<%@ page import="domain.Cruise" %>
<%@ page import="java.util.List" %>
<%@ page import="service.impl.ShipServiceImpl" %>
<%@ page import="dao.impl.ShipDAOImpl" %>
<%@ page import="domain.Ship" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %><%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Select route - CodeJava.net</title>
</head>
<body>

<div align="center">
    <h2>Select route</h2>


    <%--<form action="http://localhost:9999/seaport/order/add" method="post">--%>
        <form action="http://localhost:9999/filter/order" method="post">
        Select a Route:&nbsp;
        <select name="cruiseRoute">
            <%--<c:forEach items="${listCategory}" var="category">
                <option&lt;%&ndash; value="${category.id}"
                            <c:if test="${category.id eq selectedCatId}">selected="selected"</c:if>
                    >${category.id}&ndash;%&gt;
                    value="${category}"><c:out value="${category}" />
                </option>
            </c:forEach>--%>
        <%--    <%
                //ArrayList<Integer> shipIds = (ArrayList) request.getAttribute("shipIds");
                CruiseServiceImpl cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
                ShipServiceImpl shipService = new ShipServiceImpl(new ShipDAOImpl());
                //   List<Cruise> cruiseList = cruiseDao.getAllCruises();
                List<Integer> cruiseIds=cruiseService.getCruiseIdListByStartDateDuration((LocalDate)request.getAttribute("startdate"),
                        (Integer) request.getAttribute("duration"));
             //   List<Ship> shipList = shipDao.getAllShips();
               // for (int i = 0; i < cruiseIds.size(); i++) {
                    // if (shipDao.getShipById(i).getRoutes() != null) {
                    for (Integer cruiseId: cruiseIds){
            %>
            <option><%=cruiseService.getCruiseById(cruiseId).getRoute()%> by ship <%=cruiseService.getCruiseById(cruiseId).getShipId()%></option>
            &lt;%&ndash;<option value="<%=listCategory.get(i)%>">listCategory.get(i)</option>&ndash;%&gt;
            <%
                     }
            %>--%>
                <c:forEach items="${cruiseList}" var="cruise">
                    <%--<option value="${cruise.getStartDate()}">${cruise.getStartDate()}</option>--%>
                    <%--<option value="${cruise.getRoute()}">${cruise.getRoute()}</option> by ship <option value="${cruise.getShipId()}">${cruise.getShipId()}</option>
--%>
                    <option value="${cruise.getId()}">${cruise.getRoute()} by ship ${cruise.getShipId()}</option>
                </c:forEach>

        </select>
        <input type="hidden" name="startdate" value=<%=request.getAttribute("startdate")%>>
        <input type="hidden" name="userId" value=<%=request.getAttribute("userId")%>>
        <input type="hidden" name="Lang" value=<%=request.getAttribute("Lang")%>>
        <br/><br/>

       <%-- <%request.setAttribute("startdate",request.getAttribute("startdate"));%>--%>
        <%--<input type="hidden" name="startdate" value=<%request.getAttribute("startdate");%></input>--%>
        <input type="submit" value="Submit"/>

    </form>

</div>
</body>
</html>