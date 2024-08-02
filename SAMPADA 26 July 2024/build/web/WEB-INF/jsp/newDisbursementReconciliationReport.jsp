<%--



    Document   : newDisbursementReconciliationReport



    Created on : Dec 4, 2019, 4:52:00 PM



    Author     : JaganMohan



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



        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />



        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>



        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>



        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>



        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />



        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />



        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />



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



        <script>



            $(document).ready(function () {



                $("#datepicker").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: 'yy/mm/dd'



                });



                $("#datepicker1").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    dateFormat: 'yy/mm/dd'
                });
            });



        </script>



        <style>
            div.ui-datepicker{



                font-size:14px;
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
                margin-left: 25%;

            }
        </style>

    </head>
    <body align="center" style="color:#003366;">

        <!--<h3 id="idheader" align="center" > Reconciliation of Payment Disburse with Bank Statement Reports</h3><br>-->

        <fieldset id="fieldset2" style="width: 60%; " >

            <legend style="font-size: 16px;"><b> Reconciliation of Payment Disburse with Bank Statement Reports</b></legend>

            <form name="salesreportform" align="center">

                <div class="noborderedtable" align="center">



                    <table id="reportstable">

                        <tr><td>Bank Amount-DR From Date</td>

                            <td><input type="text" name="startdate" id="datepicker" required autocomplete="off"/></td>

                        </tr>



                        <tr>

                            <td></td>

                            <td></td>

                        </tr>

                        <tr><td>Bank Amount-DR To Date</td>

                            <td><input type="text" name="enddate" id="datepicker1" required autocomplete="off"/></td>

                        </tr>

                        <tr>

                            <td></td>

                            <td></td>

                        </tr>
                        <tr>

                            <td>Select Type</td>
                            <td><select name="reporttype">
                                    <option>Bills</option>                               
                                    <option>Refund</option>
                                </select></td>

                        </tr>

                        <tr>

                            <td></td>

                            <td></td>

                        </tr>

                        <tr><td></td>

                            <td><input type="submit" class="btn" name="bName" value="Submit" style="font-size:12px;"/></td>

                        </tr>

                    </table>

                </div>

                <p>&nbsp;</p>

                <p>&nbsp;</p>

                <p>&nbsp;</p>

            </form>
        </fieldset>
        <p>&nbsp;</p>

        <p>&nbsp;</p>

        <p>&nbsp;</p>

    </body>
</html>