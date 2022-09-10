function showLoader() {
   $('#messageId').empty();
   $('#empTable tbody').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var emp = $("#empNo").val();
   var startD = $("#startdate").val();
   var endD = $("#enddate").val();
   $.get(
                  "/index/processEmployeeWiseForm",
                  {empNo:emp,
                   startdate:startD,
                   enddate:endD},
                   function (jsonResponse) {
                   var attr = jsonResponse[0], totalRew = jsonResponse[1], messageAlert = jsonResponse[2];
                   $('#empTable tbody').empty();
                   let empRow1 = '<tr>' +
                                '<th>' + "Sr No" + '</th>' +
                                '<th>' + "Emp No" + '</th>' +
                                '<th>' + "Emp Name" + '</th>' +
                                '<th>' + "Category" + '</th>' +
                                '<th>' + "RewardDate" + '</th>' +
                                '<th>' + "Shift" + '</th>' +
                                 '<th>' + "Loom" + '</th>' +
                                 '<th>' + "Pick" + '</th>' +
                                 '<th>' + "Reward Amount in Rs." + '</th>' +
                                 '<th>' + "Reward Amount Slab" + '</th>' +
                                 '</tr>';
                      $('#empTable tbody').append(empRow1);
                     for(var i = 0; i < attr.length; i++) {
                            var a = attr[i];
                            let empRow = '<tr>' +
                                         '<td>' + a.srNo + '</td>' +
                                          '<td>' + a.empNo + '</td>' +
                                          '<td>' + a.empName + '</td>' +
                                          '<td>' + a.category + '</td>' +
                                          '<td>' + a.dateInString + '</td>' +
                                          '<td>' + a.shift + '</td>' +
                                          '<td>' + a.loom_no + '</td>' +
                                          '<td>' + a.pickMeter + '</td>' +
                                          '<td>' + a.rewardAmount + '</td>' +
                                          '<td>' + a.rewardAmountSlab + '</td>' +
                                          '</tr>';
                            $('#empTable tbody').append(empRow);
                            }
                      $('#totalReward').empty().append("Total Reward: "+totalRew);
                      $('#messageId').empty().append(messageAlert.fontcolor("red"));
                      $("#upload-header-text").html('<img src="" />');
                    }
                    )
}