function showLoader() {
   $('#messageId').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var year = $("#yearSel").val();
   var month = $("#monthSel").val();
         $.get(
                  "/index/processIncentiveUnlock1",
                  {yearSel:year,
                   monthSel:month},
                   function (jsonResponse) {
                    var attr = jsonResponse[0];
                    $('#messageId').empty().append(attr.fontcolor("green"));
                    $("#upload-header-text").html('<img src="" />');
                    }
                    )
}