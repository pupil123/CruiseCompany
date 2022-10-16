<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Cruise Company</title>
</head>
<body>

<div align="center">
    <h2>Select a Route:</h2>


    <form action="http://localhost:9999/admin/order/status" method="post">

        <p id="result"></p>
        <select name="cruiseRoute">
            <c:forEach items="${cruiseList}" var="cruise">
                <option value="${cruise.getId()}">${cruise.getRoute()} on ${cruise.getStartDate()}</option>
            </c:forEach>
        </select>

        <br/><br/>
        <input type="hidden" name="Lang" value="${Lang}">
        <input type="submit" value="selected cruise route"/>


        <%-- <button onclick="adm_add_new_cruise.jsp">Add new cruise</button>--%>

    </form>

    <br/><br/>

    <div align="center">

        <form action="adm_add_new_cruise.jsp" method="post">
            <%--<div style="float:left;">--%>
            <%
                session.setAttribute("Lang", request.getAttribute("Lang"));
            %>
            <input type="submit" value="Add new cruise"/>
            <%--  </div>--%>
        </form>

        <%--<button onclick="adm_add_new_ship.jsp">Add new ship</button>--%>

        <form action="adm_add_new_ship.jsp" method="post">
            <%--<div style="float:left;">--%>
            <input type="submit" value="Add new ship"/>
            <%-- </div>--%>
        </form>
    </div>


</div>


<%--<input type="hidden" name="cruiseRoute" value="${cruiseId}">--%>
</body>
</html>