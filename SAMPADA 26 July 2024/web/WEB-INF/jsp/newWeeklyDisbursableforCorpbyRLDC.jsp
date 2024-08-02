<%-- 
    Document   : newWeeklyDisbursableforCorpbyRLDC
    Created on : Jul 5, 2019, 4:51:43 PM
    Author     : JaganMohan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
        <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
        <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
        <link href="<c:url value="/css/cupertino/jquery-ui.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/ajaxDefaults.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/css/mystyle.css" />" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/2.10.6/moment.min.js"></script>
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';
        </script>


   <script>
            $(document).ready(function(){
                        
             $(function() {
                $("#weeklyDatePicker").datepicker({
                  showWeek: true,
                  firstDay: 0,
                  maxDate: 'today',
                  dateFormat: 'yy-mm-dd',
                  beforeShow: function(elem, ui) {
                    $(ui.dpDiv).on('click', 'tbody .ui-datepicker-week-col', function() {
                      $(elem).val('Week ' + $(this).text()).datepicker( "hide" );
                    });
                  },               
                  
                });
              });

function getWeekNumber()
{
            var value = $("#weeklyDatePicker").val();             
            var firstDate = moment(value, "YYYY-MM-DD").day(0).format("YYYY-MM-DD");
            var lastDate =  moment(value, "YYYY-MM-DD").day(6).format("YYYY-MM-DD"); 
             $("#fromdate").val(firstDate);
             $("#todate").val(lastDate);
             
             Date.prototype.getWeek = function() {
            var onejan = new Date(this.getFullYear(),0,1);
            var today = new Date(this.getFullYear(),this.getMonth(),this.getDate());
            var dayOfYear = ((today - onejan +1)/86400000);
            return Math.ceil(dayOfYear/7)
             };

            jQuery(function(){  
                var today = new Date(firstDate);
                var weekno = today.getWeek();               
                $("#weeklynumber").val(weekno);
            });
             
    
}
         //Get the value of Start and End of Week
        $('#getweek').click(function (e) {            
               getWeekNumber();             
        });
        
});
        </script>
        
        <style>
            table { margin: auto; } 

            input[type=submit] {

                padding: 10px 14px;
                border: 2px solid #964000;
                font-weight:bold;
                color:#000;
                alignment-adjust:central;
                background-color: #964000;
            }

            input[type=submit]:hover, input[type=submit]:focus {
                background-color: #964000;
                color:white;
            }
            td,div{
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
                margin-left: 25%;

            }
        </style>
</head>
<body>
    <br/>
    <form name="weeklyform">
        
      <fieldset style="min-height: 400px;">

                <legend style="font-size: 16px;"><b>Weekly Disbursable for Pool Members</b></legend>
    <div id="weekNo"></div>      
         
    <div class="container" align="center">    
            <div class="row">
                <div class="col-sm-6 form-group">
                    <div class="input-group" id="DateDemo">
                    Select the Week :  <input type='text' id='weeklyDatePicker' placeholder="Select Date" required/>
                    <p id="getweek" style="width:60px;height:30px;background-color: #964000;font-size:14px;color:white;" required>Get</p>
                   <!--<input type="submit" id="getweek" value="Get"  name="bName" align="center" style="font-size:14px;color:white;"/>-->

                    </div>
              </div>
            </div>
        <br>
            <div class="row">
                <div class="col-sm-6 form-group">
                    <div class="input-group" id="DateDemo">
                    Week No:  <input type='text' id='weeklynumber' name="weeklynumber" required/>
                  </div>
              </div>
            </div>
        <br>
            <div class="row">
                <div class="col-sm-6 form-group">
                    <div class="input-group" id="DateDemo">
                    From Date:  <input type='text' id='fromdate' name="fromdate" required/>
                </div>
              </div>
            </div>
        <br>
            <div class="row">
                <div class="col-sm-6 form-group">
                    <div class="input-group" id="DateDemo">
                    To Date:  <input type='text' id='todate' name="todate" required/>
                  </div>
                </div>
            </div>
         <div class="row">
                <div class="col-sm-6 form-group">
                    <div class="input-group" id="DateDemo">
                        Bill Type  <select name="billtype" required>
                            <option>DSM</option>
                            <option>RRAS</option>
                            <option>AGC</option>
                            <option>ALL</option>
                        </select>
                  </div>
              </div>
            </div>
        <br>
</div>
    <p align="center"><input type="submit" name="bname" value="Submit" /></p>
    </fieldset>
</form>
    <br/>
    
</body>
</html>
