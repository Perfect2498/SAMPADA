<%-- 
    Document   : aboutus
    Created on : Dec 27, 2019, 10:47:13 AM
    Author     : JaganMohan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
           <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" type="text/css" >             
        <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0" />
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>SAMPADA-WRLDC</title>
   
   
    </head>
     <body style="background-color: #FBF2EA;">
        <div style="margin-left: 15%;" id="aboutus" class="container-fluid" >
                <h3><span style='font-size:50px;'>&#9758;</span>&emsp;About Us</h3>
                  <div class="row">
                    <div class="col-sm-6">
                      <div style="alignment-adjust: left;font-size: 17px;">WRLDC is the apex body to ensure integrated operation of the power system in the Western Region.</div>
                      <br>
                      <p style="text-justify: center;font-size: 17px;">&emsp;The main responsibilities of WRLDC are:</p>
                         <ul style="font-size: 15px;">
                             <li>Monitoring of system parameters and security.</li>
                             <li>To ensure the integrated operation of the power system grid in the region.</li>
                             <li>System studies, planning and contingency analysis.</li>
                             <li>Analysis of tripping/disturbances and facilitating immediate remedial measures.</li>
                             <li>Daily scheduling and operational planning.</li>
                             <li>Facilitating bilateral and inter-regional exchanges.</li>
                             <li>Computation of energy despatch and drawal values using SEMs.</li>
                             <li>Augmentation of telemetry, computing and communication facilities in accordance with the Grid Standards and the State Grid Code.</li>
                        </ul>
                    </div>
                    <div class="col-sm-6">
                      <img src="${pageContext.request.contextPath}//images/cops-newlogo.jpg" width="100" height="100" alt="COPS">
                    </div>
                  </div>
            </div>
    </body>
</html>
