<%-- 
    Document   : viewReportRLDC
    Created on : Sep 27, 2018, 12:03:30 PM
    Author     : cdac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>JSP Page</title>
         <link href="<c:url value="/css/mystyle.css"/>" rel="stylesheet" >
<!--         <link rel="stylesheet" type="text/css"  href="<c:url value="/css/bootstrap.min.css" />">-->
<!--    <link rel="stylesheet" type="text/css" href="<c:url value="/css/dataTables.bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/fixedHeader.bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/responsive.bootstrap.min.css"/>">  -->
          
     
    <script src="<c:url value="js/jquery-1.9.1.js" />"></script>
         <script src="<c:url value="js/jquery-ui.js" />" ></script>
         <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
         <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
         <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
         <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
         <link type="text/css" href="<c:url value="/jquery.ui.datepicker.monthyearpicker.css"/>" rel="stylesheet" />
         <script type="text/javascript" src="<c:url value="/jquery.ui.datepicker.monthyearpicker.js"/>"></script>
         <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
         <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
    
<!--    <script type="text/javascript" language="javascript" src="<c:url value="/js/jquery-3.3.1.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.bootstrap.min.js" />"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.fixedHeader.min.js" />"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.responsive.min.js" />"> </script>
    <script type="text/javascript" language="javascript" src="<c:url value="/js/responsive.bootstrap.min.js" />"> </script>-->
    <script type="text/javascript">
         $(document).ready(function()
        {   
            $(".monthPicker").datepicker({
                dateFormat: 'mm/yy',
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                yearRange: '2019:+10',

                onClose: function(dateText, inst) {
                    var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                    var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                    $(this).val($.datepicker.formatDate('mm/yy', new Date(year, month, 1)));
                }
            });

            $(".monthPicker").focus(function () {
                $(".ui-datepicker-calendar").hide();
                $("#ui-datepicker-div").position({
                    my: "center top",
                    at: "center bottom",
                    of: $(this)
                });
            });
        });
        
 </script>
 <script>
     
      function full()
        {
            yeara();
            yearq();
            year();
            week();
        }
        
        
            function yeara()
            {
                var start = 2019;
//                var end = new Date().getFullYear();
                 var end = 2029;
                var options = "<option value='' selected>Year</option>";
                for(var year = start ; year <=end; year++)
                {
                  options += "<option>"+ year +"</option>";
                }
                document.getElementById("yeara").innerHTML = options;
            }
            function yearq()
            {
                var start = 2019;
//                var end = new Date().getFullYear();
                 var end = 2029;
                var options = "<option value='' selected>Year</option>";
                for(var year = start ; year <=end; year++)
                {
                  options += "<option>"+ year +"</option>";
                }
                document.getElementById("yearq").innerHTML = options;
            }
            function year()
            {
                var start = 2019;
//                var end = new Date().getFullYear();
                 var end = 2029;
                var options = "<option value='' selected>Year</option>";
                for(var year = start ; year <=end; year++)
                {
                  options += "<option>"+ year +"</option>";
                }
                document.getElementById("year").innerHTML = options;
            }
            function week()
            {
                
                var s=1;
                var l=53;
                var options = "<option value='' selected>week</option>";
                for(var week = s ; week <=l; week++)
                {
                  options += "<option>"+ week +"</option>";
                }
                document.getElementById("week").innerHTML = options;
          
            }
            
           function validate()
            {
                var duration = document.getElementById("dbType").value;
                var monthYear = document.getElementById("monthYear").value;
                var year = document.getElementById("year").value;
                var week = document.getElementById("week").value;
                var quar = document.getElementById("quar").value;
                var yearq = document.getElementById("yearq").value;
                 var yeara = document.getElementById("yeara").value;
                 
                if (duration ==="SELECT")
                {
                    alert('Please select the Duration');
                    return false;
                }
                
                if (duration ==="MONTHLY")
                {
                   if (monthYear ==="")
                    {
                    alert('Please select the month and Year');
                    return false;
                    }
                }
                
                if (duration ==="WEEKLY")
                {
                   if (year == "")
                    {
                    alert('Please select the  Year');
                    return false;
                    }
                    if (week == "")
                    {
                    alert('Please select the  week');
                    return false;
                    }
                }
        
                 if (duration ==="QUARTERLY")
                {
                   if (yearq == "")
                    {
                    alert('Please select the  Year');
                    return false;
                    }
                    if (quar ==="SELECT")
                    {
                    alert('Please select the quarter');
                    return false;
                    }
                }
                
                if (duration ==="ANNUALLY")
                {
                   if (yeara == "")
                    {
                    alert('Please select the  Year');
                    return false;
                    }
                    
                }
                
                
                
                 return true;
                 
            }
            
            
 </script>
 <script>
function showDiv(element)
{
    if(element.value ==="WEEKLY")
    {
      
    document.getElementById("WeeklyType").style.display =  'block';
    document.getElementById("MonthlyType").style.display =  'none';
    document.getElementById("QuarterlyType").style.display =  'none';
    document.getElementById("AnnualType").style.display =  'none';
    }
    if(element.value ==="MONTHLY")
    {
      
    document.getElementById("WeeklyType").style.display =  'none';
    document.getElementById("MonthlyType").style.display =  'block';
    document.getElementById("QuarterlyType").style.display =  'none';
     document.getElementById("AnnualType").style.display =  'none';
    }
    if(element.value ==="QUARTERLY")
    {
      
    document.getElementById("WeeklyType").style.display =  'none';
    document.getElementById("MonthlyType").style.display =  'none';
    document.getElementById("QuarterlyType").style.display =  'block';
     document.getElementById("AnnualType").style.display =  'none';
    }
    if(element.value ==="ANNUALLY")
    {
      
    document.getElementById("WeeklyType").style.display =  'none';
    document.getElementById("MonthlyType").style.display =  'none';
    document.getElementById("QuarterlyType").style.display =  'none';
     document.getElementById("AnnualType").style.display =  'block';
    }
    if(element.value ==="SELECT")
    {
    document.getElementById("WeeklyType").style.display =  'none';
    document.getElementById("MonthlyType").style.display =  'none';
    document.getElementById("QuarterlyType").style.display =  'none';
     document.getElementById("AnnualType").style.display =  'none';
    }
}

 
        </script>
     

        <style>
        .ui-datepicker-calendar {
            display: none;
            }
            
            div.ui-datepicker{

                font-size:14px;

            }    
        </style>
        
       <style>
      table { margin: auto; } 

            input[type=submit] {

                padding: 10px 14px;
                border: 2px solid #964000;
                font-weight:bold;
                color:white;
                alignment-adjust:central;
                background-color: #964000;
            }

            input[type=submit]:hover, input[type=submit]:focus {
                background-color: #003366;
                color:white;
            }
            td{
                color:#003366;
            }
    </style>
    
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
    </style>
     
    </head>
    <body onload="full()" style="height: 500px;" >
        <form>
         <fieldset>
                <legend>
                    <h3> ${title} Report</h3></legend>
                    
                    
                     
                    
                <table>
                        
                        <tr><td>SELECT-Duration:</td>
                           <td> 
                
                            <select name="dbType" id="dbType" onchange="showDiv(this)">
                                <option value="SELECT">SELECT</option>
                                <option value="WEEKLY">WEEKLY</option>
                                <option value="MONTHLY">MONTHLY</option>
                                <option value="QUARTERLY">QUARTERLY</option>
                                <option value="ANNUALLY">ANNUALLY</option>
                              </select>
                     
                               </td>
                   </table> 
                    
          <br/><br/>    

          <div id="WeeklyType"  style="display:none ;">          
            <table><tr><td>Select Year:</td>
                 <td><select name="year" id="year"></select></td></tr>
             </table>
            <table><tr><td>Select  Week:</td>
                <td><select name="week" id="week"></select></td></tr>
            </table>
          </div>
          <div id="MonthlyType"  style="display:none ;">          
            <table><tr><td>Select Month & Year:</td>
                    <td><input type="text" id="monthYear" name="monthYear" class="monthPicker" readonly/></td>
             </tr>
            </table>
          </div>
           <div id="QuarterlyType"  style="display:none ;">   
                 <table><tr><td>Select Year:</td>
                 <td><select name="yearq" id="yearq"></select></td></tr>
                </table>
               <table>
                        
                        <tr><td>Select Quarter:</td>
                           <td> 
                
                            <select name="quar" id="quar" >
                                <option value="SELECT">SELECT</option>
                                <option value="quar1">first quarter [1 January – 31 March]</option>
                                <option value="quar2">second quarter [ 1 April – 30 June]</option>
                                <option value="quar3">third quarter [1 July – 30 September]</option>
                                <option value="quar4">fourth quarter [1 October – 31 December]</option>
                              </select>
                     
                               </td>
                        </tr>
                   </table> 
              
          </div>
           <div id="AnnualType"  style="display:none ;">          
            <table><tr><td>Select Year:</td>
                 <td><select name="yeara" id="yeara"></select></td></tr>
             </table>           
          </div>
<br/><br/>
                    <table>
                        
                        <tr><td>Select the Pool Member:</td>
                          
                
                        <td><select name="corpId" id="corpId">
                                <!--<option value="-1">Select</option>-->
                                <c:forEach items="${corporateList}" var="item">
                                    <option value="${item}"><c:out value="${item}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                          </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                             
                   </table> 

<br/><br/>
      <input type="submit" name="generate" value="Generate"  onclick="return validate();" style="margin-left:45%;"/>

 </fieldset>
    </form>
    
                    <br><br>
                    <br><br>
                    <br>
    
</body>
</html>