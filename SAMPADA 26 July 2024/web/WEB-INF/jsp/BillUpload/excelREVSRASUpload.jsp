<%-- 
    Document   : excelREVSRASUpload
    Created on : 18 Aug, 2023, 2:06:57 PM
    Author     : root
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

        <title>DSM Upload</title>
        <script>

            $(document).ready(function () {
                $(function () {
                    $("#weeklyDatePicker").datepicker({
                        showWeek: false,
                        firstDay: 1,
                        beforeShowDay: function (date) {
                            var day = date.getDay();
                            return [(day != 0), ''];
                        },
                        //maxDate: 'today',
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

                    var firstDate = moment(value, "YYYY-MM-DD").day(1).format("YYYY-MM-DD");

                    var lastDate = moment(value, "YYYY-MM-DD").day(7).format("YYYY-MM-DD");

                    $("#fromdate").val(firstDate);

                    $("#todate").val(lastDate);





                    jQuery(function () {



                        var weeknumber = moment(firstDate, "YYYYMMDD").isoWeek();

//                        alert('weeknumber week no:' + weeknumber);

                        var currentfiscalweekno = 0;

//                        var year1 = new Date().getFullYear();
                        var year1 = moment(firstDate, "YYYYMMDD").year();
                        var aprildateformat = year1 + '-04-01';

                        var aprilweeknumber = moment(aprildateformat, "YYYYMMDD").isoWeek();

//                        alert('aprilweeknumber week no:' + aprilweeknumber);

                        var currentfiscalweekno = weeknumber - aprilweeknumber;

                        //alert('present week no:'+currentfiscalweekno);







                        if (currentfiscalweekno >= 0)

                        {

                            // alert('current:'+currentfiscalweekno);

                            currentfiscalweekno = currentfiscalweekno + 1;



                        }

                        else

                        {
                            year1 = moment(lastDate, "YYYYMMDD").year();
                            year1 = year1 - 1;
                            aprildateformat = year1 + '-04-01';

                            aprilweeknumber = moment(aprildateformat, "YYYYMMDD").isoWeek();

                            var dec3week = year1 + '-12-24';

                            var dec4week = year1 + '-12-31';

                            var dec3weeknumber1 = moment(dec3week, "YYYYMMDD").isoWeek();

                            var dec4weeknumber1 = moment(dec4week, "YYYYMMDD").isoWeek();



                            if (dec4weeknumber1 < dec3weeknumber1)

                            {

                                currentfiscalweekno = dec3weeknumber1 - aprilweeknumber;

                                currentfiscalweekno = (+weeknumber) + (+currentfiscalweekno);

                                currentfiscalweekno = currentfiscalweekno + 1;

                                //alert('January:'+currentfiscalweekno);

                            }
                            else
                            {

                                currentfiscalweekno = dec4weeknumber1 - aprilweeknumber;
                                currentfiscalweekno = (+weeknumber) + (+currentfiscalweekno);

                                currentfiscalweekno = currentfiscalweekno + 1;
                            }





                        }




//                        alert('year1 :' + year1);




                        $("#yearfin").val(year1);
                        $("#weeklynumber").val(currentfiscalweekno);

                    });
                }

                //Get the value of Start and End of Week

                $('#getweek').click(function (e) {

                    getWeekNumber();

                    $('#billTypeId').val('');
                    $("#uploadbutton").show();

                });

//                $('#Checkbutton').click(function (e) {
                $('#billTypeId').change(function (e) {
                    //alert("Hey!! you have selected the billType!!")
                    var weeklynumber = $("#weeklynumber").val();
                    var weeklyDatePicker = $("#yearfin").val();
                    var billType = "SRAS";
                    //alert("weeklynumber " + weeklynumber);
                    //alert("weeklyDatePicker " + weeklyDatePicker);

                    $.ajax({
                        url: basePath + "/AvailableBills",
                        data: {
                            "weekId": weeklynumber,
                            "year": weeklyDatePicker,
                            "billType": billType
                        },
                        type: "POST",
                        //dataType: "json",
                        success: function (data) {
                            var dataObject = jQuery.parseJSON(data);
                            // alert("Success");

                            $("#availBillTable tr").remove();
                            $("#alert").empty();
                            $('#availBillTable').attr('border', '1');
                            $("#availBillTable tbody").append("<tr><th>Week No.</th><th>Revision No.</th><th>Bill Year</th><th>Bill Issue Date</th><th>Bill Due date</th><th>Bill Upload Date</th></tr>");

                            for (var i = 0; i < dataObject.availBillInfo.length; i++) {

                                $("#availBillTable tbody").append("<tr><td>" + dataObject.availBillInfo[i][0] + "</td><td>" + dataObject.availBillInfo[i][1] + "</td><td>" + dataObject.availBillInfo[i][2] + "</td><td>" + dataObject.availBillInfo[i][3] + "</td><td>" + dataObject.availBillInfo[i][4] + "</td><td>" + dataObject.availBillInfo[i][5] + "</td></tr>");
                            }

                            $('#availBillTable td').each(function () {

                                $(this).css("text-align", "center");    // css attribute of your <td> width:15px; i.e.
                            });

                            $("#alert").append(dataObject.msg);
                            if (dataObject.msg.trim() == '') {

                            }
                            else {
                                $("#uploadbutton").hide();
                            }


                        },
                        error: function () {
                            console.log('error');
                        }
                    });

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
            td{
                color:#003366;
            }

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
                ifrm.style.height = getDocHeight(doc) + 1 + "px";
                ifrm.style.visibility = 'visible';

            }

            function resizeIframe(obj) {
                obj.style.height = obj.contentWindow.document.body.scrollHeight + 10 + 'px';

            }
        </script>
        <style>
            legend {
                display: block;
                color:#003366;

            }
            fieldset
            {
                /*background-color:#fff7c4;*/
                border: 3px solid #964000;
                max-width:900px;
                padding:16px;
                border-radius: 25px;
                margin-left: 25%;

            }
        </style>
    </head>
    <body>


        <!--        <form id="form1" method="GET" enctype="multipart/form-data">-->
        <p style="color:red;"><c:out value="${msg}"></c:out></p><br/>

            <fieldset  style="width:90%;margin-left: 10%;">

                <legend style="font-size: 16px; color:#003366;"><b>Upload SRAS Sheet</b></legend>
                <p ><h2 style="color:red;">Use this module only if previous revised bill data is not available in Data Base</h2></p>


            <div>
                <table width="100%" border="0" margin="0" padding="0" height="auto"
                       border id="availBillTable" style="color: #003366;">
                    <tbody>
                    </tbody>
                </table>
            </div>

            <div>
                <p  id="alert" style="color: red;"></p>
            </div>
            <table align="center" style="width: 100%;">
                <a  href="downloadREVSRASTemplate.htm">Download REVISED SRAS Template</a>
                <div class="noborderedtable" style="color: #003366;" align="center"><br>

                <form:form action="uploadREVSRAS.htm" id="form1" method="post" enctype="multipart/form-data" onsubmit="return formOK;">
                    <div id="weekNo"></div>
                    <br/>
                    <div class="container">

                        <div class="row">

                            <div class="col-sm-6 form-group">

                                <div class="input-group" id="DateDemo">

                                    Select the Date :<span style='font-size:20px;'>&#128197;</span>  <input type='text' id='weeklyDatePicker' placeholder="Select Date" autocomplete="off" required />
                                    <input type="submit" id="getweek" value="Get"  name="bName" align="center" style="font-size:14px;"/>
                                </div>
                            </div>
                        </div>
                        <br/>
                        <div class="row">
                            <div class="col-sm-6 form-group">
                                <div class="input-group" id="DateDemo">
                                    Week No:  <input type='text' id='weeklynumber' name="weeklynumber" readonly/>
                                    <input type='text' id='yearfin' name="yearfin" value="${yearfin}" hidden/>
                                </div>
                            </div>
                        </div>

                        <br/>

                        <div class="row">

                            <div class="col-sm-6 form-group">

                                <div class="input-group" id="DateDemo">

                                    From Date:  <input type='text' id='fromdate' name="fromdate" readonly />

                                </div>

                            </div>

                        </div>

                        <br>

                        <div class="row">

                            <div class="col-sm-6 form-group">

                                <div class="input-group" id="DateDemo">

                                    To Date:  <input type='text' id='todate' name="todate" readonly /><br>

                                </div>

                            </div>

                        </div>

                    </div><br/>

                    <!--<input type="button" value="Check" name="Check" id="Checkbutton" align="center" style="font-size:12px;">-->


                    <div> Check: <select name="billType" id="billTypeId" required>
                            <option value="">--Select--</option>
                            <option value="Original">Existing bills </option>
                            <!--  <option value="Revision">Revision </option>-->

                        </select><br/>

                    </div>

                    <div style="text-align:center">
                        <br>
                        <input type="file" name="file" id="uploadexcelfile" required><br><br>
                        <input type="submit" value="&#128228;Upload" name="upload" id="uploadbutton" align="center" style="font-size:14px;">
                    </div>
                </form:form>

            </div>
        </table>
        <div>
            <p style="text-align:center"> Note: For a particular week, original and revision bills upload provision is given. </p>
        </div>

    </fieldset>
    <br/><br/><br/>
    <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
    <script src="<c:url value="/js/lib/jquery-1.10.2.min.js" />" type="text/javascript" ></script>
    <script src="<c:url value="/js/lib/jquery-ui-1.11.0.min.js" />" type="text/javascript" ></script>
    <script src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('input[type="submit"]').button();
            var formOK = true;
            $('#uploadexcelfile').bind('change', function () {
                var file = this.value;
                var len = file.length;
                var ext = file.slice(len - 4, len);
                var size = this.files[0].size;
                var filename = $('input[type=file]').val().split('\\').pop();
                //var filename = file.substr(0, file.lastIndexOf('.'));
                var name = filename.split('.')[0];
                //  console.log(filename,$('#file'));
                console.log(name);
                console.log(ext);
                //   if(((ext.toUpperCase() == ".PDF")||(ext.toUpperCase() == ".JPEG")||(ext.toUpperCase() == ".JPG"))&&(size<=500000)){
                //                    if ((ext == "xlsx") && (name == "agc_excel_file")) {
                //                        formOK = true;
                //                    }
                if ((ext == "xlsx")) {
                    formOK = true;
                }
                else {
                    formOK = false;
                    alert("Please upload agc_excel_file");
                    $("#uploadexcelfile").val('');
                }
                return formOK;
            });
        });


    </script>
</form>
<br/>
<br/>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
<br/>
</body>

</html>



