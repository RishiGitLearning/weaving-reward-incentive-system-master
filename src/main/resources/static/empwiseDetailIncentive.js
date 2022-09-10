function showLoader() {
   $('#messageId').empty();
   $('#empTable tbody').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var emp = $("#empNo").val();
   var year = $("#yearSel").val();
   var month = $("#monthSel").val();
         $.get(
                  "/index/processIncentiveEmployeewiseDetails1",
                  {empNo:emp,
                   yearSel:year,
                   monthSel:month},
                   function (jsonResponse) {
                   var attr = jsonResponse[0], daysdetails = jsonResponse[1], messageAlert = jsonResponse[2];
                   $('#empTable tbody').empty();

                    let empRow1 = '<tr>' +
                                 '<th>' + "Sr No" + '</th>' +
                                 '<th>' + "Emp Id" + '</th>' +
                                 '<th>' + "Emp Name" + '</th>' +
                                 '<th>' + "Date" + '</th>' +
                                 '<th>' + "Category Grade" + '</th>' +
                                 '<th>' + "Designation Grade" + '</th>' +
                                 '<th>' + "Selection Grade" + '</th>' +
                                 '<th>' + "Rate in Rs." + '</th>' +
                                 '</tr>';
                    $('#empTable tbody').append(empRow1);
                   for(var i = 0; i < attr.length; i++) {
                            var a = attr[i];
                            let empRow = '<tr>' +
                                     '<td>' + a.srNo + '</td>' +
                                     '<td>' + a.empId + '</td>' +
                                     '<td>' + a.empName + '</td>' +
                                     '<td>' + a.dateInString + '</td>' +
                                     '<td>' + a.incCategory + '</td>' +
                                     '<td>' + a.incDesignation + '</td>' +
                                     '<td>' + a.incSelectionGrade + '</td>' +
                                     '<td>' + a.incDailyRate + '</td>' +
                                     '</tr>';
                    $('#empTable tbody').append(empRow);
                    }

                    $('#detailDaysWithRate').empty().append(daysdetails);
                    $('#messageId').empty().append(messageAlert.fontcolor("red"));
                    $("#upload-header-text").html('<img src="" />');
                    }
                    )
}