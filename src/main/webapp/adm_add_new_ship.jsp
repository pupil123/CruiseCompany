<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>


<body>
<form action="http://localhost:9999/filter/ship" method="post">
    <table style="text-align: left">
        <tr>
            <td>Capacity:</td>
            <td><input type="text" name="capacity" align="left"/></td>
        </tr>
        <tr>
            <td>Number of visited ports:</td>
            <td><input type="text" name="num visited ports" align="left"/></td>
        </tr>
        <tr>
            <td>Staffs:</td>
            <td><input type="text" name="staffs" align="left"/></td>
        </tr>
    </table>
    <input type="submit" value="Add New Ship"/>

    <%-- <button type="button" onclick="window.open('', '_self', ''); window.close();">Discard</button>
 --%>
    <%-- <button onclick="self.close()">Close</button>--%>


    <%-- <input type="button" value="Close this window" onclick="windowClose();">

     <button onclick="closeWindow()">Close New Window </button>
 --%>
</form>

</body>
</html>