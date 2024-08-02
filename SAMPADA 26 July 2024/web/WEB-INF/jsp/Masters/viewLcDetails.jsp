<%-- 
    Document   : viewLcDetails
    Created on : Aug 29, 2019, 8:36:40 AM
    Author     : shubham

 

--%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Letter Of Credit Details</title>

        <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
        <script src="<c:url value="/js/jquery.min.js"/>"></script>
        <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/buttons.dataTables.min.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/mystyle.css"/>">

        <script type="text/javascript" language="javascript" src="<c:url value="/js/jquery-1.12.4.js"/> ">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/jquery.dataTables.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/dataTables.buttons.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/buttons.flash.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/jszip.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/pdfmake.min.js" />">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/vfs_fonts.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/buttons.html5.min.js"/>">
        </script>
        <script type="text/javascript" language="javascript" src="<c:url value="/js/buttons.print.min.js"/>">
        </script>
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
                // $('#utilityDatatable').DataTable();

                $('#utilityDatatable').DataTable({
                    "processing": true,
                    "dom": 'lBfrtip',
                    "buttons": [
                        {
                            extend: 'collection',
                            text: 'Export',
                            // messageTop:'table',

                            buttons: [
                                {
                                    extend: 'excel',
                                    exportOptions: {
                                        columns: [0, 1, 2, 3, 4, 5]
                                    }
                                    // messageTop:"Month:"+month+" and year:"+year
                                },
                                //  'pdf'
                                {
                                    extend: 'pdf',
                                    exportOptions: {
                                        columns: [0, 1, 2, 3, 4, 5]
                                    }
                                    // messageTop:"Month:"+month+" and year:"+year
                                }
                            ]
                        }
                    ]
                });




            });
        </script>
 

    </head>
    <body style="background-color:#FBF2EA;">
        <br><br>
   <p align="center" style="font-size: 16px;color:#003366;"><b>Letter Of Credit Details</b></p>
        <form name="utilListform">

            <table id="utilityDatatable"  align="center" width="90%" border="yes" class="customerTable">
                <thead class="trth" style="min-height: 15px;height: 30px;">
                    <tr>

                        <th>Sl. No</th> 
                        <th>Constituent</th>

                        <th>Last FY Avrg</th> 

                        <th>LC Amount</th>
                        <th>Financial Year</th>
                        <th>LC Start Date</th>


                        <th>LC expiry Date</th>
                        <th>Outdated</th>
                        <th width="100"></th>
                       
                        <!--                <th width="100"></th>-->

                    </tr>
                </thead>
                <tbody>
                    <c:set var="serialno" value="${0}"/>  
                    <c:forEach items="${lclist}" var="util">

                        <tr>
                            <c:set var="serialno" value="${serialno + 1}" />
                            <td style="text-align:left"><c:out value="${serialno}"/></td>
                            <td style="text-align:left"><c:out value="${util.getConstituent()}"/></td>
                            <td style="text-align:left"><c:out value="${util.getLastFyAvg()}"/></td>

                            <td style="text-align:left;"><c:out value="${util.getLcAmount()}"/></td> 
                             <td style="text-align:left;"><c:out value="${util.getFinYear()}"/></td> 
                            <td style="text-align:left;"><c:out value="${util.getLcFromdate()}"/></td> 
                            <td style="text-align:left;"><c:out value="${util.getLcTodate()}"/></td> 

                         
                   
                     <c:if test="${(util.getExpFlag()==1)}"> 

                           <td>Yes</td> 
                      
                             </c:if> 
                              <c:if test="${(util.getExpFlag()==0)}"> 
                                   <td>No</td> 
                              </c:if> 
             
                 
                            <td><a name="bname" href="<c:url value='viewLcDetails.htm'> <c:param name="lcId" value="${util.lcId}"/> </c:url>" class="btn btn-info custom-width">View</a></td>

                            </tr>
                    </c:forEach>




                </tbody>
            </table>

        </form>
                    <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
    </body>
</html>