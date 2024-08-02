<%-- 
    Document   : successPage
    Created on : Aug 30, 2018, 4:53:18 PM
    Author     : cdac
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            
             .flash {
                    animation-name: flash;
                    animation-duration: 0.9s;
                    animation-timing-function: linear;
                    animation-iteration-count: infinite;
                    animation-direction: alternate;
                    animation-play-state: running;
                }

/*            @keyframes flash {
                from {color: #4682B4;}
                to {color: #B0E0E6;}
            }*/

            @keyframes flash {
                from {color: DarkGreen;}
                to {color: GreenYellow;}
            }
            
        </style>
       


    </head>
    <body style="min-height: 800px;">
        <h3 class="flash" style="text-align:center;">${msg}</h3><br><br>
    </body><br><br>
</html>