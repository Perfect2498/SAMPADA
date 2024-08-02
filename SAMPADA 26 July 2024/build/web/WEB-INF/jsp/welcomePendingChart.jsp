<%--
   Document   : welcomePendingChart
   Created on : Jul 8, 2019, 8:21:35 AM
   Author     : JaganMohan

--%>



<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>



<!DOCTYPE html>

<html>
   <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
       <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
       <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
       <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css" />" />


       <script src="<c:url value="/js/jquery.min.js"/>" ></script>
       <script src="<c:url value="/js/bootstrap.min.js" />" ></script>
       <script src="<c:url value="/js/loader.js" />" ></script>
       <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>




       <script>
           $(document).ready(function () {


               var arrSales = [['WeekID', 'Type', 'Total']];
               $.ajax({
                    type: "POST",
                    url: "welcomePendingBarChart.htm",
                    success: function (data) {
                       var dataObject = jQuery.parseJSON(data);                
                       // Define an array and assign columns for the chart.
                       for (var i = 0; i <= dataObject.aaData.length - 1; i++)
                       {
                           for (var j = 0; j <= dataObject.aaData[i].length - 1; j++)
                           {
                              
                               arrSales.push([dataObject.aaData[i][j][0], dataObject.aaData[i][j][1], dataObject.aaData[i][j][2]]);
                              
                           }
                       }
                   
                       google.charts.load('current', {'packages': ['bar']});                      
                       google.charts.setOnLoadCallback(drawColColors);

                   },
                    error: function () {
                       console.log('error');
                   }
               });//end of ajax


               function drawColColors() {
                   // alert(arrSales);
                   var data = google.visualization.arrayToDataTable(arrSales);


                   var options = {
                        // width: 1000,
                       legend: {position: 'right'},
                        chart: {
                            title: 'Pending Payment from Pool Member ',
                            subtitle: 'Weekwise'},
                        axes: {
                            x: {
                                0: {side: 'bottom', label: 'Week ID'} // Top x-axis.
                           }
                       }


                   };


                   var chart = new google.charts.Bar(document.getElementById('paychart_div'));
                   // Convert the Classic options to Material options.
                   chart.draw(data, google.charts.Bar.convertOptions(options));
               }


               var arrRecv = [['WeekID', 'Type', 'Total']];
               $.ajax({
                    type: "POST",
                    url: "welcomeReceivableBarChart.htm",
                    success: function (data) {




                       var dataObject = jQuery.parseJSON(data);
                       //alert(dataObject.aaData.length);


                       // Define an array and assign columns for the chart.
                       for (var i = 0; i <= dataObject.aaData.length - 1; i++)
                       {
                           for (var j = 0; j <= dataObject.aaData[i].length - 1; j++)
                           {
                               //alert(dataObject.aaData[i][j][0]);
                               arrRecv.push([dataObject.aaData[i][j][0], dataObject.aaData[i][j][1], dataObject.aaData[i][j][2]]);
                               //alert(arrSales);
                           }
                       }


                       // alert(arrSales);
                       google.charts.load('current', {'packages': ['bar']});
                       //google.charts.load('visualization', { packages: ['corechart'] });
                       google.charts.setOnLoadCallback(drawColRecvColors);






                   },
                    error: function () {
                       console.log('error');
                   }
               });//end of ajax


               function drawColRecvColors() {
                   // alert(arrSales);
                   var data = google.visualization.arrayToDataTable(arrRecv);


                   var options = {
                        //  width: 1000,
                       legend: {position: 'right'},
                        chart: {
                            title: 'Pending Payable to Pool Member ',
                            subtitle: 'Weekwise'},
                        axes: {
                            x: {
                                0: {side: 'bottom', label: 'Week ID'} // Top x-axis.
                           }
                       }


                   };


                   var chart = new google.charts.Bar(document.getElementById('recvchart_div'));
                   // Convert the Classic options to Material options.
                   chart.draw(data, google.charts.Bar.convertOptions(options));
               }
               ///////////////////////start of PIE CHART//////////////



//                  google.charts.load("current", {packages:["corechart"]});

//                  google.charts.setOnLoadCallback(drawChart);

//                  function drawChart()

//                  {

//                    var data = google.visualization.arrayToDataTable([

//                      ['Task', 'Hours per Day'],

//                      ['Work',     11],

//                      ['Eat',      2],

//                      ['Commute',  2],

//                      ['Watch TV', 2],

//                      ['Sleep',    7]

//                    ]);

//

//                    var options = {

//                      title: 'My Daily Activities',

//                      is3D: true,

//                    };

//

//                    var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));

//                    chart.draw(data, options);

//                  }














           });
       </script>
       <!--        <script>
                   function blinker() {
                       $('.blinking1').fadeOut(2000);
                       $('.blinking1').fadeIn(1000);
                   }
                   setInterval(blinker, 1000);
               </script>-->
       <style>
           #customers {
               font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
               border-collapse: collapse;
               width: 100%;
           }


           #customers td, #customers th {
               border: 1px solid #ddd;
               padding: 2px;
           }


           #customers tr:nth-child(even){background-color: #f2f2f2;}




           #customers th {
               padding-top: 12px;
               padding-bottom: 12px;
               text-align: left;
               background-color: #7B8D8E;
               color: white;
               width:50%;
           }
       </style>
   </head>
   <body style="background-color:#FBF2EA;font-family: Aparajita;">
       <p align="right"><a href="<c:url value='welcomeSysadminChart.htm'> </c:url>" >Tabular View</a></p>
       <table align="center" style="width :90%;" class="roundedCorners">
               <tr>
                   <td style="width:400px;">
                       <table id="customers" align="center" style="width:100%;justify-content: center;">
                           <tr>
                               <th style="width:150px;">Bill Type</th>
                               <th style="width:150px;">Week ID</th>
                               <th style="width:150px;">Total payable</th>
                               <th style="width:150px;">Due Date</th>
                           </tr>
                       <c:forEach items="${latestPayableAllBills}" var="objectWeek">
                            <c:set var="count" value="${0}" />
                            <tr>
                               <c:forEach items="${objectWeek}" var="objectWeek1">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 4  }">
                                        <fmt:formatDate value="${objectWeek1}" pattern="dd-MM-yyyy" var="ToDate" />
                                        <td>${ToDate}</td>
                                    </c:if>
                                    <c:if test="${count ne 4}">
                                        <td>${objectWeek1}</td>
                                    </c:if>


                               </c:forEach>
                           </tr>
                       </c:forEach>
                       <tr>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                       </tr>
                   </table>


               </td>
               <td style="width:20px;">
               </td>
               <td style="width:400px;">
                   <table id="customers" align="center" style="width:100%;justify-content: center;">
                       <tr>
                           <th style="width:150px;">Bill Type</th>
                           <th style="width:150px;">Week ID</th>
                           <th style="width:150px;">Total Receivable</th>
                           <th style="width:150px;">Due Date</th>
                       </tr>
                       <c:forEach items="${latestReceivableAllBills}" var="objectWeek">
                            <c:set var="count" value="${0}" />
                            <tr>
                               <c:forEach items="${objectWeek}" var="objectWeek1">
                                    <c:set var="count" value="${count + 1}" />
                                    <c:if test="${count eq 4  }">
                                        <fmt:formatDate value="${objectWeek1}" pattern="dd-MM-yyyy" var="ToDate" />
                                        <td>${ToDate}</td>
                                    </c:if>
                                   <c:if test="${count ne 4}">
                                        <td>${objectWeek1}</td>
                                    </c:if>
                               </c:forEach>
                           </tr>
                       </c:forEach>
                       <tr>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                           <td>&nbsp;</td>
                       </tr>
                   </table>


               </td>
               </tr>
               <tr>
               <td>&nbsp;</td>
               <td>&nbsp;</td>
               <td>&nbsp;</td>
           </tr>
               <tr>
                   
                   <td style="width:400px;">
                        <fmt:formatDate value="${maxdate}" pattern="dd-MM-yyyy" var="newdatevar" />
                        <fmt:formatDate value="${maxToDate}" pattern="dd-MM-yyyy" var="ToDate" />
                       <table id="customers" style="width:100%;" align="center">
                        <tr>
                           <th style="width:150px;"></th>
                           <th style="width:150px;"></th>                          
                       </tr>
                       
                        <tr>
                            <td>Latest Bank Statement From Date</td><td><h4 align="center"  class="blinking1"> ${newdatevar}</h4></td>
                        </tr>
                        <tr>
                            <td>Latest Bank Statement To Date</td><td><h4 align="center"  class="blinking1">${ToDate}</h4></td>
                        </tr>
                        <tr>
                            <td>Latest Payment Disbursed Week</td><td><h4 align="center"  class="blinking1">${pdweekid}</h4></td>
                        </tr>
                        <tr>
                            <td>Main Pool Account balance</td><td><h4 align="center"  class="blinking1">${mainpool}</h4></td>
                        </tr>


                   </table>
                   </td>
                     <td style="width:20px;">
               </td>
                   <td style="width:400px;">
                       <table id="customers" width="100%">
                       <tr>
                           <th colspan="2">Interest Payable</th>
                           <th colspan="2">Interest Receivable</th>
                       </tr>
                       <tr>
                           <td>DSM  </td><td>${latestInterestpayabledsm}</td>
                           <td>DSM </td><td>${latestInterestDisbursedsm}</td>
                       </tr>
                       <tr>
                           <td>RRAS  </td><td>${latestInterestpayablerras}</td>
                           <td>RRAS  </td><td>${latestInterestDisburserras}</td>
                       </tr>
                       <tr>
                           <td>AGC </td><td>${latestInterestpayableagc}</td>
                           <td>AGC </td><td>${latestInterestDisburseagc}</td>
                       </tr>
                       <tr>
                           <td>FRAS  </td><td>${latestInterestpayablefras}</td>
                           <td>FRAS  </td><td>${latestInterestDisbursefras}</td>
                       </tr>
                   </table>

                   </td>
                   
               </tr>
           </tr>
       </table>
       
       <br>
       <table style="width:90%;" align="center">
           <tr>
               <td style="width:80%;">
                   <div align="center" style="height:300px;background:#FBF2EA;" id="paychart_div"></div>
               </td>
           </tr>
           <tr>
               <td>&nbsp;</td>
           </tr>
           <tr>
               <td>&nbsp;</td>
           </tr><tr>
               <td>&nbsp;</td>
           </tr>
           <tr>
               <td style="width:80%;">
                   <div align="center" style="height:300px;background:#FBF2EA;" id="recvchart_div"></div>
               </td>
           </tr>
       </table>

   </body>

</html>