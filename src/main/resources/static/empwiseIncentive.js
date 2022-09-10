function showLoader() {
   $('#messageId').empty();
   $('#empTable tbody').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var emp = $("#empNo").val();
   var year = $("#yearSel").val();
   var month = $("#monthSel").val();
   $.get(
                  "/index/processIncentiveEmployeewise1",
                  {empNo:emp,
                   yearSel:year,
                   monthSel:month},
                   function (jsonResponse) {
                   var attr = jsonResponse[0], messageAlert = jsonResponse[1];
                   $('#empTable tbody').empty();

                    let empRow1 = '<tr>' +
                                 '<th>' + "Emp Id" + '</th>' +
                                 '<th>' + "Emp Name" + '</th>' +
                                 '<th>' + "Monthly Basic rate" + '</th>' +
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
                      $("#upload-header-text").html('<img src="" />');
                    }
                    )
}