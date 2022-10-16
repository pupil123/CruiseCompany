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
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Select StartDate and Duration of Cruise</title>
</head>
<body>

<div align="center">
    <h2>Оберіть дату початку та тривалість круїзу</h2>
    <form action="http://localhost:9999/user" method="post">
        Оберіть дату початку круїзу:&nbsp;
        <select name="startdate">
            <%--<c:forEach items="${listCategory}" var="category">
                <option&lt;%&ndash; value="${category.id}"
                            <c:if test="${category.id eq selectedCatId}">selected="selected"</c:if>
                    >${category.id}&ndash;%&gt;
                    value="${category}"><c:out value="${category}" />
                </option>
            </c:forEach>--%>
            <c:forEach items="${cruiseList}" var="cruise">
                <option value="${cruise.getStartDate()}">${cruise.getStartDate()}</option>
            </c:forEach>
        </select>

        <br/><br/>
        Оберіть тривалість:&nbsp;
        <select name="cruiseDuration">


            <c:forEach items="${cruiseList}" var="cruise">
                <option value="${cruise.getDuration()}">${cruise.getDuration()} днів</option>
            </c:forEach>
        </select>
        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="Lang" value="${Lang}">
        <br/><br/>
        <input type="submit" value="Обрати"/>
    </form>
</div>
</body>
</html>