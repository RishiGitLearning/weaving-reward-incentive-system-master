function showLoader() {
   var id = $("#idSel").val();
         $.get(
                  "/index/processRewardSlabId",
                  {idSel:id},
                   function (jsonResponse) {
                    var attr = jsonResponse[0], attr1 = jsonResponse[1], attr2 = jsonResponse[2], attr3 = jsonResponse[3], attr4 = jsonResponse[4], attr5 = jsonResponse[5], attr6 = jsonResponse[6], attr7 = jsonResponse[7], attr8 = jsonResponse[8], attr9 = jsonResponse[9], attr10 = jsonResponse[10], attr11 = jsonResponse[11], attr12 = jsonResponse[12], attr13 = jsonResponse[13], attr14 = jsonResponse[14], attr15 = jsonResponse[15], attr16 = jsonResponse[16], attr17 = jsonResponse[17], attr18 = jsonResponse[18], attr19 = jsonResponse[19], attr20 = jsonResponse[20], attr21 = jsonResponse[21], attr22 = jsonResponse[22], attr23 = jsonResponse[23], attr24 = jsonResponse[24], attr25 = jsonResponse[25], attr26 = jsonResponse[26], attr27 = jsonResponse[27], attr28 = jsonResponse[28], attr29 = jsonResponse[29], attr30 = jsonResponse[30], attr31 = jsonResponse[31], attr32 = jsonResponse[32], attr33 = jsonResponse[33], attr34 = jsonResponse[34], attr35 = jsonResponse[35], attr36 = jsonResponse[36];
                    document.getElementById("loom").value = attr;
                    document.getElementById("rpm").value = attr1;
                    document.getElementById("effPer").value = attr2;
                    document.getElementById("prodnPerHr").value = attr3;
                    document.getElementById("weftDetails").value = attr4;
                    document.getElementById("shiftAB").value = attr5;
                    document.getElementById("shiftC").value = attr6;
                    document.getElementById("shiftABPicksForRewards").value = attr7;
                    document.getElementById("shiftCPicksForRewards").value = attr8;
                    document.getElementById("reward1").value = attr9;
                    document.getElementById("shiftABPicksForRewards2").value = attr10;
                    document.getElementById("shiftCPicksForRewards2").value = attr11;
                    document.getElementById("reward2").value = attr12;
                    document.getElementById("shiftABPicksForRewards3").value = attr13;
                    document.getElementById("shiftCPicksForRewards3").value = attr14;
                    document.getElementById("reward3").value = attr15;
                    document.getElementById("shiftABPicksForRewards4").value = attr16;
                    document.getElementById("shiftCPicksForRewards4").value = attr17;
                    document.getElementById("reward4").value = attr18;
                    document.getElementById("shiftABPicksForRewards5").value = attr19;
                    document.getElementById("shiftCPicksForRewards5").value = attr20;
                    document.getElementById("reward5").value = attr21;
                    document.getElementById("shiftABPicksForRewards6").value = attr22;
                    document.getElementById("shiftCPicksForRewards6").value = attr23;
                    document.getElementById("reward6").value = attr24;
                    document.getElementById("shiftABPicksForRewards7").value = attr25;
                    document.getElementById("shiftCPicksForRewards7").value = attr26;
                    document.getElementById("reward7").value = attr27;
                    document.getElementById("shiftABPicksForRewards8").value = attr28;
                    document.getElementById("shiftCPicksForRewards8").value = attr29;
                    document.getElementById("reward8").value = attr30;
                    document.getElementById("shiftABPicksForRewards9").value = attr31;
                    document.getElementById("shiftCPicksForRewards9").value = attr32;
                    document.getElementById("reward9").value = attr33;
                    document.getElementById("shiftABPicksForRewards10").value = attr34;
                    document.getElementById("shiftCPicksForRewards10").value = attr35;
                    document.getElementById("reward10").value = attr36;
                    }
                    )
}

function showLoader1() {
   $('#messageId').empty();
   $("#upload-header-text").html('<img src="http://i.stack.imgur.com/FhHRx.gif" />');
   var id = $("#idSel").val();
       $.get(
                  "/index/processRewardDelete",
                  {idSel:id
                 },
                   function (jsonResponse) {
                   var attr = jsonResponse[0];
                   $('#messageId').empty().append(attr.fontcolor("green"));
                    $("#upload-header-text").html('<img src="" />');
                    }
                    )
}