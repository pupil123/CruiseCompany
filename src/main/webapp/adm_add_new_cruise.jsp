<%@ page import="service.impl.ShipServiceImpl" %>
<%@ page import="dao.impl.ShipDAOImpl" %>
<%@ page import="domain.Ship" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  <%-- xmlns="http://www.w3.org/1999/xhtml"
        xmlns:h="http://java.sun.com/jsf/html"
        xmlns:ui="http://java.sun.com/jsf/facelets"
        xmlns:mkyong="http://mkyong.com/facelets"--%>
>


<%--<script language="javascript" type="text/javascript">
    var newWindow;
    function windowClose() {
        window.open('','_parent','');
        window.close();
    }
</script>--%>
<%-- <style>
     #tableName td {
         text-align: left;
     }
 </style>--%>
<body>
<%--<form action="http://localhost:9999/seaport/admin/cruise/add" method="post">--%>

<%--<form action="http://localhost:9999/seaport/cruise/add" method="post">--%>
<form action="http://localhost:9999/filter/cruise" method="post">
    <table style="text-align: left">
        <tr>
            <td>Start date:</td>
            <td><input type="text" name="start date" align="left"/></td>
        </tr>
        <tr>
            <td>Finish date:</td>
            <td><input type="text" name="finish date" align="left"/></td>
        </tr>
        <tr>
            <td>Ship num:</td>
            <td><select name="ship num"><%--<%
                ShipServiceImpl shipService = new ShipServiceImpl(new ShipDAOImpl());
                List<Ship> ships = shipService.getAllShips();
                request.setAttribute("ships", ships);
                {
            %>--%>
                <c:forEach items="${ships}" var="ship">
                    <option value="${ship.id}">${ship.id}</option>
                </c:forEach>

                <%-- <option><%=ship.getId()%>--%>
               <%-- </option>
                <%
                    }
                %>--%>
            </select></td>
        </tr>
        <tr>
            <td>Route:</td>
            <td><input type="text" name="route" align="left"/></td>
        </tr>
    </table>
    <input type="hidden" name="Lang" value=<%=session.getAttribute("Lang").toString()%>>
    <input type="submit" value="Add New Cruise"/>

    <%-- <mkyong:button
             buttonSubmitLabel="Submit"
             buttonResetLabel="Reset" />--%>

    <%-- <button type="button" onclick="window.open('', '_self', ''); window.close();">Discard</button>
 --%>
    <%-- <button onclick="self.close()">Close</button>--%>


    <%-- <input type="button" value="Close this window" onclick="windowClose();">

     <button onclick="closeWindow()">Close New Window </button>
 --%>
</form>
<%--

<br/><br/>

<form>
    <td align="center"><input type="submit" value="Close" onclick="window.close();" /></td>
</form>

<button type="button"
        onclick="window.open('', '_self', ''); window.close();">Discard</button>

<button onclick="closeWindow()">Close New Window </button>

</body>

<button onclick="self.close()">Close</button>


<input type="button" value="Close this window" onclick="windowClose();">
--%>

</body>
</html>