function showLoader() {
 var linkMonthRew = document.getElementById('monthlyRewLinkId');
    linkMonthRew.setAttribute("style","pointer-events: none");
    var linkDailyRew = document.getElementById('dailyRewLinkId');
    linkDailyRew.setAttribute("style","pointer-events: none");
    var linkEmpRew = document.getElementById('empRewLinkId');
    linkEmpRew.setAttribute("style","pointer-events: none");
    var linkLoomRew = document.getElementById('loomRewLinkId');
        linkLoomRew.setAttribute("style","pointer-events: none");
    var linkUnlockEmpRew = document.getElementById('unlockRewLinkId');
    if(typeof linkUnlockEmpRew === 'undefined'){}
    else if(linkUnlockEmpRew === null){}
    else{
    linkUnlockEmpRew.setAttribute("style","pointer-events: none");}
   $('#empTable tbody').empty();
   $('#totalMonthReward').empty();
   $('#messageId').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');

   var year = $("#yearSel").val();
   var month = $("#monthSel").val();
         $.get(
                  "/index/processMonthlyRewards1",
                  {yearSel:year,
                   monthSel:month},
                   function (jsonResponse) {
                   var attr = jsonResponse[0], totalMonthReward = jsonResponse[1], messageAlert = jsonResponse[2];
                   $('#empTable tbody').empty();
                   let empRow1 = '<tr>' +
                                '<th>' + "Sr No" + '</th>' +
                                '<th>' + "Emp No" + '</th>' +
                                '<th>' + "Emp Name" + '</th>' +
                                '<th>' + "Total Shift" + '</th>' +
                                '<th>' + "Reward Amount in Rs." + '</th>' +
                                '</tr>';
                                $('#empTable tbody').append(empRow1);
                   for(var i = 0; i < attr.length; i++) {
                            var a = attr[i];
                            let empRow = '<tr>' +
                                     '<td>' + a.srNo + '</td>' +
                                     '<td>' + a.empNo + '</td>' +
                                     '<td>' + a.empName + '</td>' +
                                     '<td>' + a.totalShift + '</td>' +
                                     '<td>' + a.rewardAmount + '</td>' +
                                     '</tr>';
                                     $('#empTable tbody').append(empRow);
                    }
                    $('#messageId').empty().append(messageAlert.fontcolor("red"));
                    $('#totalMonthReward').empty().append("Total Month reward: "+totalMonthReward);
                    $("#upload-header-text").html('<img src="" />');
                     linkMonthRew.setAttribute("style","pointer-events: auto");
                     linkDailyRew.setAttribute("style","pointer-events: auto");
                     linkEmpRew.setAttribute("style","pointer-events: auto");
                     linkLoomRew.setAttribute("style","pointer-events: auto");
                     if(typeof linkUnlockEmpRew === 'undefined'){}
                     else if(linkUnlockEmpRew === null){}
                     else{
                     linkUnlockEmpRew.setAttribute("style","pointer-events: auto");
                     }
                    }
                    )
}

