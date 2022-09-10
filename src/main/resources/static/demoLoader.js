function showLoader() {
    var link = document.getElementById('linkId');
    link.setAttribute("style","pointer-events: none");
    $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
    var emp_id = $("#empNo").val();
    $.get(
            "http://localhost:8091/index/getmsg",
             {empNo:emp_id},
            function(data){
            }
          )
           link.setAttribute("style","pointer-events: auto");
}