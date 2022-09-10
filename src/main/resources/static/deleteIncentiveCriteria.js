function showLoader() {
   $('#messageId').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var id = $("#idSel").val();
         $.get(
                  "/index/processIncentiveUnlock2",
                  {idSel:id},
                   function (jsonResponse) {
                    var attr = jsonResponse[0], attr1 = jsonResponse[1];
                   document.getElementById("production").value = attr;
                    document.getElementById("factor").value = attr1;
                    $("#upload-header-text").html('<img src="" />');
                    }
                    )
}

function showLoader1() {
   $('#messageId').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var id = $("#idSel").val();
    var prod = $("#production").val();
     var fact = $("#factor").val();
         $.get(
                  "/index/processIncentiveUnlock4",
                  {idSel:id,
                  production:prod,
                  factor:fact},
                   function (jsonResponse) {
                    var attr = jsonResponse[0];
                   $('#messageId').empty().append(attr.fontcolor("green"));
                    $("#upload-header-text").html('<img src="" />');
                    }
                    )
}