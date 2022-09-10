function showLoader() {
   $('#empTable tbody').empty();
   $('#messageId').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var year = $("#yearSel").val();
   var month = $("#monthSel").val();
         $.get(
                  "/index/processMonthlyIncentiveAttendance",
                  {yearSel:year,
                   monthSel:month},
                   function (jsonResponse) {
                   var attr = jsonResponse[0], messageAlert = jsonResponse[1];
                   $('#empTable tbody').empty();
                    let empRow1 = '<tr>' +
                                 '<th>' + "Sr No." + '</th>' +
                                 '<th>' + "Pay Cat" + '</th>' +
                                 '<th>' + "Department" + '</th>' +
                                 '<th>' + "Emp Id" + '</th>' +
                                 '<th>' + "Emp Name" + '</th>' +
                                 '<th>' + "Category Code" + '</th>' +
                                 '<th>' + "Designation Code" + '</th>' +
                                 '<th>' + "Eligible Incentive" + '</th>' +
                                 '<th>' + "Rate" + '</th>' +
                                 '<th>' + "Present" + '</th>' +
                                 '<th>' + "Rokdi" + '</th>' +
                                 '<th>' + "Total" + '</th>' +
                                 '</tr>';
                    $('#empTable tbody').append(empRow1);
                   for(var i = 0; i < attr.length; i++) {
                            var a = attr[i];
                            let empRow = '<tr>' +
                                     '<td>' + a.srNo + '</td>' +
                                     '<td>' + a.payCat + '</td>' +
                                     '<td>' + a.dept + '</td>' +
                                     '<td>' + a.empCode + '</td>' +
                                     '<td>' + a.empName + '</td>' +
                                     '<td>' + a.categoryGrade + '</td>' +
                                     '<td>' + a.designationGrade + '</td>' +
                                     '<td>' + a.eligibleIncGrade + '</td>' +
                                     '<td>' + a.eligibleRate + '</td>' +
                                     '<td>' + a.present + '</td>' +
                                     '<td>' + a.rokdi + '</td>' +
                                      '<td>' + a.total + '</td>' +
                                     '</tr>';
                                     $('#empTable tbody').append(empRow);
                    }
                    $('#messageId').empty().append(messageAlert.fontcolor("red"));
                    $("#upload-header-text").html('<img src="" />');
                    }
                    )


}