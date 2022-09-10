function showLoader() {
    var linkMonthInc = document.getElementById('monthlyIncLinkId');
    linkMonthInc.setAttribute("style","pointer-events: none");
    var linkEmpInc = document.getElementById('empIncLinkId');
    linkEmpInc.setAttribute("style","pointer-events: none");
    var linkEmpIncDetail = document.getElementById('monthlyIncAttendanceLinkId');
    linkEmpIncDetail.setAttribute("style","pointer-events: none");
    var linkUnlockEmpInc = document.getElementById('unlockIncLinkId');
    if(typeof linkUnlockEmpInc === 'undefined'){}
    else if(linkUnlockEmpInc === null){}
    else{
    linkUnlockEmpInc.setAttribute("style","pointer-events: none");}
   $('#empTable tbody').empty();
   $('#totalMonthIncentive').empty();
   $('#messageId').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var year = $("#yearSel").val();
   var month = $("#monthSel").val();
         $.get(
                  "/index/processMonthlyIncentive1",
                  {yearSel:year,
                   monthSel:month},
                   function (jsonResponse) {
                   var attr = jsonResponse[0], totalMonthIncentive = jsonResponse[1], messageAlert = jsonResponse[2];
                   $('#empTable tbody').empty();
                    let empRow1 = '<tr>' +
                                 '<th>' + "Sr No" + '</th>' +
                                 '<th>' + "Pay Cat" + '</th>' +
                                 '<th>' + "Emp Id" + '</th>' +
                                 '<th>' + "Emp Name" + '</th>' +
                                 '<th>' + "Monthly Basic rate in Rs." + '</th>' +
                                 '<th>' + "Attendance" + '</th>' +
                                 '<th>' + "Basic Rate Per Day" + '</th>' +
                                 '<th>' + "Actual Basic Amount in Rs." + '</th>' +
                                 '<th>' + "Incetive in Rs." + '</th>' +
                                 '<th>' + "Total Incentive Amount in Rs." + '</th>' +
                                 '<th>' + "Round in Rs." + '</th>' +
                                 '</tr>';
                    $('#empTable tbody').append(empRow1);
                   for(var i = 0; i < attr.length; i++) {
                            var a = attr[i];
                            let empRow = '<tr>' +
                                     '<td>' + a.srNo + '</td>' +
                                     '<td>' + a.payCat + '</td>' +
                                     '<td>' + a.empId + '</td>' +
                                     '<td>' + a.empName + '</td>' +
                                     '<td>' + a.monthlyBasicRate + '</td>' +
                                     '<td>' + a.attendance + '</td>' +
                                     '<td>' + a.basicRatePerDay + '</td>' +
                                     '<td>' + a.actualBasicAmount + '</td>' +
                                     '<td>' + a.incentive + '</td>' +
                                     '<td>' + a.totalIncentiveAmount + '</td>' +
                                     '<td>' + a.round + '</td>' +
                                     '</tr>';
                                     $('#empTable tbody').append(empRow);
                    }
                    $('#messageId').empty().append(messageAlert.fontcolor("red"));
                    $('#totalMonthIncentive').empty().append("Total Incentive: "+totalMonthIncentive);
                    $("#upload-header-text").html('<img src="" />');
                    linkMonthInc.setAttribute("style","pointer-events: auto");
                    linkEmpInc.setAttribute("style","pointer-events: auto");
                    linkEmpIncDetail.setAttribute("style","pointer-events: auto");
                    if(typeof linkUnlockEmpInc === 'undefined'){}
                    else if(linkUnlockEmpInc === null){}
                    else{
                    linkUnlockEmpInc.setAttribute("style","pointer-events: auto");
                    }
                    }
                    )


}