<%-- 
    Document   : selectBillDetails
    Created on : Jan 5, 2021, 11:16:27 AM
    Author     : shubham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >
        <title>JSP Page</title>
    </head>
    <style>
        legend 
        { 
            display: block;
            color:#003366;
        }
        fieldset
        {
            /*background-color:#fff7c4;*/
            border: 3px solid #964000;
            max-width:500px;
            padding:16px;	
            border-radius: 25px;
            margin-left: 25%;
        }
        
        #s1 {
            background-color: chocolate;
            color: white;
            height: 40px;
            width: 120px;
        }
    </style>
    <body>
        <fieldset>
            <legend>Select Corporate to be Adjusted :</legend>
            <form name="form1" method="post" action="viewAllCorpDetailsForAdjustments.htm">
                <div align="center">
                    <b style="color:black;">Corporate Name :</b>&nbsp;&nbsp&nbsp; 
                                    
                                    <select name="corpname" required style="height: 30px;">
                                        <option value=""></option>
                                        <c:forEach items="${corps}" var="sd">
                                            <option value="${sd.corporateName}">${sd.corporateName}</option>
                                        </c:forEach>
                                   </select>
                <br><br>
                
                <input type="submit" name="submit" value="Submit">
                </div>
            </form>
        </fieldset>
        
        <br><br>
        <br><br>
        <br>
    </body>
</html>
