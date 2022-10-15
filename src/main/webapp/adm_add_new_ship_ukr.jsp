<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>


<body>
<form action="http://localhost:9999/filter/ship" method="post">
    <table style="text-align: left">
        <tr>
            <td>Місткість пасажирів:</td>
            <td><input type="text" name="capacity" align="left"/></td>
        </tr>
        <tr>
            <td>Кількість відвідуємих портів:</td>
            <td><input type="text" name="num visited ports" align="left"/></td>
        </tr>
        <tr>
            <td>Персонал:</td>
            <td><input type="text" name="staffs" align="left"/></td>
        </tr>
    </table>
    <input type="submit" value="Додати новий корабель"/>

    <%-- <button type="button" onclick="window.open('', '_self', ''); window.close();">Discard</button>
 --%>
    <%-- <button onclick="self.close()">Close</button>--%>


    <%-- <input type="button" value="Close this window" onclick="windowClose();">

     <button onclick="closeWindow()">Close New Window </button>
 --%>
</form>

</body>
</html>