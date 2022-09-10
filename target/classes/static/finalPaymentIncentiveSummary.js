function showLoader() {
   $('#incentiveId').empty();
   $('#empTable tbody').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var year = $("#yearSel").val();
   var month = $("#monthSel").val();
         $.get(
                  "/index/processFinalPaymentIncentive",
                  {yearSel:year,
                   monthSel:month},
                   function (jsonResponse) {
                   var attr = jsonResponse[0], messageAlert = jsonResponse[1];
                   $('#empTable tbody').empty();
                   let empRow1 = '<tr>' +
                                 '<th>' + "Sr No" + '</th>' +
                                 '<th>' + "Emp No" + '</th>' +
                                 '<th>' + "Emp Name" + '</th>' +
                                 '<th>' + "Net Amount in Rs." + '</th>' +
                                 '</tr>';
                   $('#empTable tbody').append(empRow1);
                   for(var i = 0; i < attr.length; i++) {
                            var a = attr[i];
                            let empRow = '<tr>' +
                                     '<td>' + a.id + '</td>' +
                                     '<td>' + a.ptEmpCode + '</td>' +
                                     '<td>' + a.ptEmpName + '</td>' +
                                     '<td>' + a.netAmount + '</td>' +
                                     '</tr>';
                    $('#empTable tbody').append(empRow);
                    }

                    $('#incentiveId').empty().append(messageAlert.fontcolor("red"));
                    $("#upload-header-text").html('<img src="" />');
                    }
                    )
}