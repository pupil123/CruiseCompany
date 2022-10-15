<%@ page import="java.util.ArrayList" %>
<%@ page import="domain.Cruise" %>
<%@ page import="service.impl.ShipServiceImpl" %>
<%@ page import="dao.impl.ShipDAOImpl" %>
<%@ page import="service.impl.CruiseServiceImpl" %>
<%@ page import="dao.impl.CruiseDAOImpl" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Cruise Company</title>

</head>
<body>

<div align="center" style="font-size: 20px; color: green; font-family: arial, helvetica, sans-serif">
    <h2>Оберіть маршрут круїзу:</h2>


    <form action="change_status_for_user_by_admin_ukr.jsp" method="post">

        <p id="result"></p>
        <select name="cruiseRoute">

                <c:forEach items="${cruiseList}" var="cruise">

                    <option value="${cruise.getId()}">${cruise.getRoute()} on ${cruise.getStartDate()}</option>
                </c:forEach>

        </select>

        <br/><br/>
        <%-- <input type="hidden" name="userId" value="${shipService.getShipById(cruise.getShipId()).getRoutes()}">--%>


        <%-- <input type="hidden" name="startdate" value=<%=request.getAttribute("startdate")%>>--%>


        <%--
                <script >

                    function GetSelectedText() {
                        var e = document.getElementById("cruiseId");
                        var result = e.options[e.selectedIndex].text;
                        document.getElementById("result").innerHTML = result;

                    }
                </script>--%>
        <%-- <%request.setAttribute("startdate",request.getAttribute("startdate"));%>--%>
        <%--<input type="hidden" name="startdate" value=<%request.getAttribute("startdate");%></input>--%>
        <%--<% session.setAttribute("cruiseId",%>=result<%)%>>--%>
        <%--<input type="hidden" name="cruiseRoute" value="GetSelectedText()">
        <% session.setAttribute("cruiseId", "Submit");%>--%>
        <input type="submit" value="Обрати маршрут круїзу"/>


        <%-- <button onclick="adm_add_new_cruise.jsp">Add new cruise</button>--%>

    </form>

    <br/><br/>

    <div align="center">

        <form action="adm_add_new_cruise_ukr.jsp" method="post">
            <%--<div style="float:left;">--%>
                <%session.setAttribute("Lang", request.getAttribute("Lang"));%>

            <input type="submit" value="Додати новий круїз"/>
            <%--  </div>--%>
        </form>

        <%--<button onclick="adm_add_new_ship.jsp">Add new ship</button>--%>

        <form action="adm_add_new_ship_ukr.jsp" method="post">
            <%--<div style="float:left;">--%>
            <input type="submit" value="Додати новий корабель"/>
            <%-- </div>--%>
        </form>
    </div>


</div>


<%--<input type="hidden" name="cruiseRoute" value="${cruiseId}">--%>
</body>
</html>