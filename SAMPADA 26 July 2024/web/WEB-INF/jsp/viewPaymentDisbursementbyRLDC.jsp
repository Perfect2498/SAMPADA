<%-- 
    Document   : viewPaymentDisbursementbyRLDC
    Created on : Jul 5, 2019, 3:05:02 PM
    Author     : JaganMohan
--%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">    
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>"> 
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script> 
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>        	
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>        
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>      
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script> 
       
<script>
    
 
function validate()
{
  var rowcnt=document.getElementById("count").value;
    
    if(rowcnt==0)
    {
       return false;
      }
      else
      {
           if(confirm("Are you want to Delete !!!!!"))
           {                       
              return true;       
          }
          else
          {
              return false;
          }
      }
}
</script>
    </head>
       <body style="text-align: center; alignment-adjust: central;width: 95%;min-height: 500px;">
        <form name="checkerForm">
        <p>&nbsp;</p>
        
        <h3 align="center" style="color:#003366;" >Disbursement Details - Maker </h3>
        
        <table align="center" style="width:80%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>                    
                    <th>Billing Date</th>                  
                    <th>Week-ID</th>   
                    <th>Pool Member</th> 
                    <th>Total Amount</th>                                      
                    <th>Disburse Amount</th>
                    <th>Disburse Status</th>
                    <th>Disburse Date</th>
                    <th>Bill Type</th>
                    <th>Category</th>
                </tr>
            </thead>
            <tbody>     
                 <% int cnt=0;%>
                <c:forEach items="${confirmList}" var="ele">  
                     <% 
                        cnt++;
                    %>   
                         <tr>                              
                            <td>${ele.billingDate}</td> 
                            <td>${ele.weekId}</td> 
                            <td>${ele.corporates.corporateName}</td>
                            <td>${ele.totalAmount}</td>                            
                            <td>${ele.disburseAmount}</td> 
                            <td>${ele.disburseStatus}</td>
                            <td>${ele.disbursalDate}</td>
                            <td>${ele.billType}</td>
                            <td>${ele.disburseCategory}</td>
                                                    
                        </tr>
                  
            </c:forEach>                
            </tbody>
        </table>
                 <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>
        <p align="center">
            
            <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bdelete"  value="Delete"/>
        </p>
       
        </form>
    </body>
</html>

