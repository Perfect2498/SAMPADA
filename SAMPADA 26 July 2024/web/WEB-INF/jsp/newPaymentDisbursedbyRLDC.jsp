<%--

    Document   : newPaymentDisbursedbyRLDC

    Created on : Dec 4, 2019, 6:05:29 PM

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

                iframe.style.height = container.offsetHeight + 250 + 'px';

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



    </head>



    <body style="background-color: #FBF2EA;" >

        <h3 id="idheader" align="center"> Payment Disbursed Reports</h3><br>
        <form name="salesreportform" method="post">

            <div class="noborderedtable" align="center">

                <table id="reportstable">

                    <tr>

                        <td>Select Type</td>

                        <td><select name="reporttype">
                                <option>--Select--</option>
                                <option>Bills</option>

                                <option>Interest</option>

                                <option>Refund</option>

                                <option>PSDF</option>
                                 <option>All</option>

                            </select></td>

                    </tr>
                    <tr>

                        <td></td>

                        <td></td>

                    </tr>

                    <tr><td>Start Date</td>

                        <td><input type="text" name="startdate" id="datepicker" required autocomplete="off"/></td>

                    </tr>

                    <tr>

                        <td></td>

                        <td></td>

                    </tr>

                    <tr><td>End Date</td>

                        <td><input type="text" name="enddate" id="datepicker1" required autocomplete="off"/></td>

                    </tr>

                    <tr>

                        <td></td>

                        <td></td>

                    </tr>





                    <tr>

                        <td></td>

                        <td></td>

                    </tr>

                    <tr>

                        <td></td>

                        <td></td>

                    </tr>

                    <tr>

                        <td></td>

                        <td></td>

                    </tr>

                    <tr>

                        <td></td>

                        <td></td>

                    </tr>

                    <tr><td>&nbsp;</td>

                        <td><input type="submit"  name="bName" class="btn" value="Submit" style="font-size:12px;"/></td>

                    </tr>

                </table>

                <br>
                <p>&nbsp;</p>
                    
                <p>&nbsp;</p>
                <p>&nbsp;</p><p>&nbsp;</p>
                 <p>&nbsp;</p>
                <p>&nbsp;</p><p>&nbsp;</p>
                 <p>&nbsp;</p>
                <p>&nbsp;</p><p>&nbsp;</p>


            </div>

        </form>

    </body>



</html>