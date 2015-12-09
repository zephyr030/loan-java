$(document).ready(function(){
    $("#nextBn").click(insertAmount);
    $("#backBn").click(goBack);
});

function insertAmount() {
    var account = $.trim($("#account").val());
    var amount = $.trim($("#amount").val());
    var recType = $('input[name="recType"]:checked').val();

    if(!isMultiple(amount, 100)) {
        alert("请输入100的整数倍");
        return;
    }

    $.ajax({
        cache: false,
        async: false,
        type: "GET",
        dataType: "json",
        url:path + "/user/recharge/amount/input",
        data:{
            account: account,
            amount: amount,
            recType: recType
        },
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
            if(!data.success) {
                alert(data.message);
            }else {
                $("#amountForm").submit();
            }
        }
    });
}

function goBack() {
    var account = $.trim($("#account").val());
    document.location.href = path + '/user/recharge?account=' + account;
}