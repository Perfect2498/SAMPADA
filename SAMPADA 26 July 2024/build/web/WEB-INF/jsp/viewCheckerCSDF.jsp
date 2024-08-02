<%--

    Document   : viewCheckerCSDF

    Created on : Dec 10, 2019, 2:44:29 PM

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

            $(document).ready(function () {


                var table = $('#disburseTable').DataTable({
                    responsive: true,
                    "bFilter": false,
                    "bPaginate": false,
                    "bInfo": false,
                    "buttons": [{
                            extend: 'collection',
                            text: 'Export',
                            buttons: ['excel',
                                'pdf'



                            ]

                        }

                    ],
                    "footerCallback": function (row, data, start, end, display) {
                        var api = this.api(), data;



                        // Remove the formatting to get integer data for summation

                        var intVal = function (i) {
                            return typeof i === 'string' ? i.replace(/[\$,]/g, '') * 1 : typeof i === 'number' ? i : 0;

                        };



                        // Total over all pages

                        $(api.column(0).footer()).html(
                                ' Total (Rs.)'

                                );



                        total = api

                                .column(5)

                                .data()

                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(5, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(5).footer()).html(
                                pageTotal

                                );





                        total = api

                                .column(4)

                                .data()

                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(4, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(4).footer()).html(
                                pageTotal

                                );



                        total = api

                                .column(6)

                                .data()

                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Total over this page

                        pageTotal = api

                                .column(6, {page: 'current'})

                                .data()

                                .reduce(function (a, b) {
                                    return intVal(a) + intVal(b);

                                }, 0);



                        // Update footer

                        $(api.column(6).footer()).html(
                                pageTotal

                                );







                    }

                });

             


            });

        </script>



        <script>





            function validate()

            {

                var rowcnt = document.getElementById("count").value;

                if (rowcnt == 0)

                {

                    return false;

                }

                else

                {

                    if (confirm("Are you want to Confirm !!!!!"))

                    {

                        return true;

                    }

                    else

                    {

                        return false;

                    }

                }



            }





            function validate1()

            {

                var rowcnt = document.getElementById("count").value;

                if (rowcnt == 0)

                {

                    return false;

                }

                else

                {

                    if (confirm("Are you want to Delete !!!!!"))

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

    <body width="80%">

        <form name="checkerForm">

            <p>&nbsp;</p>



            <h3 align="center" style="color:#003366;" >PSDF Details </h3>



            <table align="center" id="disburseTable" style="width:80%;" class="customerTable">

                <thead style="background-color: #0677A1;color: white;height:30px;">

                    <tr>

                        <th>Date</th>

                        <th>PSDF Amount</th>

                        <th>Pool Amount</th>

                        <th>Balance Amount</th>

                        <th>Month</th>

                        <th>Category</th>

                        <th>Remarks</th>



                    </tr>

                </thead>

                <tbody>

                    <%

                        int cnt = 0;

                        String category = "category";

                        String category1;

                    %>

                    <c:forEach items="${csdfList}" var="ele">

                        <%   cnt++;

                            category1 = category + cnt;

                        %>

                        <tr>
                            <fmt:formatDate value="${ele.entryDate}" pattern="dd-MM-yyyy" var="entryDate" />

                            <td>${entryDate}</td>

                            <td>${ele.csdfAmount}</td>

                            <td>${ele.mainPoolBalance}</td>

                            <td>${ele.mainPoolBalance-ele.csdfAmount}</td>

                            <td>${ele.csdfMonth}</td>

                            <td><input type="text" name="<%=category1%>" value="${ele.amtCategory}" hidden="yes"/>${ele.amtCategory}</td>

                            <td>${ele.remarks}</td>

                        </tr>



                    </c:forEach>

                </tbody>

            </table>



            <p><input type="text" name="count" id="count" value="<%=cnt%>" hidden="yes"/></p>

            <p align="center">

                <input style="width:100px;" class="btn" onclick="return validate();" type="submit" name="bconfirm"  value="Confirm"/>

                <input style="width:100px;" class="btn" onclick="return validate1();" type="submit" name="bcancel"  value="Delete"/>

            </p>

              <p>&nbsp;</p>  <p>&nbsp;</p>  <p>&nbsp;</p>

        </form>
            <p>&nbsp;</p>  <p>&nbsp;</p>

    </body>

</html>