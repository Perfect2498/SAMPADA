<%-- 
    Document   : dsnfileview
    Created on : 18 Mar, 2020, 3:46:12 PM
    Author     : abt
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
    <head>
        <title>Pool Member Details</title>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <script src="<c:url value="/js/jquery-3.3.1.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.4.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-1.12.0.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/js/jquery-ui.js" />" type="text/javascript"></script>
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
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

                $('#erroMsg').show();

                var formOK = true;
                //            $('#docname').bind('change', function() {
                //              var file = this.value;
                //              var len = file.length;
                //              var ext = file.slice(len - 4, len);
                //              var size=this.files[0].size;
                //
                //               if(((ext.toUpperCase() == ".PDF")||(ext.toUpperCase() == ".JPEG")||(ext.toUpperCase() == ".JPG"))&&(size<=1024000)   && (size >0) ){
                //                        formOK = true;
                //                    }
                //                    else{
                //                        formOK = false;
                //                        alert("PDF ,JPEG or jpg Files with size less than or equal to 1MB is allowed");
                //                        $("#panfile").val('');
                //                    }
                //                    return formOK;
                //                });
                $('#docname').bind('change', function () {
                    var file = this.value;
                    var len = file.length;
                    //var ext = file.slice(len - 4, len);
                    var selected_Extension = document.getElementById("fileExt").value;
                    var ext = file.substr(file.lastIndexOf('.') + 1);
                    // alert(ext);
                    // alert("selected extension"+selected_Extension);
                    var size = this.files[0].size;

                    if ((ext.toLowerCase() === selected_Extension) && (size <= 5120000) && (size > 0)) {
                        formOK = true;
                    }
                    else {
                        formOK = false;
                        alert("Please Upload the files with the selected extension and size less than or equal to 5MB");
                        $("#docname").val('');
                    }
                    return formOK;
                });
            });

        </script>

    </head>
    <body style="min-height: 400px;">

        <br>
        <form name="docuploadform" method="post" enctype="multipart/form-data" >
            <legend><h3> Upload Documents for the Unique ID : ${stmtId}</h3></legend>
            <input type="text" name="stmtId" value="${stmtId}" hidden="yes"/>
            <table align="center" width="60%" class="customerTable">


                <tr><td>Select the File Extension </td>
                    <td><select name="fileExt" id="fileExt" selected="">
                            <option>pdf</option>
                            <option>jpeg</option>   
                            <option>xls</option> 
                            <option>xlsx</option> 
                            <option>jpg</option> 
                            <option>zip</option>   
                        </select>
                    </td>
                </tr>                   

                <tr><td>Enter the file name:</td>
                    <td><input type="text" name="usrfilename" maxlength="20" required/></td>
                </tr>

                <tr><td>Upload the File:</td>
                    <td><input type="file" name="docname" id="docname" required/></td></tr>
                <tr><td>Remarks:</td>
                    <td><input type="text" name="remarks" maxlength="500" required/></td>
                </tr>
                <tr><td colspan="2"><input type="submit" name="submitBtn" value="Save" style="alignment-adjust: central"/></td></tr>


            </table>               
            <br>               
            <table class="customerTable" style="width: 100%;">            
                <tr>
                    <th>Pool Member</th>
                    
                    <th>File Name</th>
                    <th>Upload Time</th>  
                    <th>Remarks</th>  
                </tr>
                <c:forEach items="${dsnfileslist}" var="elem">                        
                    <tr>
                        <td>${elem.bankStatement.corporates.corporateName}</td>
                        
                        <td>
                            <a href="<c:url value='viewDocument.htm'> 
                                   <c:param name="savedFilename" value="${elem.savedFileName}"/><c:param name="ext" value="${elem.fileType}"/> </c:url>" >${elem.fileName}</a>
                            </td>                           
                            <td>${elem.entryTime}</td> 
                             <td>${elem.remarks}</td> 

                    </tr>    

                </c:forEach>
            </table>
            <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
            <br>
            <br>
        </form>
            <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
    </body>
    <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
</html>

