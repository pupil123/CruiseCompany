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

<div align="center"
     style="border-width:3px; border-style:solid; border-color:#0040ff;padding-left:2px;padding-right:2px;">
    <h2>Order N <c:out value="${param.newOrderId}"/></h2>
    <%--<c:set value="${param.order}" var="order"/>--%>
    <div style="font-size: 20px; color: green; font-family: arial, helvetica, sans-serif">
        First name : <c:out value="${user.firstName}"/><br/>
        Last name : <c:out value="${user.lastName}"/><br/>
        Start date : <c:out value="${cruise.startDate}"/><br/>
        UserID : <c:out value="${order.userId}"/><br/>
        Route : <c:out value="${cruise.route}"/><br/>
        Ship num : <c:out value="${cruise.shipId}"/><br/>
        Status : <c:out value="${order.status}"/><br/>



    </div>
    <%-- Route: <c:out value="${param.cruise}" />--%>
    <%-- <table style="text-align: left">
         <tr>
             <td>Route:</td>
             <td><c:out value="${param.cruise.getRoute()}"/></td>
         </tr>
     </table>--%>


    <%--<tr>Route= <c:out value="${param.cruise.getRoute()}"/></tr>--%>


</div>
</body>
</html>