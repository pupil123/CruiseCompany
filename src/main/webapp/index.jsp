<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<body>
<form action="http://localhost:9999/auth" method="post">
    <div style="font-size: 20px; color: green; font-family: arial, helvetica, sans-serif">
        <ex:Hello/>
    </div>
    <img src="<c:url value="/pictures/ship.jpg"/>">
    <table>
        <tr>
            <td>Lang:</td>
            <td><select name="Lang">
                <option value="Eng">Eng</option>
                <option value="Ukr">Ukr</option>
            </select></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" align="left"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" align="left"/></td>
        </tr>

    </table>
    <input type="submit" value="login"/>
</form>
</body>
</html>
