<%--
    Document   : viewInterestMappingPayableCorporateList
    Created on : Nov 21, 2019, 3:32:45 PM
    Author     : cdac
--%>





<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>



<html>

    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery.dataTables.min.js" />" type="text/javascript"></script>
        <script type="text/javascript" language="javascript" src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
        <script src="<c:url value="/js/jszip.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/pdfmake.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/vfs_fonts.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/buttons.html5.min.js" />" type="text/javascript"></script>

        <style>
            table { margin: auto;  }

            input[type=submit] {

                padding: 10px 14px;
                border: 2px solid #f2f2f2;
                font-weight:bold;
                color:#ffffff;
                alignment-adjust:central;
                background-color:#964000;
            }
            input[type=submit]:hover, input[type=submit]:focus {
                background-color: #151b54;
            }
            td{
                color:#003366;
            }

        </style>

        <style>
            legend {
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
                margin-left: 32%;

            }
        </style>

        <script>





            function validate()

            {

                var corp = document.getElementById("corparateID").value;

                if (corp == -1) {

                    alert('Please select the corpoarte ..');

                    return false;

                }

                return true;

            }

        </script>
        <script>
            $(document).ready(function () {

                var table = $('#pendingslist').DataTable({
                    responsive: true,
                    "pageLength": 25,
                    "order": [[0, "asc"]],
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'collection',
                            text: 'Export',
                            buttons: [
                                'excel',
                                'pdf'

                            ]
                        }
                    ]
                });

                table.columns().iterator('column', function (ctx, idx) {
                    $(table.column(idx).header()).append('<span class="sort-icon"/>');
                });

                $('#pendingslist tbody')
                        .on('mouseenter', 'td', function () {
                            var colIdx = table.cell(this).index().column;

                            $(table.cells().nodes()).removeClass('highlight');
                            $(table.column(colIdx).nodes()).addClass('highlight');
                        });

            });
        </script>
    </head>

    <body>
        <table id="pendingslist" align="center" style="width:36%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th>Mapped Interest Entries of Pool Members at Checker End</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pendingAtCheckercorps}" var="pflow">
                    <tr align="center">

                        <td>${pflow}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <br>
        <table id="pendingslistwithnobnkstmt" align="center" style="width:36%;" class="customerTable">
            <thead style="background-color: #0677A1;color: white;height:30px;">
                <tr>
                    <th colspan="3">List of Pending Pool Member without bank balance available for Mapping</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="count" scope="application" value="${0}"/> 

                <c:forEach items="${listcorpbynobnkstmt}" var="ele1">
                    <c:if test="${count == 0}">

                        <tr>                                
                        </c:if>
                        <td>${ele1}</td>

                        <c:if test="${count == 2}">

                        </tr>
                        <c:set var="count"  value="${count-3}"/>
                    </c:if>
                    <c:set var="count"  value="${count+1}"/>



                </c:forEach>

            </tbody>
        </table>
        <br>
        <br>
        <form>
            <fieldset>
                <legend> <h3>Pool Member Available for Mapping of Interest with Bank Balance</h3></legend>

                <table align="center">
                    <tr><td>Select the Pool Member:</td>
                        <td><select id="corparateID" name="corparateID" >
                                <option value="-1">select</option>
                                <c:forEach items="${interestCorpList}" var="ele">

                                    <option value="${ele}">${ele}</option>

                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>

                </table>


                <input type="submit" class="btn" value="Submit"  id="Submit" name="bname" style="margin-left:45%;" onclick="return validate();"/>
            </fieldset>



            <!--            <h4 align="center">List of Pool Member for Interest - Payable</h4>
            
                        <p align="center">
            
                            <select id="corparateID" name="corparateID" >
            
                                <option value="-1">select</option>
            
            <%--<c:forEach items="${interestCorpList}" var="ele">--%>

                <option value="${ele.corporateId}">${ele.corporateName}</option>

            <%--</c:forEach>--%>

        </select>

        <input class="btn" type="submit" name="bname" value="Submit" onclick="return validate();" />

    </p>-->
            <p style="color:#003366;text-align:center;"> Note: Kindly ask Checker for previous Interest Pending to clear from his/her end, if any.</p>

        </form>

        <p>&nbsp;</p>

        <p>&nbsp;</p>

        <p>&nbsp;</p>
        <br><br><br>
        <br><br><br>
        <br><br><br>
        <br><br>
    </body>



</html>