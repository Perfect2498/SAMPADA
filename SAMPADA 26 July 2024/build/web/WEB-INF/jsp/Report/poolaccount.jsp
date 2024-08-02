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

        <script type="text/javascript">
        $(document).ready(function()
        {   
            $(".monthPicker").datepicker({
                dateFormat: 'mm/yy',
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                yearRange: '2018:+10',
                
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
        
//         function checkMonthpicker(){
//    
//    
//                    var startMonth=document.getElementById('monthYear').value;
//
//                    if(startMonth == "")
//                    {
//                        alert("Select Month first!!");
//                        return false;     
//                    }
//                    else
//                    {
////                      alert(startMonth);
//                        var d = new Date();
//                        var n = d.getMonth();
//                        n=n+1;
//                        var y = d.getFullYear();
//                       
//                        var MonthYr=startMonth.split('/');
////                        alert(MonthYr[0]);
////                        alert(MonthYr[1]);
//                        
//                        if(MonthYr[1] > y){
//                            alert("Select year upto current year only!!");
//                            $('#monthYear').val('');
//                            return false; 
//                        }
//                        else if(MonthYr[1] <= y && MonthYr[0] > n){
//                            alert("Select month upto current month only!!");
//                            $('#monthYear').val('');
//                            return false; 
//                        }
//                        else
//                       
//                       return true;
//                    }
//       
//            }

           function validate()
            {
                var monthYear = document.getElementById("monthYear").value;
                
                if (monthYear == "")
                {
                    alert('Please select the month and Year');
                    return false;
                }
                
                 return true;
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
     <script>
            function getDocHeight(doc) {
                doc = doc || document;

                var body = doc.body, html = doc.documentElement;
                var height = Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);
                return height;

            }

            function setIframeHeight(id) {
                var ifrm = document.getElementById(id);
                var doc = ifrm.contentDocument ? ifrm.contentDocument : ifrm.contentWindow.document;
                ifrm.style.visibility = 'hidden';
                ifrm.style.height = "10px"; // reset to minimal height in case going from longer to shorter doc
                ifrm.style.height = getDocHeight(doc) + 5 + "px";
                ifrm.style.visibility = 'visible';

                var iframe = window.parent.document.getElementById('content');
                var container = document.getElementById('appcontent');
                iframe.style.height = container.offsetHeight + 50 + 'px';

            }

            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 30 + 'px';

            }
        </script>
   
        
    </head>
    <body style="height: 500px;" >
        <form>
         <fieldset>
                <legend>
                    <h3>Pool Account Report</h3></legend>
         <table><tr><td>Select Month & Year:</td>
                    <td><input type="text" id="monthYear" name="monthYear" class="monthPicker" readonly/></td></tr>
         </table>
<br/><br/>
                   

      <input type="submit" name="generate" value="Generate"  onclick="return validate();" style="margin-left:45%;"/>

 </fieldset>
    </form>
    
    
</body>
</html>