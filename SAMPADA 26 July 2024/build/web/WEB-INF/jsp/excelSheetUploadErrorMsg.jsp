<%--
    Document   : excelSheetUploadErrorMsg
    Created on : Dec 3, 2019, 9:46:42 AM
    Author     : cdac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>excelSheetUploadErrorMsg Page</title>
    </head>
    <body style="text-align: center;">
        <form>


            <h3>Excel Sheet Upload Failed!</h3>
            <p style="color:red;"><b>${Msg}</b></p><br/>

            <p style="text-align: left;"><b>Note:</b><br/>
                1. There should not be any special character except decimal for any numeric column in Excel Sheet.<br/>

                2. Excel Sheet header should be same as format given.<br/>

                3. Date format for Bill upload is dd-mmm-yy ie. 09-Dec-19<br/>

                4. Date format for Bank Statement upload is dd-mm-yyyy ie. 09-12-2019<br/>

                5. In between two columns/rows, cell/cells shouldn't be empty.

            </p>
            <p>&nbsp;</p>
        </form>
        <p>&nbsp;</p>
    </body>
</html>
