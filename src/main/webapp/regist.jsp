<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

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
<form action="http://localhost:9999/filter/regist" method="post">
    <table style="text-align: left">
        <tr>
            <td>First name:</td>
            <td><input type="text" name="first name" align="left"/></td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td><input type="text" name="last name" align="left"/></td>
        </tr>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" align="left"/></td>
            <td>min 5 char</td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" align="left"/></td>
            <td>min 5 char</td>
        </tr>
    </table>
    <input type="hidden" name="Lang" value="${Lang}">
    <input type="submit" value="Register"/>

    <%-- <button type="button" onclick="window.open('', '_self', ''); window.close();">Discard</button>
 --%>

    <%-- <button onclick="self.close()">Close</butto--%>


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