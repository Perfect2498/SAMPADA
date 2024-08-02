<%-- 
    Document   : selectWeekDates
    Created on : Jun 26, 2019, 2:18:01 PM
    Author     : shubham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

    <head>

        <meta http-equiv="content-type" content="text/html; charset=UTF-8">

        <title>Week Number</title>



        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

        <link rel="stylesheet" href="/resources/demos/style.css">

        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>

        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

        <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/2.10.6/moment.min.js"></script>





        <script>

            $(document).ready(function () {



                $(function () {

                    $("#weeklyDatePicker").datepicker({
                        showWeek: true,
                        firstDay: 0,
                        maxDate: 'today',
                        dateFormat: 'yy-mm-dd',
                        beforeShow: function (elem, ui) {

                            $(ui.dpDiv).on('click', 'tbody .ui-datepicker-week-col', function () {

                                $(elem).val('Week ' + $(this).text()).datepicker("hide");

                            });

                        },
                    });

                });



                function getWeekNumber()

                {

                    var value = $("#weeklyDatePicker").val();

                    var firstDate = moment(value, "YYYY-MM-DD").day(0).format("YYYY-MM-DD");

                    var lastDate = moment(value, "YYYY-MM-DD").day(6).format("YYYY-MM-DD");

                    $("#fromdate").val(firstDate);

                    $("#todate").val(lastDate);



                    Date.prototype.getWeek = function () {

                        var onejan = new Date(this.getFullYear(), 0, 1);

                        var today = new Date(this.getFullYear(), this.getMonth(), this.getDate());

                        var dayOfYear = ((today - onejan + 1) / 86400000);

                        return Math.ceil(dayOfYear / 7)

                    };



                    jQuery(function () {

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

    </head>

    <body>

        <div id="weekNo"></div>



        <br> 



        <div class="container">   

            <div class="row">

                <div class="col-sm-6 form-group">

                    <div class="input-group" id="DateDemo">

                        Select the Week :  <input type='text' id='weeklyDatePicker' placeholder="Select Date" />

                        <p id="getweek" style="width:60px;height:30px;background-color: green;">Get</p>

                    </div>

                </div>

            </div>

            <br>

            <div class="row">

                <div class="col-sm-6 form-group">

                    <div class="input-group" id="DateDemo">

                        Week No:  <input type='text' id='weeklynumber' />

                    </div>

                </div>

            </div>

            <br>

            <div class="row">

                <div class="col-sm-6 form-group">

                    <div class="input-group" id="DateDemo">

                        From Date:  <input type='text' id='fromdate'  />

                    </div>

                </div>

            </div>

            <br>

            <div class="row">

                <div class="col-sm-6 form-group">

                    <div class="input-group" id="DateDemo">

                        To Date:  <input type='text' id='todate'  />

                    </div>

                </div>

            </div>

        </div>





    </body>

</html>