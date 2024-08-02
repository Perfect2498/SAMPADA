<%--
    Document   : billProcessingPendingPayableList
    Created on : Jul 10, 2019, 8:33:46 AM
    Author     : JaganMohan
--%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

        <link href="https://fonts.googleapis.com/css?family=Kaushan+Script|Lobster|Rock+Salt|Satisfy&display=swap" rel="stylesheet">

        <style>
            table, td, th {
                border: 1px solid #ddd;
                text-align: left;
            }

            table {
                border-collapse: collapse;
                width: 100%;
            }
            th{
                font-family:'Kaushan Script', cursive;
                font-size: 18px;
            }
            td {
                font-size: 18px;
            }
            th, td {
                padding: 7px;
                width: 152px;
            }

            input{
                width:50px;
            }
            select{
                width:80px;
            }

        </style>
        <style>
            table.roundedCorners {
                border: 2px solid DarkOrange;
                border-radius: 13px;
                border-spacing: 0;
            }
            table.roundedCorners th {
                border: 2px solid  #964000;
                padding: 10px;
                background-color: #964000;
                color:white;

            }
            table.roundedCorners td
            {
                border: 2px solid DarkOrange;
                padding: 10px;
                background-color: #FBF2EA;

            }
            thead,tr,td {
                color:#003366;
            }
            p {
                color:black;
            }
            .btn{
                background-color:DarkOrange;
                color:#603311;
            }
            table.dataTable thead span.sort-icon {
                display: inline-block;
                padding-left: 5px;
                width: 16px;
                height: 16px;
                color:wheat;
            }

            td.highlight {
                background-color: whitesmoke !important;
            }

            table.dataTable thead .sorting span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_both.png') no-repeat center right; }
            table.dataTable thead .sorting_asc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc.png') no-repeat center right; }
            table.dataTable thead .sorting_desc span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc.png') no-repeat center right; }

            table.dataTable thead .sorting_asc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_asc_disabled.png') no-repeat center right; }
            table.dataTable thead .sorting_desc_disabled span { background: url('http://cdn.datatables.net/plug-ins/3cfcc339e89/integration/bootstrap/images/sort_desc_disabled.png') no-repeat center right; }



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
        <script>
            $(document).ready(function () {

                var table = $('#payableTable').DataTable({
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

                $('#payableTable tbody')
                        .on('mouseenter', 'td', function () {
                            var colIdx = table.cell(this).index().column;

                            $(table.cells().nodes()).removeClass('highlight');
                            $(table.column(colIdx).nodes()).addClass('highlight');
                        });

            });
        </script>


    </head>
    <body onload="setIframeHeight(this.id);" align="center" style="width:95%;" >
        <h3 align="center" style="color:#003366;">From Week-id ${week_id1} to Week-id ${week_id2} of year ${yeari}</h3>
        <!--<table id="payableTable" align="center" style="min-height:400px;width:80%;" class="table table-striped table-bordered">-->
        <form>
            <fieldset>
                <legend> <h3>Inter-Region Pool Member Receivable ${type}-Report of FINAL BILLS After revisions</h3></legend>

                <table id="payableTable" align="center" style="min-height:400px;width:81%;" class="roundedCorners">
                    <thead style="background-color: #0677A1;color: white;height:30px;">
                        <tr>
                            <th>Pool Member</th>
                            <!--                        <th>Entity Short Name</th>-->
                            <!--<th>CORPORATE_ID </th>-->
                            <th>TOTAL_NET AMOUNT</th>
                            <th>   REVISED REFUND by Corp</th>
                            <th>   REVISED RECEIVABLE</th>
                            <th>   DISBURSE AMOUNT</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${queryList1}" var="list">
                            <tr>

                                <c:forEach items="${list}" var="object">
                                    <td>${object.toString()}</td>
                                </c:forEach>

                            </tr>
                        </c:forEach>
                    <p>&nbsp;</p>
                    <p>&nbsp;</p>
                    </tbody>
                </table>
            </fieldset>
        </form>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </body>
</html>

